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
import android.widget.TextView;

public class HealthClientActivity extends Activity
{
	private final String		DEBUG_TAG	= "HealthClientActivity";
	private final String        ServerIP    = "175.159.1.121";
	
	private EditText	idText=null;
	private EditText	pwdText=null;
	private Button		loginButton=null;
	private Button		cancelButton=null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		loginButton = (Button)findViewById(R.id.loginButton);
		cancelButton = (Button)findViewById(R.id.cancelButton);
		idText=(EditText)findViewById(R.id.inputID);
		pwdText=(EditText)findViewById(R.id.inputPwd);
		
		
		
		
		
		//登陆
		loginButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//Socket socket = null;
				//String message = idText.getText().toString() + "|" + pwdText.getText().toString(); 
				
				Intent intent = new Intent(HealthClientActivity.this,MainActivity.class);
				intent.putExtra("userID", "id");
				startActivity(intent);
				/*try 
				{	
					
					
				Intent intent = new Intent(HealthClientActivity.this,MainActivity.class);
				intent.putExtra("userID", "id");
				startActivity(intent);
				
				/*
					//创建Socket
					//socket = new Socket(ServerIP,4567); //查看本机IP,每次开机都不同
					//向服务器发送消息
					//PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);      
					//out.println(message); 
					
					//接收来自服务器的消息
					//BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
					//String msg = br.readLine(); 
					
					if ( true )//msg.equals("1") )
					{
						Intent intent = new Intent(HealthClientActivity.this,MainActivity.class);
						intent.putExtra("userID", "id");
						startActivity(intent);
					}
					else
					{
						showDialog("login error!");
					}
					//关闭流
					//out.close();
					//br.close();
					//关闭Socket
					//socket.close();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Log.e(DEBUG_TAG, e.toString());
				}*/
			}
		});
	}
	private boolean validate(){
		
			return true;
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
