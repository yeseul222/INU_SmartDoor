package com.example.inu_smartdoor;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class webcam extends Activity {

String url;
EditText edit1;
WebView ipcamView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webcam);

		//IPTime에서 설정한 DDNS 주소
		url = "http://INUdoor5555.iptime.org:5555/video";
		//url = "http://192.168.219.145:5555/video";

		edit1 = findViewById(R.id.editText1);
		edit1.setText(url);

		//Webcam영상을 웹뷰를 통해 보여줌
		ipcamView = findViewById(R.id.webView1);
		ipcamView.getSettings().setJavaScriptEnabled(true);
		ipcamView.setWebViewClient(new WebViewClient());
		ipcamView.loadUrl(url);
	}
	
}