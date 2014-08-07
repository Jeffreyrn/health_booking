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



public class GetInfo extends Thread implements Runnable 
{
	String temp="", findUserInfo="";
	public void run()   
    {   
        try  
        {   
            //����ServerSocket   
        	ServerSocket serverSocket = new ServerSocket(4568); 
            while (true)   
            {   
                //���ܿͻ�������   
            	  
                Socket client = serverSocket.accept();   
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
                PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(client.getOutputStream())),true); 
                System.out.println("Get request accepted...");   
                try  
                {   
                    //���տͻ�����Ϣ   
                      
                    String str = in.readLine();
                    System.out.println("PORT 4568 info:" + str);   
                    
                    
                    
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
                        String sql = "select id, name, email, blood, pulse,hospital, disease from info";

                        // �����
                        ResultSet rs = statement.executeQuery(sql);

                        System.out.println("-----------------");
                        System.out.println("Selected User Info:");
                        

                        
                        while(rs.next()) {
                        	temp=rs.getString(1)+"|"
                                    + rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4)+"|"+rs.getString(5)
                                    +"|"+rs.getString(6)+"|"+rs.getString(7);
                       	 
                         // ѡ��sname��������
                         if (  str.equals(rs.getString(1))){
                       	  findUserInfo=temp;
                       	  System.out.println(temp);
                       	  
                         }
                        }
                        
                        rs.close();
                        conn.close();

                       } catch(SQLException e) {

                        e.printStackTrace();

                       } catch(Exception e) {

                        e.printStackTrace();

                       }
                    
                    
                    
                    
                    //��client������Ϣ   
                          
                    out.println(findUserInfo); 
                    
                    //�ر���   
                    //out.close();   
                    //in.close();   
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
                    System.out.println("close + next");   
                }
                sleep(10000);
            }   
        }   
        catch (Exception e)   
        {   
            System.out.println(e.getMessage());   
        }   
    } 

}
