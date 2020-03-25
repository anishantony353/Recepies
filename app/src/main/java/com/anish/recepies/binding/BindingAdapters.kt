package com.anish.recepies.binding

import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anish.recepies.utilities.Utility.log
import com.anish.recepies.views.adapters.RecepieAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

object BindingAdapters {
    private val TAG = BindingAdapters::class.java.simpleName
    private val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .priority(Priority.HIGH)
        .skipMemoryCache(true)
        .signature(ObjectKey(System.currentTimeMillis().toString()))

    @JvmStatic
    @BindingAdapter("invalid")
    fun setError(editText: EditText, errorMsg: String?) {
        log(TAG, "setError()")
        log(TAG, "About to clear ")
        editText.text.clear()
        editText.requestFocus()
        editText.error = errorMsg
    }

    @JvmStatic
    @BindingAdapter("adapter_recyclerview")
    fun setRecyclerViewAdapter(rv: RecyclerView, adapter: RecepieAdapter?) {
        log(
            TAG,
            "setRecyclerViewAdapter()"
        )
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(rv.context)
        rv.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("toggle")
    fun toggle(rv: RecyclerView,value:Boolean) {
        log(
            TAG,
            "toggle()..${value}"
        )

        if(rv.layoutManager == null){
            return
        }
        when(value){
            false->{
                (rv.layoutManager as LinearLayoutManager).orientation =  RecyclerView.VERTICAL
            }
            true->{
                (rv.layoutManager as LinearLayoutManager).orientation =  RecyclerView.HORIZONTAL
            }
        }

    }

    @JvmStatic
    @BindingAdapter("setImgOnImageView")
    fun setImgOnImageView(view: ImageView, path: String?) {
        log(TAG, "setImgOnImageView()")
        Glide.with(view.context.applicationContext).load(path)
            .apply(options).into(view)
    }
}