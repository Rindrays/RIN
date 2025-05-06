package com.android.mad.assignments;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}