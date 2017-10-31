# PaoNet
泡网第三方客户端

遵循架构

![](http://upload-images.jianshu.io/upload_images/3722695-fe58ac2b03c7377b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 
 ```
 .
 |____PaoApp.kt  //应用APP
 |____aop   //aspectj 拦截未登录及处理快速点击
 | |____annotation
 | | |____CheckLogin.kt
 | | |____SingleClick.kt
 | |____aspect
 | | |____CheckLoginAspect.kt
 | | |____SingleClickAspect.kt
 |____di    //依赖注入，提供页面需要的实例
 | |____component
 | | |____ActivityComponent.kt
 | | |____AppComponent.kt
 | | |____FragmentComponent.kt
 | |____module
 | | |____ActivityModule.kt
 | | |____AppModule.kt
 | | |____FragmentModule.kt
 | |____scope
 | | |____ActivityScope.kt
 | | |____FragmentScope.kt
 |____lib   //提供抽象类及kotlin扩展
 | |____adapter
 | | |____recyclerview
 | | | |____BaseViewAdapter.kt
 | | | |____BindingViewHolder.kt
 | | | |____Dummy.kt
 | | | |____ItemClickPresenter.kt
 | | | |____MultiTypeAdapter.kt
 | | | |____PagedAdapter.kt
 | | |____viewpager
 | | | |____AbstractPagerAdapter.kt
 | |____extention 
 | | |____BaseExtens.kt
 | | |____NormalBind.kt
 | | |____ToastType.java
 | | |____ViewExtens.kt
 | |____network //网络库
 | | |____NetInterceptor.kt
 | | |____NetMgr.kt
 | | |____NetProvider.kt
 | | |____RequestHandler.kt
 |____model //model层用于获取数据 MVVM的M层
 | |____data    //数据模型
 | | |____Article.kt
 | | |____ArticleList.kt
 | | |____BaseResponse.kt
 | | |____Tag.kt
 | | |____TagList.kt
 | | |____User.kt
 | | |____UserModel.kt
 | |____local   //数据库
 | | |____AppDatabase.kt
 | | |____dao
 | | | |____ArticleDao.kt
 | | | |____UserDao.kt
 | |____remote  //网络请求
 | | |____api
 | | | |____PaoService.kt
 | | | |____UserService.kt
 | | |____BaseNetProvider.kt
 | | |____exception
 | | | |____ApiException.kt
 | | | |____EmptyException.kt
 | | |____Utils.kt
 | |----repository //提供viewmodel需要的数据
 | | |____PaoRepository.kt
 | | |____UserRepository.kt
 |____view  //UI层 MVVM 的V层，按功能划分，每个功能包含各自的View和ViewModel
 | |____article //文章模块
 | | |____ArticleDetailActivity.kt
 | | |____ArticleListFragment.kt
 | | |____viewmodel
 | | | |____ArticleDetailViewModel.kt
 | | | |____ArticleItemViewModel.kt
 | | | |____ArticleListViewModel.kt
 | |____auth    //认证模块
 | | |____LoginActivity.kt
 | | |____viewmodel
 | | | |____LoginViewModel.kt
 | |____BaseActivity.kt
 | |____BaseFragment.kt
 | |____code    //代码模块
 | | |____CodeDetailActivity.kt
 | | |____CodeFragment.kt
 | | |____CodeListFragment.kt
 | | |____viewmodel
 | | | |____CodeDetailViewModel.kt
 | | | |____CodeListViewModel.kt
 | |____helper  //帮助类
 | | |____AnimUtils.java
 | | |____ArticleType.java
 | | |____CodeType.java
 | | |____Constants.kt
 | | |____ImageUtil.kt
 | | |____ItemType.java
 | | |____ListPresenter.kt
 | | |____Navigator.kt
 | | |____SpUtil.kt
 | | |____SystemBarHelper.java
 | | |____Utils.kt
 | |____home    //首页
 | | |____HomeFragment.kt
 | | |____MainActivity.kt
 | | |____RecentFragment.kt
 | | |____viewmodel
 | | | |____MainViewModel.kt
 | | | |____RecentViewModel.kt
 | |____mine    //我的收藏、文章等
 | | |____CollectionListFragment.kt
 | | |____MyArticleFragment.kt
 | | |____MyCollectFragment.kt
 | | |____viewmodel
 | | | |____MyArticleViewModel.kt
 | | | |____MyCollectViewModel.kt
 | |____search
 | | |____RecentSearchFragment.kt
 | | |____SearchActivity.kt
 | | |____SearchResultFragment.kt
 | | |____viewmodel
 | | | |____RecentSearchViewModel.kt
 | | | |____SearchViewModel.kt
 | |____setting //设置
 | | |____SettingsActivity.kt
 | |____transitions
 | | |____CircularReveal.java
 | | |____FabTransform.java
 | | |____GravityArcMotion.java
 | | |____MorphDrawable.java
 | | |____MorphTransform.java
 | | |____StartAnimatable.java
 | | |____TransitionUtils.java
 |____viewmodel     //ViewModel 基类
 | |____BaseViewModel.kt      
 | |____PagedViewModel.kt   //用于分页
 | |____StateModel.kt       //用于控制状态，包含toat和展示空页面
 |____widget    //自定义view
 | |____ColorBrewer.java
 | |____recyclerview
 | | |____CoverFlowLayoutManger.java
 | | |____RecyclerCoverFlow.java
 
 ```