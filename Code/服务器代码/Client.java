package drugs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class Client {  
	   
	   public static void main(String args[]) throws Exception {  
	      //Ϊ�˼���������е��쳣��ֱ��������  
	      String host = "127.0.0.1";  //Ҫ���ӵķ����IP��ַ  
	      int port = 8888;   //Ҫ���ӵķ���˶�Ӧ�ļ����˿�  
	      //�����˽�������  
	      Socket client = new Socket(host, port);  
	      //�������Ӻ�Ϳ����������д������  
	      BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	      BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//	      out.writeUTF("D001;n;6927043102543-122222-36-15");
	      bufferedWriter.write("D001;flash");
	      bufferedWriter.flush();
	      System.out.println(in.readLine());	      
	      in.close();
	      bufferedWriter.close();
	      client.close();
	   }  
	     
	}  