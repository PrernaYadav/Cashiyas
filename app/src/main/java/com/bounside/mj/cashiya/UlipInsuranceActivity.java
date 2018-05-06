package com.bounside.mj.cashiya;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UlipInsuranceActivity extends AppCompatActivity {

    private WebView ULIPLifeInsuranceWebview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulip_insurance);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("ULIP Life Insurance");
        }

        ULIPLifeInsuranceWebview = (WebView) findViewById(R.id.ULIP_insurance_webview);
        startWebView("https://www.policyx.com/life-insurance/ULIP-plans.php?utm_medium=CPA&utm_term=term&product=ulip&utm_source=322&src_id=322&sub_id=&agent_id=72000");

    }

    private void startWebView(String url) {

        ULIPLifeInsuranceWebview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(UlipInsuranceActivity.this);
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

        ULIPLifeInsuranceWebview.getSettings().setJavaScriptEnabled(true);

        ULIPLifeInsuranceWebview.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
