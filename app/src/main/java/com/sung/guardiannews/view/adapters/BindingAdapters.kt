package com.sung.guardiannews.view.adapters

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Field
import com.sung.guardiannews.view.GuardianArticleListAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("articles")
    fun setArticles(view: RecyclerView, articles: List<Article>?) {
        if(articles != null) {
            view.adapter = GuardianArticleListAdapter().apply {
                submitList(articles)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, fields : Field) {
        if(TextUtils.isEmpty(fields.thumbnail)){
            Picasso.get().cancelRequest(imageView)
            imageView.setImageDrawable(null)
            return
        }
        Picasso.get().load(fields.thumbnail).into(imageView)
    }
}