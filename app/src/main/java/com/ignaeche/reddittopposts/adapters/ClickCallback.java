package com.ignaeche.reddittopposts.adapters;

import android.view.View;

public interface ClickCallback<T> {
    void onClick(View view, T t);
}