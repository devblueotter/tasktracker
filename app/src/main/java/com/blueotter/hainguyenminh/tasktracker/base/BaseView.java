package com.blueotter.hainguyenminh.tasktracker.base;

import android.content.Context;

/**
 * Created by hainguyen on 3/17/18.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    Context getContext();

}
