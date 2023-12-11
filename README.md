android jetpack
DataStore  Share优化
ROOM
Page
kotlin
compose
coroutine
flow
navigator


RemoteMediator 和 PagingSource 的区别：
PagingSource：实现单一数据源以及如何从该数据源中查找数据，推荐用于加载有限的数据集（本地数据库），例如 Room，数据源的变动会直接映射到 UI 上；
RemoteMediator：实现加载网络分页数据并更新到数据库中，但是数据源的变动不能直接映射到 UI 上；
可以使用 RemoteMediator 实现从网络加载分页数据更新到数据库中，使用 PagingSource 从数据库中查找数据并显示在 UI 上


https://github.com/dailystudio/devbricksx-android

https://github.com/bytedance/scene