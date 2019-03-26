# WanAndroid
### WanAndroid，一款基于MVP + Rxjava2 + Dagger2 + Retrofit + Material Design的应用, 欢迎大家start、fork。
### Pre
本人小白一个，一直以来都想自己做一个完整的app，所以本项目就在我的闲暇时间中形成，参考了几个WanAndroid开源应用，一开始遇到很多不懂的，也靠自己解决了，所以会一直添加新的功能，解决bug，不断打造成一款持续稳定, 功能完善的WanAndroid应用。
### Features
- [x] 首页
- [x] 知识体系
- [x] 公众号
- [x] 导航
- [x] 项目
- [x] 搜索，热词搜索，搜索记录
- [x] 登录、注册、注销
- [x] 我的收藏、取消收藏、添加收藏
- [x] 文章内容，分享文章
- [x] 夜间模式，沉浸式切换，清除缓存
- [x] 应用内检查更新
### Tips
* 本人能力有限，可能会在bug，如有任何问题，欢迎[issues](https://github.com/rain9155/WanAndroid/issues)
* 项目中所用到的api均由[WanAndroid网站](http://www.wanandroid.com/blog/show/2)提供 
### Preview
<br> ![wan1](/screenshots/wan1.gif) ![wan2](/screenshots/wan2.gif) <br>
 <br> ![wan3](/screenshots/wan3.gif) ![wan4](/screenshots/wan4.gif) <br>
### Screenshots
 <br> ![sc3](/screenshots/sc3.png) ![sc2](/screenshots/sc2.png) <br>
 <br> ![sc4](/screenshots/sc4.png) ![sc5](/screenshots/sc5.png)<br>
 <br>![sc6](/screenshots/sc6.png) ![sc7](/screenshots/sc7.png)<br>
  <br>![sc8](/screenshots/sc8.png) ![sc9](/screenshots/sc9.png)<br>
### Download (v1.4, Android 4.1 or above)
1、[手动下载apk](https://github.com/rain9155/WanAndroid/releases/download/v1.4/app-release.apk) 
<br> <br>
2、二维码下载
<br> <br>
![二维码下载](/screenshots/无标题.png)
<br>
### Points And Reference
1、Rxjava2配合Retrofit进行网络请求
* [给 Android 开发者的 RxJava 详解](https://gank.io/post/560e15be2dca930e00da1083)
* [你真的会用Retrofit2吗?Retrofit2完全教程](https://www.jianshu.com/p/308f3c54abdd)
* [Android 教你一步步搭建MVP+Retrofit+RxJava网络请求框架](https://www.jianshu.com/p/7b839b7c5884)

2、Rxjava2与Retrofit的封装使用
* [Rxjava2+Retrofit完美封装](https://blog.csdn.net/qq_20521573/article/details/70991850)
* [Retrofit自定义GsonConverter处理所有请求错误情况](https://www.jianshu.com/p/5b8b1062866b)

3、Retrofit配合Cookie实现免登陆与实现离线缓存
* [Okhttp完美同步持久Cookie实现免登录](https://www.jianshu.com/p/1a5f14b63f47)
* [给Retrofit添加离线缓存](https://www.jianshu.com/p/7aa8f3443e05)

4、用Rxjava2实现轻量级的EventBus
* [用RxJava实现事件总线(Event Bus)](https://www.jianshu.com/p/ca090f6e2fe2)

5、依赖注入实现，配合MVP模式
* [Android：dagger2让你爱不释手-基础依赖注入框架篇](https://www.jianshu.com/p/cd2c1c9f68d4)
* [Dagger2 入门,以初学者角度.](https://www.jianshu.com/p/1d84ba23f4d2)

6、使用了MD控件和动画
* [MaterialDesign之SearchView全面解锁](https://www.jianshu.com/p/7c1e78e91506)
* [Android 使用CardView轻松实现卡片式设计](https://blog.csdn.net/u013651026/article/details/79000205)
* [聊聊Android5.0中的水波纹效果](https://blog.csdn.net/u012702547/article/details/52325418)


7、自定义Behavior（俩种实现）
* 具体参考[代码](https://github.com/rain9155/WanAndroid/tree/master/app/src/main/java/com/example/hy/wanandroid/widget/behaviour)

8、使用原生的夜间模式,并切换自然
* [简洁优雅地实现夜间模式](https://tonnyl.io/Night-Mode-on-Android/)
* [知乎和简书的夜间模式实现套路](https://www.jianshu.com/p/3b55e84742e5)

9、实现了Fragment的操作和懒加载
* [那些年踩过的坑](https://www.jianshu.com/p/d9143a92ad94)
* [ViewPager+TabLayout+Fragment懒加载机制完全解析](https://www.jianshu.com/p/eb81f3692229)

10、使用DownloadManager实现应用更新
* [DownloadManager 的使用 ](https://www.cnblogs.com/zhaoyanjun/p/4591960.html)
* [Android:使用 DownloadManager 进行版本更新](https://www.cnblogs.com/liyiran/p/6393813.html)

11、努力兼容Android版本
* [Android 使用 Https问题解决（SSLHandshakeException）](http://www.mamicode.com/info-detail-1728871.html)
* [Drawable 着色的后向兼容方案](https://www.race604.com/tint-drawable/)
* [CardView在API 21以下的圆角效果处理](https://www.jianshu.com/p/07097b562acb)
* [Android 6.0运行权限解析（高级篇）](https://www.jianshu.com/p/6a4dff744031)

12、集成腾讯Bugly与LeakCanary，更容易找出bug与内存泄漏
* 参考官方文档

> 以上是项目中的主要亮点和我做项目时参考的文章，当然对于一些主流的框架官方文档中也有详细的介绍，更多细节可以查看项目具体代码。

### TODO
- [ ] Dagger-Android
- [ ] 优化app
- [ ] 单元测试
### ChangeLog
```
v1.4 2019-2-23
1、修复v1.3登陆失败问题
2、添加更换头像背景功能

v1.3 2018-12-31
1、添加文章长按
1、修复一些bug
1、添加文章标签

v1.2 2018-12-24
1、增加公众号模块
2、集成bugly
3、移除Fragmentation库
4、更新版本更新弹窗

v1.1 2018-12-15
1、优化夜间切换
2、加入沉浸切换
3、加入缓存清理
4、增加活动过渡动画
5、5.0以上增加波纹点击效果，5.0以下增加触摸反馈
6、增加应用内检查更新
7、登陆持久化

v1.0 2018-12-1
第一版发布
```
### Thanks
* **App** <br>
[WanAndroid](https://github.com/iceCola7/WanAndroid) <br> 
[Awesome-WanAndroid](https://github.com/JsonChao/Awesome-WanAndroid) <br>
[GeekNews](https://github.com/codeestX/GeekNews) <br>
* **UI** <br>
[阿里巴巴图标](http://www.iconfont.cn/home/index) <br> 
[Material Design Palette](http://huaban.com/)<br>
[Material Design Color](https://www.materialui.co/colors)<br>
* **DB** <br>
[LitePal](https://github.com/LitePalFramework/LitePal) <br>
* **Network** <br>
[Retrofit](https://github.com/square/retrofit) <br>
* **Rx** <br>
[RxJava2](https://github.com/ReactiveX/RxJava) <br>
* **Injection** <br>
[Dagger](https://github.com/google/dagger) <br>
[Butterknife](https://github.com/JakeWharton/butterknife)
* **ImageLoader** <br>
[Glide](https://github.com/bumptech/glide)<br>
* **Leak Check** <br>
[LeakCanary](https://github.com/square/leakcanary)
* **Other excellent open source library** <br>
[WaveLoadingView](https://github.com/tangqi92/WaveLoadingView)<br>
[MultiWaveHeader](https://github.com/scwang90/MultiWaveHeader)<br>
[VerticalTabLayout](https://github.com/qstumn/VerticalTabLayout)<br>
[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)<br>
[Banner](https://github.com/youth5201314/banner)<br>
[FlowLayout](https://github.com/hongyangAndroid/FlowLayout)<br>
[AgentWeb](https://github.com/Justson/AgentWeb)<br>
[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)<br>
[ImageCropper](https://github.com/ArthurHub/Android-Image-Cropper)
### End
本项目只用作学习用途，不做其他用途 
### License
```
Copyright 2018 rain9155

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License a

          http://www.apache.org/licenses/LICENSE-2.0 
          
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
   
   ```
