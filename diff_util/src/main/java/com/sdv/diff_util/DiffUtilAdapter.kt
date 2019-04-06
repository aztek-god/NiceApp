package com.sdv.diff_util

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class DiffUtilAdapter<T : DiffUtilItem, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    private val dataList: MutableList<T> = mutableListOf()

    protected val currentData: List<T> get() = dataList

    fun updateData(newData: List<T>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(DiffUtilCallback(dataList, newData))
        diffResult.dispatchUpdatesTo(this)
        dataList.clear()
        dataList.addAll(newData)
    }

    override fun getItemCount() = dataList.size

    private class DiffUtilCallback(
        private val oldItems: List<DiffUtilItem>,
        private val newItems: List<DiffUtilItem>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return oldItem.isItemTheSame(newItem)
        }

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return oldItem.isContentTheSame(newItem)
        }
    }

}