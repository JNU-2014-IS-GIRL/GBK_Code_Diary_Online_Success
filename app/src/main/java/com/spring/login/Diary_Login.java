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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Diary_Login extends Activity{

	//为user_log_layout布局的各项内容进行初始化
	private EditText user_Login_Name = null;
	private EditText user_Login_Password = null;
	private CheckBox user_Login_CheckBox = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//放置user_login_layout布局
		setContentView(R.layout.user_login_layout);
		//创建新的活动
		SysApplication.getInstance().addActivity(this);

		//将user_login_layout布局各项控件实例化
		user_Login_Name = (EditText)findViewById(R.id.user_Login_Name);
		user_Login_Password = (EditText)findViewById(R.id.user_Login_Password);
		user_Login_CheckBox = (CheckBox)findViewById(R.id.user_Login_CheckBox);
		
		//拿到屏幕的大小
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
		//int width			=this.getWindowManager().getDefaultDisplay().getWidth();
		//上面一行是原本注释掉的，是另一种获得屏幕宽度的方法？
		int width = dm.widthPixels;//获得屏幕宽度
		//dm.heightPixels;//获得屏幕高度
		Window window			=this.getWindow();
		LayoutParams params		=window.getAttributes();
		params.width			=width;
		this.getWindow().setAttributes(params);
		
		Diary_Login_Initialize();
		
		//CheckBox消息响应
		user_Login_CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences userInfo = getSharedPreferences("user_info",0);
				if(isChecked){
					//如果被选中
					userInfo.edit().putBoolean("is_Remember_Password", true).commit();
				}
				else{
					//如果未被选中
					userInfo.edit().putBoolean("is_Remember_Password", false).commit();
				}
			}
		});
		
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

	public void onClickListener_Login(View view){
		
		if(is_Success_Login()){
			
			this.finish();

			Intent intent = new Intent();
			intent.setClass(this, MyMenu.class);

			startActivity(intent);
			}	
		}
	
	public boolean is_Success_Login(){

		String userName = user_Login_Name.getText().toString();
		String userPassword = user_Login_Password.getText().toString();

		if(userName == null){
			Toast.makeText(Diary_Login.this, "请输入用户名!",Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(userPassword == null){
			Toast.makeText(Diary_Login.this, "请输入密码!",Toast.LENGTH_SHORT).show();
			return false;
		}
		else {
			SharedPreferences userInfo = getSharedPreferences("user_info",0);
			String user_Register_Name = userInfo.getString("user_Register_Name", "");
			String user_Register_Password = userInfo.getString("user_Register_Password", "");
			if(userName.equals(user_Register_Name) && userPassword.equals(user_Register_Password)){
				return true;
			}
			else{
				
				Toast.makeText(Diary_Login.this, "密码错误!",Toast.LENGTH_SHORT).show();
				user_Login_Password.setText("");
				userInfo.edit().putBoolean("is_Remember_Password", false).commit();
				return false;
			}
		}
	}
	
	public void Diary_Login_Initialize(){
		SharedPreferences userInfo = getSharedPreferences("user_info",0);
		boolean is_Remember_Password = userInfo.getBoolean("is_Remember_Password", false);
		if(is_Remember_Password){
			user_Login_Name.setText(userInfo.getString("user_Register_Name", ""));
			user_Login_Password.setText(userInfo.getString("user_Register_Password", ""));
			user_Login_CheckBox.setChecked(true);
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
