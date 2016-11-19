package com.spring.login;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Diary_New extends Activity{
	
	private EditText new_Title_EditText = null;
	private EditText new_Words_EditText = null;
	private String sdPath;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_diary_layout);
		
		new_Title_EditText = (EditText)findViewById(R.id.new_Title_EditText);
		new_Words_EditText = (EditText)findViewById(R.id.new_Words_EditText);
		
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			//在SD卡张建立文件夹
			File sdCardDir = Environment.getExternalStorageDirectory();
			sdPath = sdCardDir.getPath()+"/DiaryBook";
			File file = new File(sdPath);
			if(!file.exists()){
				file.mkdir();
			}
		}
		
	}
	

	public void new_Return_OnClickListener(View view){

		this.finish();
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), MyMenu.class);
		startActivity(intent);	
	}
	//保存按钮消息响应
	public void new_Save_OnClickListener(View view){
		if(saveFile()){
		this.finish();
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), MyMenu.class);
		startActivity(intent);
		Toast.makeText(Diary_New.this, "保存成功",Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean saveFile(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

			String new_File_Name = getDate()+".txt";

			File saveFile = new File(sdPath,new_File_Name);
			byte[] new_Title = get_New_Title();
			byte[] new_Words = get_New_Words();
			if(new_Title == null){
				Toast.makeText(getApplication(), "请添加标题!", Toast.LENGTH_SHORT).show();	
				return false;
			}
			if(new_Words == null){
				Toast.makeText(getApplication(), "随便写点什么吧！", Toast.LENGTH_SHORT).show();
				return false;
			}
			try {
				FileOutputStream outStream = new FileOutputStream(saveFile);
				try {
					outStream.write(new_Title);
					outStream.write(new_Words);
					outStream.close();
					return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return false;
	}
	
	public byte[] get_New_Title(){
		String new_Title = new_Title_EditText.getText().toString();
		int length = new_Title.length();
		if(length != 0){
			byte[] str = (new_Title_EditText.getText().toString()+"\n").getBytes();

			return str;
		}

		return null;
	}
	public byte[] get_New_Words(){
		String new_Words = new_Words_EditText.getText().toString();
		int length = new_Words.length();
		if(length != 0){
		byte[] str = new_Words_EditText.getText().toString().getBytes();
		return str;
		}
		return null;
	}
	
	//获得当前时间
	@SuppressLint("SimpleDateFormat")
	public String getDate(){
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");       
	Date curDate = new Date(System.currentTimeMillis());//获取当前时间    
	String str = formatter.format(curDate); 
	return str;
	}
}
