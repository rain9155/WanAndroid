## 目标

通过组件化把WanAndroid项目拆分成1个壳app+n个组件，搭建一个清晰的代码框架，加速代码构建速度，提升开发效率，组件化搭建过程：

- 1、把app按功能划分为多个模块，把多个模块之间公用的能力下沉；
- 2、每个模块可以独立发版、独立运行调试，app依赖这些模块时可以在maven和源码依赖之间切换；
- 3、除基础模块外，其他模块之间通过接口进行相互依赖，不能直接依赖实现，具体实现由app打包时注入.

## 组件化架构

![sc12](/screenshots/sc12.png)

整个app分为3部分：壳app、业务组件、基础库：

- 基础库：主要包含第三方库、基于第三方库封装的库、基础的Activity和Fragment类，业务无关的工具类、通用资源、通用Model等，供所有业务组件集成；
- 业务组件：细分为业务组件和业务基础组件，业务组件是指按照业务划分的组件，例如首页、项目、搜索等，业务基础组件是指各个业务都在依赖的能力，例如账户、下载、分享等；
- 壳app：主要是集成所有业务组件整合成完整的App，提供Application唯一实现，Gradle、Manifest的统一配置等.

## 依赖关系

![sc13](/screenshots/sc13.png)

组件依赖关系是上层依赖下层，不能反向依赖，修改频率是上层高于下层,  业务组件之间禁止直接依赖，通过Api来进行隔离，基础库不包含任何业务，无需进行Api隔离，可以被业务组件直接依赖，任何组件的Api库只负责定义接口、数据结构，不能依赖其它组件的Api部分，每个组件最多只允许2层依赖，更多层的依赖意味着更多的管理成本(Exclude & force)，站在图中每一个角色的角度来分析它们当前能看到什么依赖：

- 基础库：所有基础库只能看到其他的基础库，不能看到其它的依赖；
- Api库：所有的Api库原则上只能看到自己，看不到其他依赖；
- 业务基础组件：能看到基础库，自己对外暴露的Api，其它业务基础组件的Api；
- 业务组件：能看到基础库，自己对外暴露的Api，公共基础组件的Api和其他业务组件的Api；
- 壳app：在编译环境下，能看到各个Api，在运行时，能看到各个Api和各个实现，这样我们就在编译时把所有的实现都隔离开.

Api库设计原则：

- Api库不能依赖其它API库；

- Api库原则上不能依赖其它库或者第三方库，如果需要依赖，请使用implementation来隔绝依赖向上传递；
- Api库需要需要尽量控制Api接口中的方法个数，如果组件需要对外暴露较多能力，可以通过多个接口进行职责分离；
- Api库中只能包含接口中直接依赖的类和需要对外部暴露的类，其它内容都不应该出现在API库中，优先使用接口对外暴露调用，尽量避免直接对外暴露类来使用.

组件库 & 基础库设计原则：

- 原则上组件需要实现功能闭环，不依赖外部调度也能正常work；
- 有多个实现库可选的，建议做成基础能力组件，抽象出接口，未来可以方便替换实现库；
- 尽量避免提供类似于init的方法，直接使用Dagger实现组件懒加载.

## 组件化设计

### 1、组件独立调试

1.1、单仓方案

通过gradle动态配置每个模块的工程类型，apply不同的插件：

```groovy
//gradle.properties
isModule = false

//build.gradle
if (isModule.toBoolean()){
    apply plugin: 'com.android.application'
}else {
    apply plugin: 'com.android.library'
}
```

模块独立调试时需要配置模块的applicationId和AndroidManifest文件，

```groovy
//build.gradle
android {
    defaultConfig {
        // 独立调试时添加applicationId，集成调试时移除
        if (isModule.toBoolean()) {
            applicationId "com.example.rain9155"
        }
    }

    sourceSets {
        main {
            // 独立调试与集成调试时使用不同的AndroidManifest.xml文件
            if (isModule.toBoolean()) {
                manifest.srcFile 'src/main/debug/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
    }
}
```

独立调试时AndroidManifest文件需要指定启动的Application和Activity。

1.2、多仓方案

多仓库方案，业务组件以module的形式存在于独立的仓库，独立仓库自然就可以独立调试了，不再需要进行上面那些配置了，同时对于单仓库，也可以采用类似多仓库的方案，为每个组件新建一个目录，对每个组件新建相应的demo工程引用这个组件进行独立调试，把跟这个组件相关的Impl库、Api库、demo工程放在这个目录下。

### 2、组件独立发版

把组件的aar包通过maven-publish插件发布到maven仓库，然后在壳工程通过maven进行依赖就行，组件发布脚本参考[MavenPublishScript](https://github.com/rain9155/MavenPublishScript)、[ModuleManager](https://github.com/theCakeOfCupid/ModuleManager)。

### 3、组件源码依赖切换

使用gradle的[Dependency substitution](https://docs.gradle.org/current/userguide/resolution_rules.html#sec:dependency_substitution_rules)功能，动态地把组件的maven依赖切换成源码依赖，方便集成调试：

```groovy
//build.gradle
//维护一份组件到maven依赖的映射表
def libMappings = [
        'project1': 'com.example.rain9155:lib1:1.0',
        'project2': 'com.example.rain9155:lib2:1.0',
        'project3': 'com.example.rain9155:lib3:1.0',
]
//如果是独立调试，则进行依赖替换
if(isModule.toBoolean()) {
    def substitutions = libMappings.collectEntries { entry ->
        def substitution = [:]
        def aar = entry.value.lastIndexOf(':').with { index ->
            entry.value.substring(0, index)
        }
        substitution[aar] = ":${entry.key}"
        return substitution
    }
    configurations.forEach { configuration ->
        configuration.resolutionStrategy.dependencySubstitution { dependencySubstitutions ->
            substitutions.forEach { aar, path ->
                //把组件maven依赖切换到本地源码依赖
                substitute module(aar) with project(path)
            }
        }
    }
}
```

对于多仓库，组件的源码往往是在独立的仓库中，为了进行多仓的源码切换，我们要先把组件的仓库clone下来，然后在主仓库的settings.gradle文件中include组件相应模块，让组件参与本地构建。

### 4、组件依赖版本统一管理

对于单仓库，我们可以采用project.ext、[Kotlin+buildSrc](https://juejin.cn/post/6844903615346245646)、[Plugin+includeBuild](https://juejin.cn/post/6844904169833234439)这些方式来在多个模块之间统一管理依赖的版本，而对于多个仓库，我们可以使用单仓库的方式发布共享库来共享依赖版本，也可以使用gradle提供的[Version Catalog](https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog)、[Platform](https://docs.gradle.org/current/userguide/platforms.html#sub:using-platform-to-control-transitive-deps)方式来在多个项目之间共享依赖版本。

> buildSrc中的修改会导致整个项目的缓存失效，所以推荐使用includeBuild来代替，[Stop using Gradle buildSrc, Use composite builds instead](https://proandroiddev.com/stop-using-gradle-buildsrc-use-composite-builds-instead-3c38ac7a2ab3).

### 5、组件间通信

组件间通信统一采用服务暴露、注入、发现的方式来进行通信，在Api库暴露服务接口，在Impl库实现服务，通过[ARouter](https://github.com/alibaba/ARouter)、[Dagger2](https://github.com/google/dagger)、[EventBus](https://github.com/greenrobot/EventBus)等建立起服务接口和服务实现的映射关系，使用路由或组件总线的方式来管理和发现服务

### 6、其他改造点

- ButterKnife改造：组件化后在模块中使用R.id.XX会报错，解决办法是在模块中添加[ButterKnife插件](https://github.com/JakeWharton/butterknife#library-projects)，然后在源码中把R的使用替换成R2；
- Dagger改造：组件化后每个模块的依赖注入在模块内闭环，模块之间的Component需要相互依赖，Dagger提供了SubComponent和dependencies两种依赖方式，可以参考[Using Dagger in multi-module apps](https://developer.android.com/training/dependency-injection/dagger-multi-module)；
- 组件初始化：对于一些组件需要在app启动时进行初始化，可以借助[App Startup](https://developer.android.com/topic/libraries/app-startup)在组件内部提前完成初始化，对于一些组件的初始化需要依赖其他组件的初始化，可以借助[Alpha](https://github.com/alibaba/alpha)、[Anchors](https://github.com/YummyLau/Anchors)这些库完成初始化.


## 参考文档

[Android组件化，全面掌握](https://juejin.cn/post/6881116198889586701)

[模块管理最佳实践-ModuleManager](https://juejin.cn/post/6986326399296471053#heading-9)

[Android组件化之(路由 vs 组件总线)](https://juejin.cn/post/6844903582374821901?share_token=6d6f0fd3-5148-4556-95b0-89346adc2e31)

[总结一波安卓组件化开源方案](https://juejin.cn/post/6844903565035569166)

[组件化场景下module依赖优雅实践方案](https://juejin.cn/post/6925629544946892813)
