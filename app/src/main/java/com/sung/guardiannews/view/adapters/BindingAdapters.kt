package com.sung.guardiannews.view.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.view.GuardianArticleListAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("articles")
    fun setArticles(view: RecyclerView, articles: List<Article>) {
        if(!articles.isNullOrEmpty()) {
            view.adapter = GuardianArticleListAdapter().apply {
                submitList(articles)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("url")
    fun setImageResource(imageView: ImageView, url: String) {
        Picasso.get().load(url).into(imageView)
    }
}