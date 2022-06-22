package com.aelsi2.practice14.utils.debouncers;

public abstract class Debouncer {
    private final long threshold;
    private long lastClick;
    public Debouncer(long threshold){
        this.threshold = threshold;
        lastClick = System.currentTimeMillis();
    }
    protected boolean CallAllowed() {
        return System.currentTimeMillis() - lastClick > threshold;
    }
    protected void RegisterClick() {
        lastClick = System.currentTimeMillis();
    }
}