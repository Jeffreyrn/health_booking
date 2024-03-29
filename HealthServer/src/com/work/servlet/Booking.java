package com.work.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Booking implements Runnable{
	
	String findUserInfo="";
	public void run()   
    {   
        try  
        {   
            //创建ServerSocket   
            ServerSocket serverSocket = new ServerSocket(4570);   
            while (true)   
            {   
                //接受客户端请求   
                Socket client = serverSocket.accept();   
                
                try  
                {   
                    //接收客户端消息   
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));   
                    String str = in.readLine();
                    System.out.println("booking request recieved...");   
                    System.out.println("PORT 4570 info:" + str);   
                    
                    
                    
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
                        String sql = "select date, time from booking where Id='"+str+"'";

                        // 结果集
                        ResultSet rs = statement.executeQuery(sql);

                        System.out.println("-----------------");
                        System.out.println("find user '"+str+"' booking info:");
                        while(rs.next()) {//important!
                        findUserInfo=rs.getString(1)+"|"+ rs.getString(2);
                        }
                        System.out.println(findUserInfo);  
                        
                        
                        rs.close();
                        conn.close();

                       } catch(SQLException e) {

                        e.printStackTrace();

                       } catch(Exception e) {

                        e.printStackTrace();

                       }
                    
                    
                    
                    
                    //向client发送消息   
                    PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(client.getOutputStream())),true);         
                    out.println(findUserInfo);    
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

}
