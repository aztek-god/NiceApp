package com.sdv.diff_util

interface DiffUtilItem {
    fun isItemTheSame(item: Any): Boolean = this == item
    fun isContentTheSame(item: Any): Boolean = this == item
}