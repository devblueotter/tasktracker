package com.blueotter.hainguyenminh.tasktracker.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.blueotter.hainguyenminh.tasktracker.R;
import com.blueotter.hainguyenminh.tasktracker.data.local.prefs.SharedPreferenceHelper;
import com.blueotter.hainguyenminh.tasktracker.ui.task.TasksActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import java.util.Arrays;

/**
 * Created by hainguyen on 3/17/18.
 */

public class LoginActivity extends AppCompatActivity
        implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter loginPresenter;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> loginResultFacebookCallback;
    private AccessToken accessToken;
    private Button btnConnectWithFb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        if (checkUseLogin()) {
            gotoTasksActivity();
            return;
        }
        initDefaultValue();
        setAction();
    }

    private boolean checkUseLogin() {
        accessToken = SharedPreferenceHelper.getInstance(this)
                .get(SharedPreferenceHelper.PREF_KEY_FACEBOOK_ACCESS_TOKEN, AccessToken.class);
        return accessToken != null;
    }

    private void initDefaultValue() {
        loginPresenter = new LoginPresenter(this);
        callbackManager = CallbackManager.Factory.create();
        loginResultFacebookCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginPresenter.saveAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                LoginManager.getInstance().logOut();
            }
        };
        LoginManager.getInstance().registerCallback(callbackManager, loginResultFacebookCallback);
    }

    @Override
    public void gotoTasksActivity() {
        Intent intent = new Intent(LoginActivity.this, TasksActivity.class);
        startActivity(intent);
        finish();
    }

    private void setAction() {
        btnConnectWithFb.setOnClickListener(this);
    }

    private void initView() {
        setContentView(R.layout.activity_login);
        btnConnectWithFb = findViewById(R.id.btnConnectWithFb);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnectWithFb:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
                break;
        }
    }
}
