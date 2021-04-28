package com.rajkumar.shadidotcom.adapter

import android.view.View

interface IRecyclerClickListener<T> {
    fun onClick(t : T,view : View)
}