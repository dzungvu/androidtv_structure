package com.thedung.androidtvstructure.classes.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commitNow
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.DetailsParallax
import androidx.leanback.widget.ObjectAdapter
import androidx.leanback.widget.VerticalGridView
import androidx.lifecycle.lifecycleScope
import com.thedung.androidtvstructure.R
import com.thedung.androidtvstructure.classes.extension.inflateView

abstract class BaseRowsFragment : BaseLeanbackFragment() {
    protected lateinit var rowsFragment: RowsSupportFragment
    private lateinit var detailParallax: DetailsParallax

    protected var adapter: ObjectAdapter? = null
        set(value) {
            field = value
            if (::rowsFragment.isInitialized)
                rowsFragment.adapter = field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            addRowsFragment()
            initViews()
            observeData()
        }
        initPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateView(R.layout.fragment_base_rows, container)
    }

    protected open fun addRowsFragment() {
        rowsFragment = RowsSupportFragment()
        childFragmentManager.commitNow { replace(R.id.fragmentContainer, rowsFragment) }
        rowsFragment.adapter = adapter
        setupVerticalGridLayout()
    }

    private fun setupVerticalGridLayout() {
        rowsFragment.verticalGridView.run {
            detailParallax = DetailsParallax()
            detailParallax.recyclerView = this
            itemAlignmentOffset = -focusTopSpacing()
            itemAlignmentOffsetPercent = VerticalGridView.ITEM_ALIGN_OFFSET_PERCENT_DISABLED
            windowAlignmentOffset = 0
            windowAlignmentOffsetPercent = VerticalGridView.WINDOW_ALIGN_OFFSET_PERCENT_DISABLED
            windowAlignment = VerticalGridView.WINDOW_ALIGN_NO_EDGE
        }
    }

    /**
     * Return the spacing from top to focused item in RowsSupportFragment
     * @return no spacing
     */
    protected open fun focusTopSpacing(): Int {
        return 0
    }
}