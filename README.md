
adb shell am start -n com.saint.struct/.ui.activity.MainActivity

android jetpack
DataStore Share优化
ROOM
Page
kotlin
compose
coroutine
flow
navigator

RemoteMediator 和 PagingSource 的区别：
PagingSource：实现单一数据源以及如何从该数据源中查找数据，推荐用于加载有限的数据集（本地数据库），例如 Room，数据源的变动会直接映射到 UI
上；
RemoteMediator：实现加载网络分页数据并更新到数据库中，但是数据源的变动不能直接映射到 UI 上；
可以使用 RemoteMediator 实现从网络加载分页数据更新到数据库中，使用 PagingSource 从数据库中查找数据并显示在 UI 上

https://github.com/dailystudio/devbricksx-android

https://github.com/bytedance/scene

实现类	
核心库	GlobalScope
工厂函数 MainScope( ) CoroutineScope( )

平台支持
ViewModel.viewModelScope LifecycleOwner.lifecycleScope

扩展函数	
协程构建器 
launch( )
启动一个协程但不会阻塞调用线程,必须要在协程作用域(CoroutineScope)中才能调用,返回值是一个Job
async( )
启动一个协程但不会阻塞调用线程,必须要在协程作用域(CoroutineScope)中才能调用。以Deferred对象的形式返回协程任务。
返回值泛型T同runBlocking类似都是协程体最后一行的类型

作用域函数	
普通函数	runBlocking()
启动一个新的协程并阻塞调用它的线程，直到里面的代码执行完毕,返回值是泛型T，就是你协程体中最后一行是什么类型，最终返回的是什么类型T就是什么类型

挂起函数 coroutineScope( )supervisorScope( )withContext( )withTimeout( )withTimeoutOrNull( )
挂起函数创建的子协程是串行运行，协程构建器创建的子协程是并行运行。

原文链接：https://blog.csdn.net/HugMua/article/details/126044114

ContextScope
上下文作用域，intermal修饰未对外暴露，根据指定的上下文创建协程作用域。使用工厂函数 MainScope()
、CoroutineScope() 传入上下文对象参数，获取到的就是 ContextScope 实例。

mainScope()
默认上下文使用 SupervisorJob()+Dispatchers.Main 的协程作用域。该调度器会绑定到主线程（在Android中就是 UI Thread） ，
在 onDestroy() 中调用 scope.cancel() 关闭协程。可用于主动控制协程的生命周期，对Android开发意义在于避免内存泄漏。

CoroutineScope( )
根据自定义上下文创建协程作用域（如果上下文中没有 Job 会自动创建一个用于结构化并发）。
CoroutineScope是一个只包含 coroutineContext 属性的接口，
虽然我们可以创建一个实现类但这不是一个流行的做法，而且存在不小心在其它地方取消作用域。
通常我们会更喜欢通过对象来启动协程，最简单的办法是使用 CoroutineScope() 工厂函数，它用传入的上下文来创建作用域

Android平台支持
3.3.1 ViewModel.viewModelScope( )
是ViewModelKTX 提供的扩展属性。使用的上下文是SupervisorJob() + Dispatchers.Main.immediate，
ViewModel销毁时协程作用域会自动被 cancel，避免造成协程泄漏（内存泄漏）。

LifecycleOwner.lifecycleScope( )
是 LifeCycleKTX 提供的扩展属性。使用的上下文是SupervisorJob() + Dispatchers.Main.immediate，
在 Activity/Fragment 销毁时协程作用域会自动被 cancel，避免造成协程泄漏（内存泄漏）。

协程构建器 CoroutineBuilder
launch() 和 async() 是 CoroutineScope 接口的扩展函数，继承了它的 coutineContext 来自动传播其上下文元素和可取消性。挂起函数需要相互传递
Continuation，每个挂起函数都要由另一个挂起函数或协程调用，这一切都是从协程构建器创建协程开始的，
即作用域函数只能创建子协程，协程构建器能创建根协程或子协程（因为它通过实例调用可以存在于普通函数中）。
挂起函数创建的子协程是串行运行，协程构建器创建的子协程是并行运行。

/******************************************************/
如果某个协程满足以下几点，那它里面的子协程将会是同步执行的:
父协程的协程调度器是处于Dispatchers.Main情况下启动。
同时子协程在不修改协程调度器下的情况下启动。
如果其中的某一个子协程将他的协程调度器修改为非Dispatchers.Main，那么这个子协程将会与其他子协程并发执行
/******************************************************/
GlobalScope.launch(Dispatchers.Main) {
for (index in 1 until  10) {
    //同步执行
        launch {
            Log.d("launch$index", "启动一个协程")
        }
    }
}

GlobalScope.launch {
    for (index in 1 until  10) {
        //并发执行
        launch {
            Log.d("launch$index", "启动一个协程")
        }
    }
  }

CoordinatorLayout部分属性
scroll：所有想滚动出屏幕的view都需要设置这个flag，没有设置这个flag的view将被固定在屏幕顶部。
enterAlways：设置这个flag时，向下的滚动都会导致该view变为可见，启用“快速返回模式”。
enterAlwaysCollapsed：当你的视图已经设置minHeight属性又使用此标志时，你的视图只能以最小高度进入，只有当滚动视图到达顶部时才扩大到完整高度。
snap：磁性吸附，在滑动到一定程度后松手可以自动缩到顶端或自动拉伸到最大。


CollapsingToolbarLayout部分属性

app:contentScrim CollapsingToolbarLayout完全折叠后的背景颜色
app:titleEnabled 是否显示标题 app:title 标题 app:toolbarId toolbar 对应的view id
app:statusBarScrim 折叠后状态栏的背景
app:scrimVisibleHeightTrigger 设置收起多少高度时，显示ContentScrim的内容
app:scrimAnimationDuration 展开状态和折叠状态之间，内容转换的动画时间
app:expandedTitleTextAppearance 布局张开的时候title的样式
app:expandedTitleMarginTop 布局张开的时候title的margin top
app:expandedTitleMarginStart 布局张开的时候title的margin start
app:expandedTitleMarginEnd 布局张开的时候title的margin end
app:expandedTitleMarginBottom 布局张开的时候title的margin bottom
app:expandedTitleMargin 布局张开的时候title的margin
app:expandedTitleGravity 布局张开的时候title的位置
app:collapsedTitleTextAppearance 布局折叠的时候title的样式
app:collapsedTitleGravity 布局折叠的时候title的gravity