package com.thedung.androidtvstructure.ui.base.home

import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import com.thedung.androidtvstructure.classes.bases.BaseRowsFragment
import com.thedung.androidtvstructure.ui.base.home.presenters.HomeHeaderPresenter
import com.thedung.androidtvstructure.ui.base.home.presenters.SectionListPresenter

abstract class BaseHomeFragment : BaseRowsFragment(), HomeHeaderPresenter.HomeHeaderListener {

    /**
     * Define presenter for row
     */
    protected lateinit var mRowPresenterSelector: ClassPresenterSelector

    /**
     * Home rowAdapter
     */
    protected lateinit var mAdapter: ArrayObjectAdapter

    /**
     * Define presenter for row item
     */
    protected lateinit var mRowItemPresenterSelector: ClassPresenterSelector

    protected lateinit var sectionListPresenter: SectionListPresenter

    protected var selectedRow = 0

    override fun addRowsFragment() {
        super.addRowsFragment()
        initRowsFragmentListener()
    }

    override fun initPresenter() {
        TODO("Not yet implemented")
    }

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun onHeaderButtonClick(btnId: Int, item: Any?) {}

    override fun onHeaderButtonFocusChanged(id: Int, focused: Boolean, item: Any?) {}

    protected open fun initRowsFragmentListener() {
        rowsFragment.setOnItemViewSelectedListener { _, _, _, _ ->
            val newSelectedRow = rowsFragment.selectedPosition
            when {
                selectedRow == 0 && newSelectedRow != 0 -> {
                    selectedRow = newSelectedRow
                }
                selectedRow != 0 && newSelectedRow == 0 -> {
                    selectedRow = newSelectedRow
                }
                else -> {}
            }
        }
        rowsFragment.setOnItemViewClickedListener { _, item, _, _ ->
            onItemClick(item)
        }
    }

    abstract fun onItemClick(item: Any)
}