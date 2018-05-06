package com.bounside.mj.cashiya;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet {
	
	Context con;
	public CheckInternet(Context con) {
		this.con = con;
	}
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) con
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null;
	}
}
