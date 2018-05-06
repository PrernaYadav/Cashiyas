package com.bounside.mj.cashiya;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TermLifeInsuranceActivity extends AppCompatActivity {

    private WebView termLifeInsuranceWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_life_insurance);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Term Life Insurance");
        }

        termLifeInsuranceWebview = (WebView) findViewById(R.id.term_life_insurance_webview);
        startWebView("https://www.policyx.com/life-insurance/compare-term-plan.php?utm_medium=CPA&utm_term=term&product=term&utm_source=322&src_id=322&sub_id=&agent_id=72000");

    }

    private void startWebView(String url) {

        termLifeInsuranceWebview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(TermLifeInsuranceActivity.this);
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

        termLifeInsuranceWebview.getSettings().setJavaScriptEnabled(true);

        termLifeInsuranceWebview.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
