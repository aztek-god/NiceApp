package com.sdv.diff_util

interface DiffUtilItem {
    fun isItemTheSame(item: Any): Boolean
    fun isContentTheSame(item: Any): Boolean
}