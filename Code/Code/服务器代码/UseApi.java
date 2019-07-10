package drugs;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
//
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import java.text.SimpleDateFormat;
import java.util.Date;
//48ee8200edecd4cc7e76
//������������ҩƷapi������������Ϣ
public class UseApi 
{
	static String name=null;//����
	static String price=null;//�۸�
	static String tag=null;//��Ӧ֢
	static String type=null;//����
	static String properties=null;//��״
	static String functions=null;//��������
	static String usage = null;//�÷�����
	static String attentions = null;//ע������
	static String specifications = null;//���
	

	public static String send(String code) throws Exception 
	{
		String result =null;
		//����ͨ��API��ȡҩƷ��Ϣ
		try 
		{
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://api.avatardata.cn/Drug/Code"); 
			post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//��ͷ�ļ�������ת��
			NameValuePair[] data ={new NameValuePair("Key", "b327038fa63c4dd6a98173d7bd9d7452"),new NameValuePair("code",code),new NameValuePair("format","true")};
			post.setRequestBody(data);

			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			
			
			result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
			System.out.println(result);

			post.releaseConnection();
			
			

		} catch (HttpException e) {
   			e.printStackTrace();		} catch (IOException e) {
			e.printStackTrace();
		}
	//����Է��ص���Ϣ���д������������Ϣ
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(result.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));//���ַ���ת��ΪԴ��������ȡ
		String line;  
		for(int i=0;i<18;i++)
		{
			line = br.readLine();
			//��ȡҩƷ��
			if(i == 10){
				
				String metaproperties = null;
				String metaproperties2 = null;
				String properties = null;
				//��ȡ��״
				if(line.indexOf("����״")>0){
					metaproperties = line.substring(line.indexOf("����״")+5,line.length());
					metaproperties2 = metaproperties.substring(0, metaproperties.indexOf("��"));
					properties = metaproperties2.replaceAll("((\r\n)|\n|\\\\|n|\\s|<p>|</p>|&nbsp)", "");
					properties = properties.replaceAll(",|;", "��");
					properties = properties.replaceAll("-", "~");
					if(properties.indexOf("չ��")>0){
						String temp = properties;
						properties = temp.substring(0,temp.indexOf("չ��")-1);
					}
					UseApi.properties = properties;
				}
				
				//��ȡ��������
				if(line.indexOf("����������")>0){
					metaproperties = line.substring(line.indexOf("����������")+7,line.length());
					metaproperties2 = metaproperties.substring(0, metaproperties.indexOf("��"));
					properties = metaproperties2.replaceAll("((\r\n)|\n|\\\\|n|\\s|<p>|</p>|&nbsp)", "");
					properties = properties.replaceAll(",|;", "��");
					properties = properties.replaceAll("-", "~");
					if(properties.indexOf("չ��")>0){
						String temp = properties;
						properties = temp.substring(0,temp.indexOf("չ��")-1);
					}
					UseApi.functions = properties;
				}
				
				//��ȡ�÷�����
				if(line.indexOf("���÷�����")>0){
					metaproperties = line.substring(line.indexOf("���÷�����")+7,line.length());
					metaproperties2 = metaproperties.substring(0, metaproperties.indexOf("��"));
					properties = metaproperties2.replaceAll("((\r\n)|\n|\\\\|n|\\s|<p>|</p>|&nbsp)", "");
					properties = properties.replaceAll(",|;", "��");
					properties = properties.replaceAll("-", "~");
					if(properties.indexOf("չ��")>0){
						String temp = properties;
						properties = temp.substring(0,temp.indexOf("չ��")-1);
					}
					UseApi.usage = properties;
				}
				
				//��ȡע������
				if(line.indexOf("��ע������")>0){
					metaproperties = line.substring(line.indexOf("��ע������")+7,line.length());
					metaproperties2 = metaproperties.substring(0, metaproperties.indexOf("��"));
					properties = metaproperties2.replaceAll("((\r\n)|\n|\\\\|n|\\s|<p>|</p>|&nbsp)", "");
					properties = properties.replaceAll(",|;", "��");
					properties = properties.replaceAll("-", "~");
					if(properties.indexOf("չ��")>0){
						String temp = properties;
						properties = temp.substring(0,temp.indexOf("չ��")-1);
					}
					UseApi.attentions = properties;
				}
				
				//��ȡ���
				if(line.indexOf("�����")>0){
					metaproperties = line.substring(line.indexOf("�����")+5,line.length());
					metaproperties2 = metaproperties.substring(0, metaproperties.indexOf("��"));
					properties = metaproperties2.replaceAll("((\r\n)|\n|\\\\|n|\\s|<p>|</p>|&nbsp)", "");
					properties = properties.replaceAll(",|;", "��");
					properties = properties.replaceAll("-", "~");
					if(properties.indexOf("չ��")>0){
						String temp = properties;
						properties = temp.substring(0,temp.indexOf("չ��")-1);
					}
					UseApi.specifications = properties;
				}
				
			}
			if(i==11)
			{
				String s=line.substring(0, line.indexOf(':'));
				line=line.substring((s.length()+3));
				name=line.substring(0, line.indexOf('"'));							
			}
			else if(i==12)//��ȡ�۸�
			{
				String s=line.substring(0, line.indexOf(':'));
				line=line.substring((s.length()+2));
				price=line.substring(0,line.lastIndexOf(','));			
			}
			else if(i==13)
			{
				String s=line.substring(0, line.indexOf(':'));
				line=line.substring((s.length()+3));
				tag=line.substring(0, line.indexOf('"'));		
			}
			else if(i==14)
			{
				String s=line.substring(0, line.indexOf(':'));
				line=line.substring((s.length()+3));
				type=line.substring(0, line.indexOf('"'));
				//System.out.println(type);
			}

		}
		
		
	
		return null;
	}
	public static void main(String args[])
	{
		try {
			send("6927043102543");
			System.out.println(UseApi.attentions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}


