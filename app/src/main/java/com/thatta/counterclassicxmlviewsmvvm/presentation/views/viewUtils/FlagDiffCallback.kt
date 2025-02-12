package com.thatta.counterclassicxmlviewsmvvm.presentation.views.viewUtils

import androidx.recyclerview.widget.DiffUtil

class FlagDiffCallback(
    private val oldList: List<Int>,
    private val newList: List<Int>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}