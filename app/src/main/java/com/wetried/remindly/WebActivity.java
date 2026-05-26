package com.wetried.remindly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.wetried.remindly.databinding.ActivityWebBinding;

public class WebActivity extends AppCompatActivity {
    private ActivityWebBinding binding;
    private final static String URL = "URL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.siteMain.getSettings().setLoadsImagesAutomatically(true);
        binding.siteMain.getSettings().setJavaScriptEnabled(true);
        binding.siteMain.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        binding.siteMain.setWebViewClient(new WebViewClient());
        String url = getIntent().getStringExtra(URL);
        binding.siteMain.loadUrl(url);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.siteMain.canGoBack()) {
                    binding.siteMain.goBack();
                } else {
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(URL, url);
        return intent;
    }
}
