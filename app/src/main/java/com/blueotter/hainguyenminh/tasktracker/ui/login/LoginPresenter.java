package com.blueotter.hainguyenminh.tasktracker.ui.login;

import com.blueotter.hainguyenminh.tasktracker.data.local.prefs.SharedPreferenceHelper;
import com.facebook.AccessToken;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hainguyen on 3/17/18.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
        this.loginView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void saveAccessToken(AccessToken accessToken) {
        SharedPreferenceHelper.getInstance(loginView.getContext())
                .put(SharedPreferenceHelper.PREF_KEY_FACEBOOK_ACCESS_TOKEN, accessToken);
        FirebaseDatabase.getInstance().getReference().child(accessToken.getUserId());
        loginView.gotoTasksActivity();
    }
}
