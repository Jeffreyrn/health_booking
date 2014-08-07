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
            //����ServerSocket   
            ServerSocket serverSocket = new ServerSocket(4570);   
            while (true)   
            {   
                //���ܿͻ�������   
                Socket client = serverSocket.accept();   
                
                try  
                {   
                    //���տͻ�����Ϣ   
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));   
                    String str = in.readLine();
                    System.out.println("booking request recieved...");   
                    System.out.println("PORT 4570 info:" + str);   
                    
                    
                    
                    // ����������
                       String driver = "com.mysql.jdbc.Driver";

                       // URLָ��Ҫ���ʵ����ݿ���scutcs
                       String url = "jdbc:mysql://127.0.0.1:3306/health";

                       // MySQL����ʱ���û���
                       String user = "root"; 

                       // MySQL����ʱ������
                       String passwordDB = "";

                       try { 
                        // ������������
                        Class.forName(driver).newInstance();
                       }
                       catch(Exception e){
                           System.out.println("Error loading Mysql Driver!"); // ����ʧ��
                           return ;  // ����
                       }
                       System.out.println("Success loading Mysql Driver!\n"); // jdbc�ɹ�
                       
                       try {
                        // �������ݿ�
                        Connection conn = DriverManager.getConnection(url, user, passwordDB);

                        if(!conn.isClosed()) 
                         System.out.println("Succeeded connecting to the Database!");

                        // statement����ִ��SQL���
                        Statement statement = conn.createStatement();
                        
                        // Ҫִ�е�SQL���
                        String sql = "select date, time from booking where Id='"+str+"'";

                        // �����
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
                    
                    
                    
                    
                    //��client������Ϣ   
                    PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(client.getOutputStream())),true);         
                    out.println(findUserInfo);    
                    //�ر���   
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
                    //�ر�   
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
