package com.spring.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

public class Diary_Register extends Activity{

	private EditText user_Name = null;
	private EditText user_Password = null;
	private EditText user_Password_Next = null;
			
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.user_register_layout);
	
	user_Name = (EditText)findViewById(R.id.user_Register_Name);
	user_Password = (EditText)findViewById(R.id.user_Register_Password);
	user_Password_Next = (EditText)findViewById(R.id.user_Register_PasswordNext);
	
	SysApplication.getInstance().addActivity(this);
	//拿到屏幕的大小
	DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);

	int width = dm.widthPixels;//获得屏幕宽度
	//dm.heightPixels;//获得屏幕高度
	Window window			=this.getWindow();
	LayoutParams params		=window.getAttributes();
	params.width			=width;
	this.getWindow().setAttributes(params);
	
	}
	
	public void onClickListener_Register(View view){
		String user_getName = user_Name.getText().toString();
		String user_getPassword = user_Password.getText().toString();
		String user_getPassword_Next = user_Password_Next.getText().toString();
		if(user_getName == null ){
			Toast.makeText(Diary_Register.this, "请输入用户名!",Toast.LENGTH_SHORT).show();
			return;
		}
		if(user_getPassword == null){
			Toast.makeText(Diary_Register.this, "请输入密码!",Toast.LENGTH_SHORT).show();
			return;
		}
		if(user_getPassword_Next == null){
			Toast.makeText(Diary_Register.this, "请再次输入密码!",Toast.LENGTH_SHORT).show();
			return;
		}
		if(!user_getPassword.equals(user_getPassword_Next)){
			Toast.makeText(Diary_Register.this, "两次输入不相同!",Toast.LENGTH_SHORT).show();
			return;
		}
		SharedPreferences userInfo = getSharedPreferences("user_info",0);
		userInfo.edit().putBoolean("flag", true).commit();
		userInfo.edit().putString("user_Register_Name", user_getName).commit();
		userInfo.edit().putString("user_Register_Password", user_getPassword).commit();
		
		Toast.makeText(Diary_Register.this, "注册成功!",Toast.LENGTH_SHORT).show();
		this.finish();
		Intent intent		=new Intent();
		intent.setClass(getApplicationContext(), Diary_Login.class);
		startActivity(intent);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getY()>0 && event.getY()<50 && event.getX()>getWindow().getAttributes().width-50){
			this.finish();
			return super.onTouchEvent(event);
		}else{
			return false;
		}		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   

	        	SysApplication.getInstance().exit(); 

	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
