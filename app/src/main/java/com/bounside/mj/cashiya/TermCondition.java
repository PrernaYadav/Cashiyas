package com.bounside.mj.cashiya;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Tamanna on 10/8/2016.
 */

public class TermCondition extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termcondition);
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setTitle("Terms & Conditions");
            ac.setDisplayHomeAsUpEnabled(true);
        }
        webView= (WebView) findViewById(R.id.web_privacy_term);
        startWebView("http://cashiya.com/terms-&-conditions.php");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startWebView(String url) {

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(TermCondition.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                       // progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(url);
    }


    @Override

    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {

            super.onBackPressed();
        }
    }
}

