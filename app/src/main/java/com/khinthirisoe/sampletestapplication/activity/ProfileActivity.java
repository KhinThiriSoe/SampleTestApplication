package com.khinthirisoe.sampletestapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.khinthirisoe.sampletestapplication.R;
import com.khinthirisoe.sampletestapplication.utils.PrefUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_photo)
    ImageView imagePhoto;
    @BindView(R.id.text_username)
    TextView textUserName;
    @BindView(R.id.text_email)
    TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_profile));

        SharedPreferences prefs = this.getSharedPreferences(PrefUtils.PREF_FILE_NAME, MODE_PRIVATE);
        String accountName = prefs.getString(PrefUtils.PREF_ACCOUNT_NAME, "");
        String email = prefs.getString(PrefUtils.PREF_EMAIL, "");
        String photoUrl = prefs.getString(PrefUtils.PREF_PHOTO,"");

        textUserName.setText(accountName);
        textEmail.setText(email);
        Picasso.with(ProfileActivity.this).load(photoUrl).into(imagePhoto);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
