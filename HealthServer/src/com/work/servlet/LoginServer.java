package com.work.servlet;

import java.io.BufferedReader;   
import java.io.BufferedWriter;   
import java.io.InputStreamReader;   
import java.io.OutputStreamWriter;   
import java.io.PrintWriter;   
import java.net.ServerSocket;   
import java.net.Socket;   
import java.sql.*;


  
public class LoginServer implements Runnable   
{   
	private String        USER_ID = "";
    public void run()   
    {   
        try  
        {   
        	//创建ServerSocket   
            ServerSocket serverSocket = new ServerSocket(4567);
               
            while (true)   
            {   
                //接受客户端请求   
                Socket client = serverSocket.accept();   
                String loginsuccess="0";
                System.out.println("Login info accept...");   
                try  
                {   
                    //接收客户端消息   
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));   
                    String str = in.readLine();
                    System.out.println("Server read:" + str);
                    
                    String[] array=new String[2];
                    array=str.split("\\|");
                    System.out.println("useID="+array[0]+", pwd="+array[1]);
                    
                 // 驱动程序名
                    String driver = "com.mysql.jdbc.Driver";

                    // URL指向要访问的数据库名scutcs
                    String url = "jdbc:mysql://127.0.0.1:3306/health";

                    // MySQL配置时的用户名
                    String user = "root"; 

                    // MySQL配置时的密码
                    String passwordDB = "";

                    try { 
                     // 加载驱动程序
                     Class.forName(driver).newInstance();
                    }
                    catch(Exception e){
                        System.out.println("Error loading Mysql Driver!"); // 连接失败
                        return ;  // 返回
                    }
                    System.out.println("Success loading Mysql Driver!\n"); // jdbc成功
                    
                    try {
                     // 连续数据库
                     Connection conn = DriverManager.getConnection(url, user, passwordDB);

                     if(!conn.isClosed()) 
                      System.out.println("Succeeded connecting to the Database!");

                     // statement用来执行SQL语句
                     Statement statement = conn.createStatement();

                     // 要执行的SQL语句
                     String sql = "select id, password from user";

                     // 结果集
                     ResultSet rs = statement.executeQuery(sql);

                     System.out.println("-----------------");
                     

                     String name = array[0];
                     USER_ID= array[0];
                     String pwd= array[1];
                     while(rs.next()) {
                    	 
                      // 选择sname这列数据
                      if (  name.equals(rs.getString(1))  &&  pwd.equals(rs.getString(2))  ){
                    	  loginsuccess="1";
                    	  System.out.println("userID="+rs.getString(1)+", userPassword="+ rs.getString(2));
                    	  System.out.println("login success...");
                      }
                     }
                     
                     rs.close();
                     conn.close();

                    } catch(SQLException e) {

                     e.printStackTrace();

                    } catch(Exception e) {

                     e.printStackTrace();

                    }
                    
                    
                    
                    //向服务器发送消息   
                    PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(client.getOutputStream())),true);         
                       
                    out.println(loginsuccess);    
                    //关闭流   
                    out.close();   
                    in.close();   
                }   
                catch (Exception e)   
                {   
                    System.out.println(e.getMessage());   
                    e.printStackTrace();   
                }   
                finally  
                {   
                    //关闭   
                    client.close();   
                    System.out.println("close");   
                }   
            }   
        }   
        catch (Exception e)   
        {   
            System.out.println(e.getMessage());   
        }   
    } 
    
    //main函数，开启服务器   
    public static void main(String a[])   
    {   
    	
    	
    	final String        USER_ID = "";
    	
        Thread loginThread = new Thread(new LoginServer());   
        Thread getThread = new Thread(new GetInfo());
        Thread updateThread = new Thread(new Update());
        Thread bookingThread = new Thread(new Booking()); 
        Thread submitThread = new Thread(new SubmitBookingInfo());
        loginThread.start();   
        getThread.start();
        updateThread.start();
        bookingThread.start();
        submitThread.start();
    }   
}  
