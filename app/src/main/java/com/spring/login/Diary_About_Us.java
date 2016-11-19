package com.spring.login;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;


public class Diary_About_Us extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_us_layout);
		
	}
	
	public void about_Us_OnClickListener(View view){
		this.finish();
	}
}
