package com.saint.struct.widget.nested;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

public interface NestedScrollingChild {
    /**
     * @param enabled 开启或关闭嵌套滑动
     */
    void setNestedScrollingEnabled(boolean enabled);

    /**
     * @return 返回是否开启嵌套滑动
     */
    boolean isNestedScrollingEnabled();

    /**
     * 沿着指定的方向开始滑动嵌套滑动
     * @param axes 滑动方向
     * @return 返回是否找到NestedScrollingParent配合滑动
     */
    boolean startNestedScroll(@ViewCompat.ScrollAxis int axes);

    /**
     * 停止嵌套滑动
     */
    void stopNestedScroll();

    /**
     * @return 返回是否有配合滑动NestedScrollingParent
     */
    boolean hasNestedScrollingParent();

    /**
     * 滑动完成后，将已经消费、剩余的滑动值分发给NestedScrollingParent
     * @param dxConsumed 水平方向消费的距离
     * @param dyConsumed 垂直方向消费的距离
     * @param dxUnconsumed 水平方向剩余的距离
     * @param dyUnconsumed 垂直方向剩余的距离
     * @param offsetInWindow 含有View从此方法调用之前到调用完成后的屏幕坐标偏移量，
     * 可以使用这个偏移量来调整预期的输入坐标（即上面4个消费、剩余的距离）跟踪，此参数可空。
     * @return 返回该事件是否被成功分发
     */
    boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                 int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow);

    /**
     * 在滑动之前，将滑动值分发给NestedScrollingParent
     * @param dx 水平方向消费的距离
     * @param dy 垂直方向消费的距离
     * @param consumed 输出坐标数组，consumed[0]为NestedScrollingParent消耗的水平距离、
     * consumed[1]为NestedScrollingParent消耗的垂直距离，此参数可空。
     * @param offsetInWindow 同上dispatchNestedScroll
     * @return 返回NestedScrollingParent是否消费部分或全部滑动值
     */
    boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed,
                                    @Nullable int[] offsetInWindow);

    /**
     * 将惯性滑动的速度和NestedScrollingChild自身是否需要消费此惯性滑动分发给NestedScrollingParent
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @param consumed NestedScrollingChild自身是否需要消费此惯性滑动
     * @return 返回NestedScrollingParent是否消费全部惯性滑动
     */
    boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);

    /**
     * 在惯性滑动之前，将惯性滑动值分发给NestedScrollingParent
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @return 返回NestedScrollingParent是否消费全部惯性滑动
     */
    boolean dispatchNestedPreFling(float velocityX, float velocityY);
}
