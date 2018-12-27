package com.example.hy.wanandroid.view.homepager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.bean.ArticleBean;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.homepager.ArticleContract;
import com.example.hy.wanandroid.di.component.activity.DaggerArticleActivityComponent;
import com.example.hy.wanandroid.presenter.homepager.ArticlePresenter;
import com.example.commonlib.utils.NetWorkUtil;
import com.example.commonlib.utils.ShareUtil;
import com.example.commonlib.utils.StatusBarUtil;
import com.example.hy.wanandroid.view.mine.LoginActivity;
import com.example.hy.wanandroid.widget.layout.WebLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class ArticleActivity extends BaseActivity implements ArticleContract.View {

    private AgentWeb mAgentWeb;
    private String mAddress;
    private String mTitle;
    private boolean isCollection;
    private boolean isHideCollection;
    private int mArticleId;
    private MenuItem mCollectionItem;

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @Inject
    ArticlePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent != null){
            ArticleBean articleBean = intent.getParcelableExtra(Constant.KEY_ARTICLE_BEAN);
            mAddress = articleBean.getLink();
            mTitle = articleBean.getTitle();
            isCollection = articleBean.isCollect();
            mArticleId = articleBean.getId();
            isHideCollection = intent.getBooleanExtra(Constant.KEY_ARTICLE_FLAG, false);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DaggerArticleActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
        mPresenter.attachView(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP)
            StatusBarUtil.setHeightAndPadding(this, tlCommon);
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(tlCommon);
        tlCommon.setTitle("");
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tlCommon.setNavigationOnClickListener(v -> {
            Intent intent2 = new Intent();
            intent2.putExtra(Constant.KEY_DATA_RETURN, isCollection);
            setResult(RESULT_OK, intent2);
            finish();
        });
    }

    @Override
    protected void initData() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(flContainer, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(getResources().getColor(R.color.colorPrimary))
                .setMainFrameErrorView(R.layout.error_view, -1)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .createAgentWeb()
                .ready()
                .go(mAddress);

        //得到WebView
        WebView webView = mAgentWeb.getWebCreator().getWebView();
        WebSettings settings = webView.getSettings();
        setSettings(settings);

        //得到AgentWeb最底层的控件
        FrameLayout frameLayout = mAgentWeb.getWebCreator().getWebParentLayout();
        addBgChild(frameLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_tl_menu, menu);
        mCollectionItem = menu.findItem(R.id.item_collection);
        if(isHideCollection) mCollectionItem.setVisible(false);
        if(isCollection) mCollectionItem.setTitle(getString(R.string.articleActivity_cancel_collection));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_share:
                shareText();
                break;
            case R.id.item_collection:
                if(mArticleId == -1) return true;
                collection();
                break;
            case R.id.item_browser:
                ShareUtil.openBrowser(this, mAddress);
                break;
            case R.id.item_copy:
                copy(this, mAddress);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == Constant.REQUEST_COLLECT_ARTICLE){
            if(isCollection) mPresenter.unCollectArticle(mArticleId);
            else mPresenter.collectArticle(mArticleId);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Intent intent = new Intent();
            intent.putExtra(Constant.KEY_DATA_RETURN, isCollection);
            setResult(RESULT_OK, intent);
            finish();
        }
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void collectArticleSuccess() {
        mCollectionItem.setTitle(R.string.articleActivity_cancel_collection);
        isCollection = true;
        showToast(getString(R.string.common_collection_success));
    }

    @Override
    public void unCollectArticleSuccess() {
        mCollectionItem.setTitle(R.string.articleActivity_collection);
        isCollection = false;
        showToast(getString(R.string.common_uncollection_success));
    }

    @Override
    public void collect() {
        if(isCollection)
            mPresenter.unCollectArticle(mArticleId);
        else
            mPresenter.collectArticle(mArticleId);
    }

    /**
     * 收藏事件
     */
    private void collection() {
        if(!User.getInstance().isLoginStatus()){
            LoginActivity.startActivityForResult(this, Constant.REQUEST_COLLECT_ARTICLE);
            showToast(getString(R.string.first_login));
            return;
        }
        collect();
    }

    /**
     * 复制字符串
     */
    private void copy(Context context, String text) {
        ShareUtil.copyString(context, text);
        showToast(getString(R.string.articleActivity_copy_success));
    }

    /**
     * 分享
     */
    private void shareText() {
        if(!TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(mAddress))
            ShareUtil.shareText(
                    this,
                    getString(R.string.articleActivity_share_text) + "\n" + mTitle + "\n" + mAddress,
                    getString(R.string.articleActivity_share_to)
            );
        else
            showToast(getString(R.string.articleActivity_share_fail));
    }

    /**
     * 配置WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void setSettings(WebSettings settings) {
        if (mPresenter.getNoImageState())
            settings.setBlockNetworkImage(true);
        else
            settings.setBlockNetworkImage(false);

        if(mPresenter.getAutoCacheState()){
            settings.setAppCacheEnabled(true);
            settings.setDatabaseEnabled(true);
            settings.setDatabaseEnabled(true);
            if(NetWorkUtil.isNetworkConnected(this))
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);//（默认）根据cache-control决定是否从网络上取数据
            else
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        }else {
            settings.setAppCacheEnabled(false);
            settings.setDatabaseEnabled(false);
            settings.setDatabaseEnabled(false);
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据
        }

        //支持缩放
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        settings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        settings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    /**
     * 设置下拉回弹的文字
     */
    protected void addBgChild(FrameLayout frameLayout) {
        TextView textView = new TextView(frameLayout.getContext());
        textView.setText(R.string.articleActivity_support);
        textView.setTextSize(16);
        textView.setTextColor(Color.parseColor("#727779"));
        frameLayout.setBackgroundColor(Color.parseColor("#272b2d"));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        final float scale = frameLayout.getContext().getResources().getDisplayMetrics().density;
        params.topMargin = (int) (30 * scale + 0.5f);
        frameLayout.addView(textView, 0, params);
    }

    /**
     * 启动活动
     */
    public static void startActivity(Context context,  ArticleBean articleBean, boolean isHideCollection){
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(Constant.KEY_ARTICLE_FLAG, isHideCollection);
        intent.putExtra(Constant.KEY_ARTICLE_BEAN, articleBean);
        context.startActivity(intent);
    }

    /**
     * 启动活动，使用Activity中的startActivityForResult（）
     */
    public static void startActivityForResult(Activity activity, ArticleBean articleBean, boolean isHideCollection, int request){
        Intent intent = new Intent(activity, ArticleActivity.class);
        intent.putExtra(Constant.KEY_ARTICLE_BEAN, articleBean);
        intent.putExtra(Constant.KEY_ARTICLE_FLAG, isHideCollection);
        activity.startActivityForResult(intent, request);
    }

    /**
     * 启动活动，使用Fragment中的startActivityForResult（）
     */
    public static void startActicityForResultByFragment(Activity activity, Fragment fragment, ArticleBean articleBean, boolean isHideCollection, int request){
        Intent intent = new Intent(activity, ArticleActivity.class);
        intent.putExtra(Constant.KEY_ARTICLE_BEAN, articleBean);
        intent.putExtra(Constant.KEY_ARTICLE_FLAG, isHideCollection);
        fragment.startActivityForResult(intent, request);
    }
}


