package com.bounside.mj.cashiya;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CriticalIllnessInsuranceActivity extends AppCompatActivity {

    private WebView criticalIllnessInsuranceWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critical_illness_insurance);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Critical Illness Insurance");
        }

        criticalIllnessInsuranceWebview = (WebView) findViewById(R.id.critical_illness_insurance_webview);
        startWebView("https://www.policyx.com/health-insurance/critical-illness-insurance.php?utm_medium=CPA&utm_term=health&product=health&utm_source=322&src_id=322&sub_id=&agent_id=72000");

    }

    private void startWebView(String url) {

        criticalIllnessInsuranceWebview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(CriticalIllnessInsuranceActivity.this);
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

        criticalIllnessInsuranceWebview.getSettings().setJavaScriptEnabled(true);

        criticalIllnessInsuranceWebview.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
