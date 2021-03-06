package com.sdv.niceapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sdv.niceapp.R
import com.sdv.niceapp.util.logd
import kotlinx.android.synthetic.main.bottom_sheet_layout.*

class BottomSheetDialog : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorites -> {
                    logd()
                    Navigation.findNavController(
                        activity!!,
                        R.id.fragment
                    )
                        .navigate(R.id.action_homeFragment_to_bookmarkFragment)
                }
            }
            true
        }
    }
}