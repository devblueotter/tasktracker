package com.blueotter.hainguyenminh.tasktracker.ui.login;

import com.blueotter.hainguyenminh.tasktracker.base.BasePresenter;
import com.blueotter.hainguyenminh.tasktracker.base.BaseView;
import com.facebook.AccessToken;

/**
 * Created by hainguyen on 3/17/18.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void gotoTasksActivity();
    }

    interface Presenter extends BasePresenter {

        void saveAccessToken(AccessToken accessToken);
    }
}
