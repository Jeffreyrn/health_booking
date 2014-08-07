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
        	//����ServerSocket   
            ServerSocket serverSocket = new ServerSocket(4567);
               
            while (true)   
            {   
                //���ܿͻ�������   
                Socket client = serverSocket.accept();   
                String loginsuccess="0";
                System.out.println("Login info accept...");   
                try  
                {   
                    //���տͻ�����Ϣ   
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));   
                    String str = in.readLine();
                    System.out.println("Server read:" + str);
                    
                    String[] array=new String[2];
                    array=str.split("\\|");
                    System.out.println("useID="+array[0]+", pwd="+array[1]);
                    
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
                     String sql = "select id, password from user";

                     // �����
                     ResultSet rs = statement.executeQuery(sql);

                     System.out.println("-----------------");
                     

                     String name = array[0];
                     USER_ID= array[0];
                     String pwd= array[1];
                     while(rs.next()) {
                    	 
                      // ѡ��sname��������
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
                    
                    
                    
                    //�������������Ϣ   
                    PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(client.getOutputStream())),true);         
                       
                    out.println(loginsuccess);    
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
    
    //main����������������   
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
