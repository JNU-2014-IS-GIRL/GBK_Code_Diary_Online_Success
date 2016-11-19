package com.spring.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.util.EncodingUtils;

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

public class Diary_Edit extends Activity{
	
	private EditText editTitle = null;
	private EditText editWords = null;
	private EditText edit_Title_EditText = null;
	private String file_Name = null;
	private String diary_Name = null;
	private String words = null;
	private String diary_Words = null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_diary_layout);

		editWords = (EditText)findViewById(R.id.edit_Words);
		edit_Title_EditText = (EditText)findViewById(R.id.edit_Title_EditText);
		getFileName();
		read_File_Line(file_Name);
		inputFile();
	}

	
	public void edit_Return_OnClickListener(View view){
		this.finish();
	}
	
	
	//保存按钮消息响应
	public void edit_Save_OnClickListener(View view){
		if(saveFile()){
			this.finish();
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MyMenu.class);
			startActivity(intent);
			Toast.makeText(Diary_Edit.this, "保存成功",Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean saveFile(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File sdCardDir = Environment.getExternalStorageDirectory();
			String sdPath = sdCardDir.getPath()+"/DiaryBook";
			File saveFile = new File(sdPath,file_Name+".txt");
			byte[] edit_Title = get_Edit_Title();
			byte[] edit_Words = get_Edit_Words();
			if(edit_Title == null){
				Toast.makeText(getApplication(), "请添加标题!", Toast.LENGTH_SHORT).show();	
				return false;
			}
			if(edit_Words == null){
				Toast.makeText(getApplication(), "随便写点什么吧！", Toast.LENGTH_SHORT).show();
				return false;
			}
			try {
				FileOutputStream outStream = new FileOutputStream(saveFile);
				try {

					outStream.write(edit_Title);
					outStream.write(edit_Words);

					outStream.close();
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
	public byte[] get_Edit_Title(){

		if(edit_Title_EditText.getText().toString().length() != 0){
			byte[] str = (edit_Title_EditText.getText().toString()+"\n").getBytes();

			return str;
		}

		return null;
	}
	public byte[] get_Edit_Words(){

		if(editWords.getText().toString().length() != 0){
		byte[] str = editWords.getText().toString().getBytes();
		return str;
		}
		return null;
	}
	
	public void getFileName(){
				
		//获得上一活动传递来的参数
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		file_Name = bundle.getString("fileName");
	}
	
	public void inputFile(){

		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			
			File sdCardDir = Environment.getExternalStorageDirectory();
			String sdPath = sdCardDir.getPath()+"/DiaryBook";
			File openFile = new File(sdPath,file_Name+".txt");
			try {
				@SuppressWarnings("resource")
				FileInputStream file = new FileInputStream(openFile);
				byte[] name = diary_Name.getBytes();
				file.skip(name.length+1);

				int length = file.available();
				byte[] buffer = new byte[length];
				file.read(buffer);
					words = EncodingUtils.getString(buffer, "UTF-8"); 
				//word = file.r

			}catch (IOException e) {
					// TODO Auto-generated catch block

				}
			editWords.setText(words);
		
		}	
	}
	
	@SuppressWarnings("resource")
	public void read_File_Line(String fileName){
		String sdPath = getSDCardPath();
		File openFile = new File(sdPath,fileName+".txt");
		try {
			FileInputStream file = new FileInputStream(openFile);
			
            InputStreamReader inputreader = new InputStreamReader(file);
            BufferedReader buffreader = new BufferedReader(inputreader);
            diary_Name = buffreader.readLine();

            edit_Title_EditText.setText(diary_Name);

		}catch (IOException e) {
			// TODO Auto-generated catch block

		}
	}
	
	public String getSDCardPath(){
		
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File sdCardDir = Environment.getExternalStorageDirectory();
			String sdPath = sdCardDir.getPath()+"/DiaryBook";
			return sdPath;
		}
		
		return null;
	}
	//获得当前时间
	@SuppressLint("SimpleDateFormat")
	public String getDate(){
	SimpleDateFormat   formatter    =   new    SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");       
	Date    curDate  =   new    Date(System.currentTimeMillis());//获取当前时间    
	String    str    =    formatter.format(curDate); 
	return str;
	}
}
