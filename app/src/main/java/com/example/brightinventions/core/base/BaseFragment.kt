package com.example.brightinventions.core.base

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        initializeDaggerDependency()
        super.onAttach(context)
    }

    abstract fun initializeDaggerDependency()

}