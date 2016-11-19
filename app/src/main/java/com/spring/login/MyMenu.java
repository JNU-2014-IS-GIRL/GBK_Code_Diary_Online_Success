package com.spring.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EncodingUtils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
//import android.renderscript.ProgramVertexFixedFunction.Constants;
//import android.provider.SyncStateContract.Constants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
//import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyMenu extends ListActivity implements OnItemLongClickListener{

	private ArrayList <String> listItem;

	private SimpleAdapter adapter;
	private String[] dailog_item;
	private String[] diary_Item = new String[]{"diary_Title","diary_Name"};
	private int[] menu_item;
	private String[] diary_Title;
	private String delete_File_Name = null;
	private String article_Title = null;
	private String week_Weather = null;
	private long exitTime = 0;
	
	private List<Map<String,Object>> listView_Date = new ArrayList<Map<String,Object>>();

	private int delete_position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu);
		SysApplication.getInstance().addActivity(this);
		this.getListView().setOnItemLongClickListener(this); 
		
		menu_item = new int[] {android.R.id.text1,android.R.id.text2};
		
		dailog_item = getResources().getStringArray(R.array.dailog_Item);
			
		listItem = new ArrayList <String>();
		setListView();
		
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//ListView子项单击消息响应
	@SuppressLint("DefaultLocale")
	protected void onListItemClick(ListView l,View v,int position,long id){

		Map map = listView_Date.get(position);
		String edit_File_Name = (String)map.get(diary_Item[1]);
		
		to_Edit_Diary(edit_File_Name);

	}
	
	public void to_Edit_Diary(String str){
		Intent intent = new Intent(MyMenu.this,Diary_Edit.class);
		Bundle data = new Bundle();
		data.putString( "fileName", str);

		intent.putExtras(data);
		startActivity(intent);
	}
	
	public void to_New_Diary(){
		this.finish();
		Intent intent		=new Intent();
		intent.setClass(getApplicationContext(), Diary_New.class);
		startActivity(intent);
	}
		//菜单选项消息响应
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case R.id.add_Daily: 
			
			to_New_Diary();

			return true;
		case R.id.about_Daily:

			Intent intent = new Intent();
			intent.setClass(getApplicationContext(),Diary_About_Us.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}		
	}
	@SuppressLint("DefaultLocale")
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		delete_position = position;

		this.delete_File_Name = (String)listView_Date.get(position).get(diary_Item[1]);//得到文件名
		
		new AlertDialog.Builder(this).setTitle("日记本")
			.setItems(dailog_item, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch(which){
					case 0:
						to_Edit_Diary(delete_File_Name);
						break;
					case 1:
						if(delete_File(delete_File_Name+".txt")){
							
							//以下两行代码实现ListView的某一行的动态删除
							//adapter.remove(delete_File_Name);
							//把map中的数据删除，在刷新显示即可
							listView_Date.remove(delete_position);
							adapter.notifyDataSetChanged();
							Toast.makeText(getApplicationContext(), "删除成功!", Toast.LENGTH_LONG).show();
						}
						break;
					case 2:
						break;
					default:
						break;
					}
				}
			}).show();
		return true;
	}


	
	//获得当前时间
	@SuppressLint("SimpleDateFormat")
	public String getDate(){
	SimpleDateFormat   formatter    =   new    SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");       
	Date    curDate  =   new    Date(System.currentTimeMillis());//获取当前时间    
	String    str    =    formatter.format(curDate); 
	return str;
	}
	
	public void setListView(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File sdCardDir = Environment.getExternalStorageDirectory();
			File writePath = new File(sdCardDir.getPath().toString()+"/DiaryBook");

			File[] files = writePath.listFiles();
			getFileName(files);

			adapter = new SimpleAdapter(
        			this, 
        			listView_Date,
                    android.R.layout.simple_list_item_2, 
                    diary_Item, 
                    menu_item
        			);
			setListAdapter(adapter);
		}
	}
	
	
	public void getFileName(File[] files){
		if(files!=null){
			for(File file:files){
	
				if(file.isDirectory()){
				//如果为文件夹
					getFileName(file.listFiles());
				}else{
					String fileName = file.getName();
					if(fileName.endsWith(".txt")){
						//		HashMap map = new HashMap();
						String str_Date = fileName.substring(0,fileName.lastIndexOf(".")).toString();
						//listItem.add(str);
						String str_Name = read_File_Line(fileName);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put(diary_Item[0], str_Name);
						map.put(diary_Item[1], str_Date);
						listView_Date.add(map);
					}
				}
			}
		}
	}
	
	public boolean delete_File(String str){
		if((getSDCardPath()) != null){
			File file = new File(getSDCardPath(),str);
			if(file.exists()){
				file.delete();
				return true;
			}
		}
		return false;
	}
	
	public String getSDCardPath(){
		
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

			File sdCardDir = Environment.getExternalStorageDirectory();

			String sdPath = sdCardDir.getPath()+"/DiaryBook";
			return sdPath;
		}
		
		return null;
	}
//		文件与文件夹下的文件的删除
//	public void deleteFile(File file) {
//		 if (file.exists()) { // 判断文件是否存在
//		if (file.isFile()) { // 判断是否是文件
//		file.delete(); // delete()方法 你应该知道 是删除的意思;
//		 } else if (file.isDirectory()) { // 否则如果它是一个目录
//		File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
//		 for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
//		this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
//		}
//		 }
//		file.delete();
//		 } else {
//		// Constants.Logdada("文件不存在！"+"\n");
//		 }
//		 }
	
//	 import java.io.FileInputStream;
//	 void readFileOnLine(){
//	 String strFileName = "Filename.txt";
//	 FileInputStream fis = openFileInput(strFileName);
//	 StringBuffer sBuffer = new StringBuffer();
//	 DataInputStream dataIO = new DataInputStream(fis);
//	 String strLine = null;
//	 while((strLine =  dataIO.readLine()) != null) {
//	     sBuffer.append(strLine + “\n");
//	 }
//	 dataIO.close();
//	 fis.close();
//	 }
	public String read_File_Line(String fileName){
		String sdPath = getSDCardPath();
		String str = null;
		File openFile = new File(sdPath,fileName);
		try {
			FileInputStream file = new FileInputStream(openFile);
			
            InputStreamReader inputreader = new InputStreamReader(file);
            BufferedReader buffreader = new BufferedReader(inputreader);

            str = buffreader.readLine();
            return str;

		}catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {

	        	SysApplication.getInstance().exit(); 
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

}

