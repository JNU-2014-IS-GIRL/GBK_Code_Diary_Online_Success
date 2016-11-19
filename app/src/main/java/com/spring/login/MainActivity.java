package com.spring.login;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity {

	//这个初始化的imageView， 后面会实例化
	private ImageView imageView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//从这里开始正式的代码部分一行一注释

		//设置布局效果，默认效果是非全屏，一个标题栏上写着 APP名字，然后下面是布局
		//这里设置布局为不含有标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//把activity_main布局放进去，这个布局中有一个空的imageView控件
		setContentView(R.layout.activity_main);

		//1:在每个activity被创建时加上SysApplication.getInstance().addActivity(this);
		//2:关闭整个程序  SysApplication.getInstance().exit();
		//所以现在要创建一个activity了，什么activity？不懂……this是什么东西，是哪来的
		SysApplication.getInstance().addActivity(this);

		//这是imageView的实例化
		//第一个只是一个名字，定义的时候可以随便起名字，最后那个就是刚才注册过id的那个imageView
		imageView = (ImageView) findViewById(R.id.imageView);
		//刚刚imageView控件中没有android：src添加图片内容，因此这里给它加上了图片资源
		imageView.setImageResource(R.drawable.background);
		//imageView.setImageResource(R.drawable.diary_background);

		//我猜测是说，如果是首次登陆，那么就进行用户设置
		//这个判断函数写在后面了
		if(isFirstLogin())
		{
			//intent：意图，在不同的activity之间跳转，并进行数据传递
			Intent intent = new Intent();
			//进入用户设置界面，并且用intent获得用户数据
			intent.setClass(getApplicationContext(), Diary_Login.class);
			//这是在干吗
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
			//这是在干吗，原有的注释又是打算放在哪里的
			startActivity(intent);
		}
		//不是首次登陆的情况
		else
		{
			//创建了一个新的intent来进行activity跳转和传递数据
			Intent intent = new Intent();
			//那么就进入日记界面并且，获取输入的内容？
			intent.setClass(getApplicationContext(), Diary_Register.class);
			//就是不知道这一句和下一句什么意思，以及后面的注释是什么意思
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
			startActivity(intent);
		}
		
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}

	//这是判断用户是不是第一次登陆的，是的话就进行用户注册
	public boolean isFirstLogin()
	{
		//这句话中的参数可以解释为？？？？
		SharedPreferences userInfo = getSharedPreferences("user_info",0);

		//如果之前设置的已经注册过的flag是false的话，那么就返回一个叫做is_First_Login的布尔型数据来设置为真？
		boolean is_First_Login = userInfo.getBoolean("flag", false);
		return is_First_Login;
	}
}
