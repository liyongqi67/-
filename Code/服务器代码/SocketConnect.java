package drugs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;



//���ڵȴ�socket�ͻ��˵�����
public class SocketConnect
{
	private static ServerSocket serversocket;
	private static Socket socket;
	public static void serverStart()
	{
		try {
			serversocket=new ServerSocket(8889);
			System.out.println("service start...");
			while(true)
			{
				socket=serversocket.accept();
				System.out.println("yilainjie");
//				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//				bufferedWriter.write("6927043102543-С��ϲʳ�ǽ�-15.0-969696-0-��ʹ��к,����,����,С��йк��ҩ,��������,����ҩƷ,ʳ������-�г�ҩ-0;");
//				bufferedWriter.flush();
//				bufferedWriter.close();
				ServerThread serverthread=new ServerThread(socket);
				Thread thread=new Thread(serverthread);
				thread.start();
				System.out.println("���������߳�");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	public static void main(String args[])
	{
		SocketConnect.serverStart();
	}
}
