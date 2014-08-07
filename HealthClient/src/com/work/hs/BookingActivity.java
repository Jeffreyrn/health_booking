package com.work.hs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class BookingActivity extends Activity{
	
	private final String		DEBUG_TAG	= "BookingActivity";
	private final String        ServerIP    = "175.159.1.121";
	private EditText	dateText=null;
	private EditText	timeText=null;
	private Button		submitButton=null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking);
		
		Intent intent=getIntent();
		final String userID =intent.getStringExtra("userID") ;
		
		dateText = (EditText)findViewById(R.id.bookDate);
		timeText = (EditText)findViewById(R.id.bookTime);
		submitButton = (Button)findViewById(R.id.SubmitButton);
		
		Socket socket = null;
		String message = userID;
		try 
		{	
			//创建Socket
			socket = new Socket(ServerIP,4570); //查看本机IP,每次开机都不同
			//向服务器发送消息
			PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);      
			out.println(message); 
			
			//接收来自服务器的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			String msg = br.readLine(); 
			String[] array=new String[2];
            array=msg.split("\\|");
            dateText.setText(array[0]);
            timeText.setText(array[1]);
			
		
			
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
		
		
		
		submitButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Socket socket = null;
				String message = userID+"|"+dateText.getText().toString()+
						"|"+timeText.getText().toString(); 
				try 
				{	
					//创建Socket
					socket = new Socket(ServerIP,4571); //查看本机IP,每次开机都不同
					//向服务器发送消息
					PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);      
					out.println(message); 
					
					//接收来自服务器的消息
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
					String msg = br.readLine(); 
					
					showDialog("Submit success!");
					
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
