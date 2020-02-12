package com.example.bloodbagbb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.bloodbagbb.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WhyDonateBloodActivity extends AppCompatActivity {
    private WebView mWebView;
    public static final String TAG = "WhyNeed";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why_donate_blood);
        initViews();
        handleToolBar();
        getHtmlFromAsset();
        loadHtmlPage();
    }

    private void handleToolBar() {
        final Toolbar tool = (Toolbar)findViewById(R.id.toolBar);
        CollapsingToolbarLayout c = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolBar);
        AppBarLayout appbar = (AppBarLayout)findViewById(R.id.appBarLayout);
        tool.setTitle("");
        setSupportActionBar(tool);
        c.setTitleEnabled(false);

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isVisible = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    tool.setTitle("Why Donate Blood");
                    tool.setNavigationIcon(R.drawable.ic_arrow);
                    isVisible = true;
                } else if(isVisible) {
                    tool.setTitle("");
                    isVisible = false;
                }
            }
        });

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initViews() {

        mWebView = findViewById(R.id.butterfly_webview);
    }

    private String getHtmlFromAsset() {
        InputStream is;
        StringBuilder builder = new StringBuilder();
        String htmlString = null;
        try {
            is = getAssets().open(getString(R.string.butterfly_html));
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                htmlString = builder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlString;
    }

    private void loadHtmlPage() {
        String htmlString = getHtmlFromAsset();
        if (htmlString != null)
            mWebView.loadDataWithBaseURL("file:///android_asset/", htmlString, "text/html", "UTF-8", null);

        else
            Toast.makeText(WhyDonateBloodActivity.this, R.string.no_such_page, Toast.LENGTH_LONG).show();
    }

}
