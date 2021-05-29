package com.thedung.androidtvstructure.classes.extension

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

fun TextView.mixClickableSpan(string: String?, spanMap: Map<String, ClickableSpan>) {
    val currentText = SpannableString(string ?: text)
    spanMap.entries.forEach {
        val index = currentText.indexOf(it.key)
        if (index > -1) {
            currentText.setSpan(
                it.value,
                index,
                index + it.key.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    movementMethod = LinkMovementMethod.getInstance()
    setText(currentText, TextView.BufferType.SPANNABLE)
}

fun TextView.mixSpan(string: String?, highlight: String, color: Int) {
    val currentText = SpannableString(string ?: text)
    val index = currentText.indexOf(highlight)
    if (index > -1) {
        currentText.setSpan(
            ForegroundColorSpan(color),
            index,
            index + highlight.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    setText(currentText, TextView.BufferType.SPANNABLE)
}

fun TextView.removeLast() {
    text = text.replace(".$".toRegex(), "")
}

fun TextView.fromHtml(string: String) {
    text = if (Build.VERSION.SDK_INT >= 24)
        Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY)
    else
        Html.fromHtml(string)
}

fun TextView.clearDrawable() {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
}

fun TextView.setDrawableStart(drawableId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
}

fun TextView.setDrawableEnd(drawableId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableId, 0)
}

fun TextView.setDrawableTop(drawableId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(0, drawableId, 0, 0)
}

fun TextView.setDrawableBottom(drawableId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawableId)
}

fun TextView.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}