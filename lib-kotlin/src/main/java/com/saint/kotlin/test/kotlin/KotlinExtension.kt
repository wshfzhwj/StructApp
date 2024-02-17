package com.saint.kotlin.test.kotlin
//StandardKt加备注
class KotlinExtension {
    //定义两个泛型T,R，T.let代表对任意类T添加let扩展函数，这个扩展函数的返回值为R
    //参数block是一个函数且类型为(T) -> R
    //由于let返回值为R且block返回值也为R，所以直接返回block函数的返回值
    //调用let的是T，所以let闭包里this就代表T，block的参数为T，block应传入this，所以在block闭包内使用it即代表T
    inline fun <T, R> T.let(block: (T) -> R): R = block(this)

    //定义两个泛型T,R，T.run代表对任意类T添加run扩展函数，这个扩展函数的返回值为R
    //参数block是一个函数且类型为T.() -> R
    //由于run返回值为R且block返回值也为R，所以直接返回block函数的返回值
    //T.()代表给T添加一个匿名扩展函数，即block函数是T的匿名扩展函数，所以block函数闭包内的this即代表T
//    getItem(position)?.run{
//        holder.tvNewsTitle.text = StringUtils.trimToEmpty(titleEn)
//        holder.tvNewsSummary.text = StringUtils.trimToEmpty(summary)
//        holder.tvExtraInf = "难度：$gradeInfo | 单词数：$length | 读后感: $numReviews"
//        ...
//    }
    inline fun <T, R> T.run(block: T.() -> R): R = block()

    //with和run的区别在于，run是通过扩展函数实现，with通过顶层函数实现(不借助类或对象可以直接使用)
    //由于with是顶层函数，闭包内不包含上下文，所以调用block函数时需要指定T的对象进行调用，这也导致了with相对于run需要多传入一个参数T
    //对一个对象实例调用多个方法
//    with(item){
//        holder.tvNewsTitle.text = StringUtils.trimToEmpty(titleEn)
//        holder.tvNewsSummary.text = StringUtils.trimToEmpty(summary)
//        holder.tvExtraInf.text = "难度：$gradeInfo | 单词数：$length | 读后感: $numReviews"
//        ...
//    }
    inline fun <T, R> with(receiver: T, block: T.() -> R): R = receiver.block()

    //相较于run，apply只有一个泛型，即apply是T的扩展函数，返回的也是T
    //block依旧为T的匿名扩展函数，apply闭包包含T的上下文关系，直接在闭包调用block，返回this
    //配置对象的属性（apply）这对于配置未出现在对象构造函数中的属性非常有用。
//    mSheetDialogView = View.inflate(activity, R.layout.biz_exam_plan_layout_sheet_inner, null).apply{
//        course_comment_tv_label.paint.isFakeBoldText = true
//        course_comment_tv_score.paint.isFakeBoldText = true
//        course_comment_tv_cancel.paint.isFakeBoldText = true
//        course_comment_tv_confirm.paint.isFakeBoldText = true
//        course_comment_seek_bar.max = 10
//        course_comment_seek_bar.progress = 0
//    }

//    mSectionMetaData?.apply{
//        //mSectionMetaData不为空的时候操作mSectionMetaData
//    }?.questionnaire?.apply{
//        //questionnaire不为空的时候操作questionnaire
//    }?.section?.apply{
//        //section不为空的时候操作section
//    }?.sectionArticle?.apply{
//        //sectionArticle不为空的时候操作sectionArticle
//    }
    inline fun <T> T.apply(block: T.() -> Unit): T {
        block()
        return this
    }

    //also是T的扩展函数，所以also的闭包的上下文是T
    //匿名函数block的入参为T，所以block传入this
    //block是非扩展函数，所以block的闭包上下文为also的外层对象，在block的闭包里用it指向T
    inline fun <T> T.also(block: (T) -> Unit): T {
        block(this)
        return this
    }
}