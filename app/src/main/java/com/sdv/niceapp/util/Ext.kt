package com.sdv.niceapp.util

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

@BindingAdapter("app:imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide
        .with(imageView)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

fun verticalLayoutManager(context: Context, isReversed: Boolean = false): LinearLayoutManager {
    return LinearLayoutManager(context, RecyclerView.VERTICAL, isReversed)
}

fun logd(message: String = "", tag: String = "") {
    Log.d(tag, message)
}

fun <T> MutableList<T>.update(newList: List<T>) {
    clear()
    addAll(newList)
}

fun View.visible() {
    if (!isVisible) {
        visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (!isGone) {
        visibility = View.GONE
    }
}

fun shortToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun EditText.asObservable(): Observable<CharSequence> {
    return RxTextView
        .afterTextChangeEvents(this)
        .skipInitialValue()
        .map { it.view().text }
        .observeOn(AndroidSchedulers.mainThread())
}