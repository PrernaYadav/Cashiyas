package com.bounside.mj.cashiya;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ChildLifeInsuranceActivity extends AppCompatActivity {

    private WebView childLifeInsuranceWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_life_insurance);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Child Life Insurance");
        }

        childLifeInsuranceWebview = (WebView) findViewById(R.id.child_life_insurance_webview);
        startWebView("https://www.policyx.com/life-insurance/child-plan.php?utm_medium=CPA&utm_term=term&product=child&utm_source=322&src_id=322&sub_id=&agent_id=72000");

    }

    private void startWebView(String url) {

        childLifeInsuranceWebview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(ChildLifeInsuranceActivity.this);
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

        childLifeInsuranceWebview.getSettings().setJavaScriptEnabled(true);

        childLifeInsuranceWebview.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}