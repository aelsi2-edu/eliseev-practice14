package com.aelsi2.practice14.utils.debouncers;

import android.view.View;

public class OnClickDebouncer extends Debouncer implements View.OnClickListener {
    View.OnClickListener listener;
    public OnClickDebouncer(View.OnClickListener listener, long threshold){
        super(threshold);
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if (!CallAllowed()) return;
        RegisterClick();
        listener.onClick(view);
    }
}
