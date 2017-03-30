package com.khinthirisoe.sampletestapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.khinthirisoe.sampletestapplication.R;
import com.khinthirisoe.sampletestapplication.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.progress_view)
    CircularProgressView progressView;

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int G_PLUS_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //bind view
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //google signIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @OnClick(R.id.btn_login)
    void onClickLogin(View view) {

        progressView.setVisibility(View.VISIBLE);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, G_PLUS_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == G_PLUS_SIGN_IN) {
            progressView.setVisibility(View.GONE);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            SharedPreferences sharedPreferences = this.getSharedPreferences(PrefUtils.PREF_FILE_NAME, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PrefUtils.PREF_ACCOUNT_NAME, account.getDisplayName());
            editor.putString(PrefUtils.PREF_EMAIL, account.getEmail());
            editor.putString(PrefUtils.PREF_PHOTO, String.valueOf(account.getPhotoUrl()));
            editor.apply();

            startActivity(new Intent(LoginActivity.this, BookListsActivity.class));
            finish();

        } else {
            Snackbar.make(toolbar, "please signIn or check your connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Snackbar.make(toolbar, "connection failed", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> optionalPendingResult = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (optionalPendingResult.isDone()) {

            GoogleSignInResult result = optionalPendingResult.get();
            handleSignInResult(result);
        } else {

            progressView.setVisibility(View.VISIBLE);
            optionalPendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    progressView.setVisibility(View.GONE);
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

}
