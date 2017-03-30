package com.khinthirisoe.sampletestapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.khinthirisoe.sampletestapplication.R;
import com.khinthirisoe.sampletestapplication.adapter.BookListsAdapter;
import com.khinthirisoe.sampletestapplication.data.api.RetrofitClient;
import com.khinthirisoe.sampletestapplication.data.model.Books;
import com.khinthirisoe.sampletestapplication.data.model.Datum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookListsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_book_item)
    RecyclerView recyclerBookItem;
    @BindView(R.id.progress_view)
    CircularProgressView progressView;

    BookListsAdapter adapter;

    private static final String TAG = BookListsActivity.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_lists);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        GridLayoutManager layoutManager = new GridLayoutManager(BookListsActivity.this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSpanCount(2);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter != null) {
                    switch (adapter.getItemViewType(position)) {
                        case 1:
                            return 1;
                        case 0:
                            return 2;
                        default:
                            return -1;
                    }
                } else {
                    return -1;
                }
            }
        });

        recyclerBookItem.setLayoutManager(layoutManager);

        loadBooks();
    }

    private void loadBooks() {

        progressView.setVisibility(View.VISIBLE);
        RetrofitClient.getApiInterface().getBookLists()
                .enqueue(new Callback<Books>() {
                    @Override
                    public void onResponse(Call<Books> call, Response<Books> response) {

                        Books books = response.body();
                        books.setTotal(response.body().getTotal());
                        books.setData(response.body().getData());

                        List<Datum> data = new ArrayList<>();

                        for (int i = 0; i < books.getData().size(); i++) {
                            data.add(books.getData().get(i));
                        }

                        adapter = new BookListsAdapter(BookListsActivity.this, data);
                        recyclerBookItem.setAdapter(adapter);

                        progressView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<Books> call, Throwable t) {

                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_lists, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_profile:
                startActivity(new Intent(BookListsActivity.this, ProfileActivity.class));
                break;
            case R.id.item_singout:
                SignOut();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    private void SignOut() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Snackbar.make(toolbar, "sign out successful", Snackbar.LENGTH_SHORT).show();
                        startActivity(new Intent(BookListsActivity.this,LoginActivity.class));
                        finish();
                    }
                });
    }
}
