package com.aelsi2.practice14.utils.debouncers

import com.aelsi2.practice14.OnItemClickListener

class OnItemClickDebouncer(private val listener: OnItemClickListener, threshold : Long) : Debouncer(threshold), OnItemClickListener {
    override fun onItemClick(index: Int) {
        if (!CallAllowed()) return
        RegisterClick()
        listener.onItemClick(index)
    }
}