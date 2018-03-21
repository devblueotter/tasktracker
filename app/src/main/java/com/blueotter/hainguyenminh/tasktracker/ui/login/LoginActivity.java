package com.blueotter.hainguyenminh.tasktracker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.blueotter.hainguyenminh.tasktracker.R;
import com.blueotter.hainguyenminh.tasktracker.data.local.prefs.SharedPreferenceHelper;
import com.blueotter.hainguyenminh.tasktracker.ui.task.TasksActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hainguyen on 3/17/18.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter loginPresenter;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private FacebookCallback<LoginResult> loginResultFacebookCallback;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDefaultValue();
        setAction();
    }

    private void initDefaultValue() {
        FirebaseApp.initializeApp(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        loginResultFacebookCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                databaseReference.child(loginResult.getAccessToken().getUserId());
                SharedPreferenceHelper.getInstance(getApplicationContext())
                        .put(SharedPreferenceHelper.PREF_KEY_FACEBOOK_ACCESS_TOKEN,
                                loginResult.getAccessToken());
                Intent intent = new Intent(LoginActivity.this, TasksActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Log.d("onCancel", "onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("onError", exception.getMessage());
            }
        };
    }

    private void setAction() {
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, loginResultFacebookCallback);
    }

    private void initView() {
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_button);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
