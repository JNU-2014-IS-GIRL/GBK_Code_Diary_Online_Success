package com.spring.login;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity {

	//�����ʼ����imageView�� �����ʵ����
	private ImageView imageView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//�����￪ʼ��ʽ�Ĵ��벿��һ��һע��

		//���ò���Ч����Ĭ��Ч���Ƿ�ȫ����һ����������д�� APP���֣�Ȼ�������ǲ���
		//�������ò���Ϊ�����б�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//��activity_main���ַŽ�ȥ�������������һ���յ�imageView�ؼ�
		setContentView(R.layout.activity_main);

		//1:��ÿ��activity������ʱ����SysApplication.getInstance().addActivity(this);
		//2:�ر���������  SysApplication.getInstance().exit();
		//��������Ҫ����һ��activity�ˣ�ʲôactivity����������this��ʲô��������������
		SysApplication.getInstance().addActivity(this);

		//����imageView��ʵ����
		//��һ��ֻ��һ�����֣������ʱ�������������֣�����Ǹ����Ǹղ�ע���id���Ǹ�imageView
		imageView = (ImageView) findViewById(R.id.imageView);
		//�ո�imageView�ؼ���û��android��src���ͼƬ���ݣ�����������������ͼƬ��Դ
		imageView.setImageResource(R.drawable.background);
		//imageView.setImageResource(R.drawable.diary_background);

		//�Ҳ²���˵��������״ε�½����ô�ͽ����û�����
		//����жϺ���д�ں�����
		if(isFirstLogin())
		{
			//intent����ͼ���ڲ�ͬ��activity֮����ת�����������ݴ���
			Intent intent = new Intent();
			//�����û����ý��棬������intent����û�����
			intent.setClass(getApplicationContext(), Diary_Login.class);
			//�����ڸ���
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//�����Թص���Ҫ���Ľ����м��activity
			//�����ڸ���ԭ�е�ע�����Ǵ�����������
			startActivity(intent);
		}
		//�����״ε�½�����
		else
		{
			//������һ���µ�intent������activity��ת�ʹ�������
			Intent intent = new Intent();
			//��ô�ͽ����ռǽ��沢�ң���ȡ��������ݣ�
			intent.setClass(getApplicationContext(), Diary_Register.class);
			//���ǲ�֪����һ�����һ��ʲô��˼���Լ������ע����ʲô��˼
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//�����Թص���Ҫ���Ľ����м��activity
			startActivity(intent);
		}
		
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}

	//�����ж��û��ǲ��ǵ�һ�ε�½�ģ��ǵĻ��ͽ����û�ע��
	public boolean isFirstLogin()
	{
		//��仰�еĲ������Խ���Ϊ��������
		SharedPreferences userInfo = getSharedPreferences("user_info",0);

		//���֮ǰ���õ��Ѿ�ע�����flag��false�Ļ�����ô�ͷ���һ������is_First_Login�Ĳ���������������Ϊ�棿
		boolean is_First_Login = userInfo.getBoolean("flag", false);
		return is_First_Login;
	}
}
