package com.work.hs;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity{
	private final String		DEBUG_TAG	= "MainActivity";
	private final String        ServerIP    = "175.159.1.121";
	
	
	private EditText	nameText=null;
	private EditText	emailText=null;
	private EditText	bloodpressText=null;
	private EditText	pulseText=null;
	private EditText	diseaseText=null;
	private EditText	hospitalText=null;
	private Button		updateButton=null;
	private Button		bookingButton=null;
	private Button		getButton=null;
	
	String[] cache=new String[7];
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Intent intent=getIntent();
		final String userID =intent.getStringExtra("userID") ;
		for(int i=0;i<8;i++){
			cache[i]="null";
		}
		
		nameText = (EditText)findViewById(R.id.nameText);
		emailText = (EditText)findViewById(R.id.emailText);
		bloodpressText = (EditText)findViewById(R.id.bloodText);
		pulseText = (EditText)findViewById(R.id.pulseText);
		diseaseText = (EditText)findViewById(R.id.diseaseText);
		hospitalText = (EditText)findViewById(R.id.hospitalText);
		updateButton = (Button)findViewById(R.id.updateButton);
		bookingButton = (Button)findViewById(R.id.bookingButton);
		getButton = (Button)findViewById(R.id.getButton);
		
		
		
		
		
		getButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Socket socket = null;
				String message = userID; 
				
				try 
				{	
					//创建Socket
					
					//while(true){
						socket = new Socket(ServerIP,4568); //查看本机IP,每次开机都不同
						//向服务器发送消息
						PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
						BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					out.println(message); 
					
					
					//接收来自服务器的消息
					String msg = br.readLine(); 
					
                    cache=msg.split("\\|");
					
					//socket.close(); 
					
					
					//关闭流
					//out.close();
					//br.close();
					//关闭Socket
					
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Log.e(DEBUG_TAG, e.toString());
				}
				
				nameText.setText(cache[1]);
				emailText.setText(cache[2]);
				bloodpressText.setText(cache[3]);
				pulseText.setText(cache[4]);
				hospitalText.setText(cache[5]);
				diseaseText.setText(cache[6]);
				
			}
		});
		
		updateButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Socket socket = null;
				String message = userID+"|"+nameText.getText().toString()+
						"|"+emailText.getText().toString()+
						"|"+bloodpressText.getText().toString()+
						"|"+pulseText.getText().toString()+
						"|"+hospitalText.getText().toString()+
						"|"+diseaseText.getText().toString(); 
				try 
				{	
					//创建Socket
					socket = new Socket(ServerIP,4569); //查看本机IP,每次开机都不同
					//向服务器发送消息
					PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);      
					out.println(message); 
					
					//接收来自服务器的消息
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
					String msg = br.readLine(); 
					
					showDialog("Update success!");
					
					//关闭流
					out.close();
					br.close();
					//关闭Socket
					socket.close(); 
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Log.e(DEBUG_TAG, e.toString());
				}
			}
		});
		
		
		bookingButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this,BookingActivity.class);
						intent.putExtra("userID", userID);
						startActivity(intent);
			}
		});
	
	
	
	
	
	}
	
	private void showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}

}