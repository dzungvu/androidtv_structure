package com.thedung.androidtvstructure.ui.base.home.presenters

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.leanback.widget.RowPresenter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.thedung.androidtvstructure.R
import com.thedung.androidtvstructure.classes.delegates.viewBinding
import com.thedung.androidtvstructure.classes.extension.onClickDelay
import com.thedung.androidtvstructure.classes.extension.setDrawableStart
import com.thedung.androidtvstructure.data.models.entities.base.Header
import com.thedung.androidtvstructure.databinding.PresenterHomeHeaderBinding
import com.thedung.androidtvstructure.utils.GlideApp

class HomeHeaderPresenter(
    private var listener: HomeHeaderListener? = null,
    private val states: List<LiveData<Any>>?,
    private val lifecycle: LifecycleOwner?
) : RowPresenter() {

    init {
        selectEffectEnabled = false
    }

    override fun createRowViewHolder(parent: ViewGroup): ViewHolder {
        return ItemViewHolder(
            parent.viewBinding(PresenterHomeHeaderBinding::inflate),
            listener,
            states,
            lifecycle
        )
    }

    override fun onBindRowViewHolder(vh: ViewHolder, item: Any?) {
        if (vh is ItemViewHolder && item is Header) {
            vh.data = item
            vh.bind(item)
        }
    }

    class ItemViewHolder(
        val binding: PresenterHomeHeaderBinding,
        listener: HomeHeaderListener?,
        private val states: List<LiveData<Any>>?,
        private val lifecycle: LifecycleOwner?
    ) : ViewHolder(binding.root) {

        var data: Header? = null
        private var firstBind = true

        init {
            binding.btnAction.run {
                onClickDelay { listener?.onHeaderButtonClick(id, data) }
                setOnFocusChangeListener { v, hasFocus ->
                    listener?.onHeaderButtonFocusChanged(v.id, hasFocus, data)
                }
            }
            binding.btnDetail.run {
                onClickDelay { listener?.onHeaderButtonClick(id, data) }
                setOnFocusChangeListener { v, hasFocus ->
                    listener?.onHeaderButtonFocusChanged(v.id, hasFocus, data)
                }
            }
            lifecycle?.run {
                states?.forEach {
                    it.observe(this, { item ->
                        when (item) {
                            is Header -> {
                                data = item
                                handleActionBtn()
                            }
                        }
                    })
                }
            }
        }

        fun bind(item: Header) {
            GlideApp.with(view).load(item.imageBg)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.tvHeaderTitle.text = item.title
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.tvHeaderTitle.text = ""
                        return false
                    }

                }).into(binding.ivHeaderTitle)
            handleActionBtn()
            if (firstBind) {
                firstBind = false
                binding.btnAction.requestFocus()
            }
        }

        private fun handleActionBtn() {
            val (stringId, resId) = Pair(
                R.string.view_now,
                R.drawable.ic_home_view_now
            )
            binding.btnAction.run {
                setText(stringId)
                setDrawableStart(resId)
            }
        }
    }

    interface HomeHeaderListener {

        fun onHeaderButtonClick(btnId: Int, item: Any?)

        fun onHeaderButtonFocusChanged(id: Int, focused: Boolean, item: Any?)
    }
}