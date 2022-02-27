package com.sergeenko.alexey.titangym.items

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem

abstract class BaseViewHolder<B: ViewBinding, T : IItem<*>>(val containerView: View) :
    FastAdapter.ViewHolder<T>(containerView) {

    val context: Context = itemView.context

    val resources: Resources = itemView.resources

    val binding = bind()

    abstract fun bind(): B

    fun getColor(@ColorRes color: Int) = ContextCompat.getColor(context, color)

    fun getDrawable(@DrawableRes drawable: Int) = ContextCompat.getDrawable(context, drawable)

    fun getDimen(@DimenRes dimenRes: Int) = resources.getDimension(dimenRes)

    fun getString(@StringRes stringRes: Int): String = context.getString(stringRes)

    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any): String = context.getString(stringRes, *formatArgs)

    override fun bindView(item: T, payloads: List<Any>) = Unit

    override fun unbindView(item: T) = Unit
}