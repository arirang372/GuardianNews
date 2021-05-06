package com.sung.guardiannews.view.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.sung.guardiannews.R

class CustomTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView
        )
        val fontPath = a.getString(R.styleable.CustomTextView_textFontPath)
        a.recycle()
        setTypeFace(fontPath)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        val fontPath = a.getString(R.styleable.CustomTextView_textFontPath)
        a.recycle()
        setTypeFace(fontPath)
    }

    fun setTypeFace(fontPath: String?) {
        if (fontPath != null) {
            val myTypeFace = Typeface.createFromAsset(this.context.assets, fontPath)
            this.typeface = myTypeFace
        }
    }
}