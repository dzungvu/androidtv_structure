package com.thedung.androidtvstructure.ui.base.home.presenters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.leanback.widget.*
import androidx.leanback.widget.FocusHighlight.ZOOM_FACTOR_SMALL

class SectionListPresenter private constructor(
    private val startPadding: Int,
    private val topPadding: Int,
    private val endPadding: Int,
    private val bottomPadding: Int,
    private val itemHorizontalSpacing: Int,
    hoverCardPresenter: PresenterSelector?,
    headerPresenter: RowHeaderPresenter?
) : ListRowPresenter(ZOOM_FACTOR_SMALL) {

    init {
        selectEffectEnabled = true
        shadowEnabled = false
        hoverCardPresenterSelector = hoverCardPresenter
        this.headerPresenter = headerPresenter
    }

    private var isInit = false      //Prevent multi call of super

    @SuppressLint("RestrictedApi")
    override fun createRowViewHolder(parent: ViewGroup): RowPresenter.ViewHolder {
        if (!isInit) {
            isInit = true
            super.createRowViewHolder(parent)
        }
        val listRowView = ListRowView(parent.context)

        listRowView.gridView.run {
            windowAlignment = BaseGridView.WINDOW_ALIGN_LOW_EDGE
            windowAlignmentOffsetPercent = 0f
            windowAlignmentOffset = startPadding
            itemAlignmentOffsetPercent = 0f

            setPadding(startPadding, topPadding, endPadding, bottomPadding)
            horizontalSpacing = itemHorizontalSpacing
        }
        return ViewHolder(listRowView, listRowView.gridView, this)
    }

    override fun onBindRowViewHolder(holder: RowPresenter.ViewHolder?, item: Any?) {
        super.onBindRowViewHolder(holder, item)

        val gridView = (holder as? ViewHolder)?.gridView ?: return
        with(gridView as BaseGridView) {
            if (windowAlignmentOffsetPercent != 50f) {
                postDelayed({
                    windowAlignment = BaseGridView.WINDOW_ALIGN_BOTH_EDGE
                    windowAlignmentOffsetPercent = 50f
                    windowAlignmentOffset = 0
                }, 50)
            }
        }
    }

    class Builder {

        private var startPadding: Int = 0
        private var topPadding: Int = 0
        private var endPadding: Int = 0
        private var bottomPadding: Int = 0
        private var itemHorizontalSpacing: Int = 0
        private var hoverCardPresenter: PresenterSelector? = null
        private var headerPresenter: RowHeaderPresenter? = null

        fun startPadding(padding: Int) = apply { startPadding = padding }
        fun topPadding(padding: Int) = apply { topPadding = padding }
        fun endPadding(padding: Int) = apply { endPadding = padding }
        fun bottomPadding(padding: Int) = apply { bottomPadding = padding }
        fun itemHorizontalSpacing(spacing: Int) = apply { itemHorizontalSpacing = spacing }
        fun hoverCardPresenterSelector(presenter: PresenterSelector?) = apply { hoverCardPresenter = presenter }
        fun headerPresenter(presenter: RowHeaderPresenter) = apply { headerPresenter = presenter }

        fun build(): SectionListPresenter {
            return SectionListPresenter(
                startPadding,
                topPadding,
                endPadding,
                bottomPadding,
                itemHorizontalSpacing,
                hoverCardPresenter,
                headerPresenter
            )
        }
    }
}