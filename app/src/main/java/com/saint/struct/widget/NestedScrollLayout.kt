package com.saint.struct.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent

class NestedScrollLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.scrollViewStyle
) : FrameLayout(context, attrs, defStyleAttr), NestedScrollingParent