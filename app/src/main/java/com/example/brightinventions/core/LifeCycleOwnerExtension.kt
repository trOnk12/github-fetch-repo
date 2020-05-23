package com.example.brightinventions.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> Fragment.observe(liveData: LiveData<T>, onDataChanged: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer {
        it?.let { t -> onDataChanged(t) }
    })
}

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, onDataChanged: (T) -> Unit) {
    liveData.observe(this, Observer {
        it?.let { t -> onDataChanged(t) }
    })
}
