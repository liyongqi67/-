package drugs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.SynchronousQueue;

//�������������豸�˵ĸ�������������һ���̵߳���ʽ����
public class ServerThread implements Runnable
{
	private static Socket socket;
	 private static BufferedReader in = null;
	private String instruction = null;
//	private static DataOutputStream out = null;
	private static BufferedWriter bufferedWriter=null;
	public ServerThread (Socket socket) 
	{
		this.socket=socket;
	}
		  
	public void run()
	{
//			************************************
		//���Ľ���
		System.out.println("ִ��run");
		try 
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"gbk"));
//			out = new DataOutputStream(socket.getOutputStream());
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gbk"));
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("������ͻ��˵���Ϣͨ��ʱ����һ������,�����ǿͻ����ѶϿ�����");
			e.printStackTrace();

			try 
			{
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			return;
		}
//			****************************************	
		//��ȡ����
		System.out.println("���Զ�ȡ����");
		try 
		{
			
			instruction = in.readLine();
			//ȷ�����������ݷǿ�
			System.out.println("�������ݣ�"+instruction);
			while(instruction==null)
			{
				instruction=in.readLine();
				System.out.println("��ȡ��������Ϊ��"+instruction);
				System.out.println(instruction);
			}
		} catch (IOException e) 
		{
			System.out.println("���ܴӿͻ��˷��͵�ʶ����ʱ����һ������,�����ǿͻ����ѶϿ�����");
			
			try {
				in.close();
				socket.close();
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			return;
		
		}
		//ִ�д�����Ϣ�ķ���
		System.out.println("���������Ϣ����");
		handle(instruction);
		
//		****************************
	}
	//����Ϣ���з�����ִ����Ӧ�Ĳ���
	public static void handle(String s)
	{
		 char c=s.charAt(0);//ȡ��һ����ĸ���ж����豸�˻����ֻ���
		 s=s.substring(1);//ȡ����һ����ĸ����ַ�����
		
		 switch(c)
		 {
			 case 'P'://�ֻ��˴�������Ϣ
				String user=s.substring(0, s.indexOf(';'));//ȡ���û���
				s=s.substring((user.length()+1));
				
				
					 
				if (user.equals("sdu")) //��ʾ������ע����¼����Ϣ
				{
					String first=s.substring(0, s.indexOf(';'));
					s=s.substring((first.length()+1), s.lastIndexOf(';'));//�������Ϣ
					 
					switch(first)
					{
						case "r"://�ֻ���ע��
							String name=s.substring(0,s.indexOf('-'));//�û�ע�������
							s=s.substring((name.length()+1));
							String password=s.substring(0,s.indexOf('-'));//�û�ע������� 
							
							String iduser=s.substring((password.length()+1));//���û���Ӧ���豸��
							if(Mysql.haveIduser(iduser))//���豸���Ѿ�����,���������豸�ź�����û�������
								Mysql.setName(iduser, name, password);
							else//���豸�Ų�δ����	
								Mysql.register(iduser, name, password);
							//���سɹ�
							try 
							{
								bufferedWriter.write("s");
								bufferedWriter.flush();
							} catch (IOException e) {
								System.out.println("����������ע��ɹ�ʱ����");
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//�ر�out,in,socket
							try 
							{
								in.close();
								bufferedWriter.close();
								socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case "1"://�ֻ��˵�¼
							name=s.substring(0,s.indexOf('-'));//�û���¼������
							password=s.substring((name.length()+1));//�û���¼������
							if(Mysql.login(name, password))//��¼�ɹ�
							{
								//���سɹ�
								System.out.println("��½�ɹ�");
								try 
								{
									bufferedWriter.write("s");
									bufferedWriter.flush();
									System.out.println("�ѳɹ�����s");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//�ر�out,in,socket
								try 
								{
									in.close();
									bufferedWriter.close();
									socket.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else
							{
								//����ʧ��
								System.out.println("��½ʧ��");
								try 
								{
									bufferedWriter.write("f ");
									bufferedWriter.flush();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//�ر�out,in,socket
								try 
								{
									in.close();
									bufferedWriter.close();
									socket.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break; 
						default:
							break;
							
						
					}
				}
				else//��ʾ�������û�������������,
				{
					String first=s.substring(0, s.indexOf(';'));
//					s=s.substring((first.length()+1), s.indexOf(';'));//�������Ϣ
					switch(first)
					{
					
					case "flash"://��ʾ�ֻ��˻�ȡҩƷ�б�
						String iduser=Mysql.getIduser(user);
						try 
						{
//							out.writeUTF(Mysql.getTable(userTable));
							String jjj=Mysql.getPTable(iduser);
							if(jjj==null)
								jjj="none";
							bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gbk"));
							bufferedWriter.write(jjj+"\n");
							bufferedWriter.flush();	
							
						
							
							System.out.println("���͹�ȥ������Ϊ :"+jjj);
							System.out.println(socket.isClosed());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//�ر�out,in,socket
						try 
						{
							in.close();
							bufferedWriter.close();
							
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					break;
					case "n":
						iduser=Mysql.getIduser(user);
						s=s.substring((first.length()+1));//�������Ϣ
						boolean isadd=true;
						String code=s.substring(0,s.indexOf('-'));//ҩƷ������
						s=s.substring((code.length()+1));
						String birthday=s.substring(0,s.indexOf('-'));//��������
						s=s.substring(birthday.length()+1);
						String guaranteePeriod =s.substring(0,s.indexOf('-'));//������
						String surplus=s.substring((guaranteePeriod.length()+1),s.indexOf(';'));//ʣ����
						
						try 
						{
							UseApi.send(code);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							isadd=false;
							try 
							{
								bufferedWriter.write("f");
								bufferedWriter.flush();
							} catch (IOException el) {
								// TODO Auto-generated catch block
								el.printStackTrace();
							}
							System.out.println("û�ж�Ӧ�������룬�ѳɹ����豸�˷���ʧ��");
							
							e.printStackTrace();
						}
						if(isadd)
						{
							
							
							String name=UseApi.name;
							String price=UseApi.price;
							String tag=UseApi.tag;
							String type=UseApi.type;
							String properties=UseApi.properties;
							String functions=UseApi.functions;
							String usage=UseApi.usage;
							String attentions =UseApi.attentions;
							String specifications =UseApi.specifications;
	//						String userTable=Mysql.getUser(iduser);
							//��ӡ��ҩƷ��Ϣ
		//					System.out.println(userTable);
		//					System.out.println(code);
		//					System.out.println(name);
		//					System.out.println(birthday);
		//					System.out.println(guaranteePeriod);
		//					System.out.println(tag);
		//					System.out.println(type);
		//					System.out.println(surplus);
							try 
							{
								if(!Mysql.haveIduser(iduser))
									Mysql.register(iduser, "li", "li");
								Mysql.addDrug(iduser, code, name, price, "111111","12", tag, type,"6", properties, functions, usage, attentions, specifications);
								Data.explodeTag(tag);
								for(int i=0;i<Data.s.length;i++)
								{
									Mysql.setTag(iduser, Data.s[i]);
								}
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								System.out.println("����addDrug����ʱ����");
								try 
								{
									bufferedWriter.write("f");
									bufferedWriter.flush();
								} catch (IOException el) {
									// TODO Auto-generated catch block
									el.printStackTrace();
								}
								e.printStackTrace();
							}
							//�ɹ�����s
							try 
							{
	                        //	out.writeUTF("s");
								bufferedWriter.write("s");
								bufferedWriter.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						//�ر�out,in,socket
					try {
						in.close();
					//	out.close();
						bufferedWriter.close();
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
					//�����ʾ�ֻ����޸���Ϣ
					case "m":
						iduser=Mysql.getIduser(user);
						s=s.substring((first.length()+1),s.lastIndexOf(';'));//�������Ϣ
						String what=s.substring(0,s.indexOf(';'));//��ʾҪ�޸ĵ�ֵ������
						s=s.substring((what.length()+1));
						String isWhat=s.substring(0,s.indexOf(';'));//��ʾ�޸ĺ��ֵ
						String number=s.substring(isWhat.length()+1);
						System.out.println("Ҫ�޸ĵ�ҩƷ���Ϊ��"+number);
						if(what.equals("eaten"))
						{
							
							Mysql.setEaten(iduser, number, isWhat);
							System.out.println("�ֻ���eatenֵ�޸ĳɹ�");
						}
						else if(what.equals("birthday"))
						{
							
							Mysql.setBirthday(iduser, number, isWhat);
							System.out.println("�ֻ������������޸ĳɹ�");
						}
						else if(what.equals("surplus"))
						{
							
							Mysql.setBirthday(iduser, number, isWhat);
							System.out.println("�ֻ������������޸ĳɹ�");
						}
						else if(what.equals("drugHour"))
						{
							Mysql.setDrugHour(iduser, number, isWhat);
						}
						else if(what.equals("drugMinute"))
						{
							Mysql.setDrugMinute(iduser, number, isWhat);
						}
						else if(what.equals("attentionTime"))
						{
							Mysql.setAttentionTime(iduser, number, isWhat);
						}
						else if(what.equals("dosage"))
						{
							Mysql.setAttentionTime(iduser, number, isWhat);
						}
						
						
						break;	
					case "done":
						iduser=Mysql.getIduser(user);
						System.out.println(s);
						number=s.substring((first.length()+1),s.lastIndexOf(';'));
						System.out.println(number);
						String isEaten=Mysql.getIsEaten(iduser, number);
						try 
						{
//							out.writeUTF(Mysql.getTable(userTable));
							bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gbk"));
							String jjj;
							if(isEaten.equals("yes"))
							{
								jjj="0";
							}
							else
							{
								jjj="1";
							}
							bufferedWriter.write(jjj);
							bufferedWriter.flush();	
							
						
							
							System.out.println("���͹�ȥ������Ϊ :"+jjj);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//�ر�out,in,socket
						try 
						{
							in.close();
							bufferedWriter.close();
							
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "drugEate":
						iduser=Mysql.getIduser(user);
						number=s.substring((first.length()+1),s.lastIndexOf(';'));
						try 
						{
//							out.writeUTF(Mysql.getTable(userTable));
							bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gbk"));
							String jjj=Mysql.getDrugEate(iduser, number);
							bufferedWriter.write(jjj);
							bufferedWriter.flush();	
							
						
							
							System.out.println("���͹�ȥ������Ϊ :"+jjj);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//�ر�out,in,socket
						try 
						{
							in.close();
							bufferedWriter.close();
							
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						break;
					case "a":
						try 
						{
//							out.writeUTF(Mysql.getTable(userTable));
							bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gbk"));
							String jjj=PushMassage.findMassages(user);
							bufferedWriter.write(jjj);
							bufferedWriter.flush();	
							
						
							
							System.out.println("���͹�ȥ������Ϊ :"+jjj);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//�ر�out,in,socket
						try 
						{
							in.close();
							bufferedWriter.close();
							
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "delete":
						iduser=Mysql.getIduser(user);
						System.out.println(iduser);
						s=s.substring(first.length()+1);
						number=s.substring(0,s.lastIndexOf(';'));//��ȡ������ɾ��ҩƷ������
					
						Mysql.deleteDrug(iduser, number);
						try 
						{
							bufferedWriter.write("s"+"\r\n");
							bufferedWriter.flush();
							System.out.println("ɾ��ҩƷ�ɹ�������s");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//�ر�out,in,socket
						try {
							in.close();
	//						out.close();
							bufferedWriter.close();
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
							
							
						}
				}
			break;
			case 'D':
				String iduser=s.substring(0, s.indexOf(';'));//ȡ���豸��
				System.out.println("�豸��Ϊ��"+iduser);
				s=s.substring((iduser.length()+1));//�豸��֮�����Ϣ
				String first=s.substring(0, s.indexOf(';'));//ȡ��������
				System.out.println("������Ϊ"+first);
				s=s.substring(first.length()+1);
				switch(first)
				{
					//�����ʾ�豸����������ҩƷ
					case "n":
						boolean isadd=true;
						String code=s.substring(0,s.indexOf('-'));//ҩƷ������								
						s=s.substring((code.length()+1));
						String birthday=s.substring(0,s.indexOf('-'));//��������
						s=s.substring(birthday.length()+1);
						String guaranteePeriod =s.substring(0,s.indexOf('-'));//������
						String surplus=s.substring((guaranteePeriod.length()+1),s.indexOf(';'));//ʣ����
						
						try 
						{
							UseApi.send(code);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							isadd=false;
							try 
							{
								bufferedWriter.write("f");
								bufferedWriter.flush();
							} catch (IOException el) {
								// TODO Auto-generated catch block
								el.printStackTrace();
							}
							System.out.println("û�ж�Ӧ�������룬�ѳɹ����豸�˷���ʧ��");
							
							e.printStackTrace();
						}
						if(isadd)
						{
							
							
							String name=UseApi.name;
							String price=UseApi.price;
							String tag=UseApi.tag;
							String type=UseApi.type;
							String properties=UseApi.properties;
							String functions=UseApi.functions;
							String usage=UseApi.usage;
							String attentions =UseApi.attentions;
							String specifications =UseApi.specifications;
	//						String userTable=Mysql.getUser(iduser);
							//��ӡ��ҩƷ��Ϣ
		//					System.out.println(userTable);
		//					System.out.println(code);
		//					System.out.println(name);
		//					System.out.println(birthday);
		//					System.out.println(guaranteePeriod);
		//					System.out.println(tag);
		//					System.out.println(type);
		//					System.out.println(surplus);
							try 
							{
								if(!Mysql.haveIduser(iduser))
									Mysql.register(iduser, "li", "li");
								Mysql.addDrug(iduser, code, name, price, birthday, guaranteePeriod, tag, type, surplus, properties, functions, usage, attentions, specifications);
								Data.explodeTag(tag);
								for(int i=0;i<Data.s.length;i++)
								{
									Mysql.setTag(iduser, Data.s[i]);
								}
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								System.out.println("����addDrug����ʱ����");
								try 
								{
									bufferedWriter.write("f");
									bufferedWriter.flush();
								} catch (IOException el) {
									// TODO Auto-generated catch block
									el.printStackTrace();
								}
								e.printStackTrace();
							}
							//�ɹ�����s
							try 
							{
	                        //	out.writeUTF("s");
								bufferedWriter.write("s");
								bufferedWriter.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						//�ر�out,in,socket
					try {
						in.close();
					//	out.close();
						bufferedWriter.close();
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
					
					//�����ʾ�豸��������ҩƷ�б�
					case "flash":
//						userTable=Mysql.getUser(iduser);
//						System.out.println("�豸�Ŷ�Ӧ���û�Ϊ��"+userTable);
						try 
						{
//							out.writeUTF(Mysql.getTable(userTable));
							if(!Mysql.haveIduser(iduser))
								Mysql.register(iduser, "li", "li");
							String jjj=Mysql.getTable(iduser);
							if(jjj!=null)
							{
//								jjj="6927043102543-��ŵҩҵ ���Ӱ���Ƭ-������ʹ-����,������ʹ,����,��ʹ-44.0-151111-24-55;";

								bufferedWriter.write(jjj);
								System.out.println("���͹�ȥ������Ϊ :"+jjj);
								bufferedWriter.flush();
								
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//�ر�out,in,socket
						try {
							in.close();
							bufferedWriter.close();
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					break;
					//�����ʾ�豸���޸���Ϣ
					case "m":
//						userTable=Mysql.getUser(iduser);//����豸�Ŷ�Ӧ���û���
						String number=s.substring(0,s.indexOf('-'));//ҩƷ����
						s=s.substring((number.length()+1));
						if(s.charAt(0)=='-')							
						{
							s=s.substring(1);
							if(s.charAt(0)=='-')
							{
								s=s.substring(1);
								if(s.charAt(0)=='-')
								{
									s=s.substring(1);
									if(s.charAt(0)!=';')
									{
										surplus=s.substring(0,s.indexOf(';'));//ʣ����
										Mysql.setSurplus(iduser, number, surplus);
										String tag=Mysql.getTag(iduser, number);
										Data.explodeTag(tag);
										for(int i=0;i<Data.s.length;i++)
										{
											Mysql.setTag(iduser, Data.s[i]);
										}
										
									}
								}
								else
								{
									guaranteePeriod =s.substring(0,s.indexOf('-'));//������
									Mysql.setGuaranteePeriod(iduser, number, guaranteePeriod);
									s=s.substring(guaranteePeriod.length()+1);
									if(s.charAt(0)!=';')
									{
										surplus=s.substring(0,s.indexOf(';'));//ʣ����
										Mysql.setSurplus(iduser, number, surplus);
										String tag=Mysql.getTag(iduser, number);
										Data.explodeTag(tag);
										for(int i=0;i<Data.s.length;i++)
										{
											Mysql.setTag(iduser, Data.s[i]);
										}
									}
								}
								
							}
							else
							{
								birthday=s.substring(0,s.indexOf('-'));//��������
								Mysql.setBirthday(iduser, number, birthday);
								
								s=s.substring(birthday.length()+1);
								if(s.charAt(0)=='-')
								{
									s=s.substring(1);
									if(s.charAt(0)!=';')
									{
										surplus=s.substring(0,s.indexOf(';'));//ʣ����
										Mysql.setSurplus(iduser, number, surplus);
										String tag=Mysql.getTag(iduser, number);
										Data.explodeTag(tag);
										for(int i=0;i<Data.s.length;i++)
										{
											Mysql.setTag(iduser, Data.s[i]);
										}
									}
								}
								else
								{
	
									guaranteePeriod =s.substring(0,s.indexOf('-'));//������
									Mysql.setGuaranteePeriod(iduser, number, guaranteePeriod);
									s=s.substring(guaranteePeriod.length()+1);
									if(s.charAt(0)!=';')
									{
										surplus=s.substring(0,s.indexOf(';'));//ʣ����
										Mysql.setSurplus(iduser, number, surplus);
										String tag=Mysql.getTag(iduser, number);
										Data.explodeTag(tag);
										for(int i=0;i<Data.s.length;i++)
										{
											Mysql.setTag(iduser, Data.s[i]);
										}
									}
								}
							}
						}
						else
						{
							String drugName=s.substring(0,s.indexOf('-'));//Ҫ�޸ĳɵ�ҩƷ��
							Mysql.setDrugname(iduser, number, drugName);
							s=s.substring(drugName.length()+1);
							if(s.charAt(0)=='-')
							{
								s=s.substring(1);
								if(s.charAt(0)=='-')
								{
									s=s.substring(1);
									if(s.charAt(0)!=';')
									{
										surplus=s.substring(0,s.indexOf(';'));//ʣ����
										Mysql.setSurplus(iduser, number, surplus);
										String tag=Mysql.getTag(iduser, number);
										Data.explodeTag(tag);
										for(int i=0;i<Data.s.length;i++)
										{
											Mysql.setTag(iduser, Data.s[i]);
										}
									}
								}
								else
								{
									guaranteePeriod =s.substring(0,s.indexOf('-'));//������
									Mysql.setGuaranteePeriod(iduser, number, guaranteePeriod);
									s=s.substring(guaranteePeriod.length()+1);
									if(s.charAt(0)!=';')
									{
										surplus=s.substring(0,s.indexOf(';'));//ʣ����
										Mysql.setSurplus(iduser, number, surplus);
										String tag=Mysql.getTag(iduser, number);
										Data.explodeTag(tag);
										for(int i=0;i<Data.s.length;i++)
										{
											Mysql.setTag(iduser, Data.s[i]);
										}
									}
								}
								
							}
							else
							{
								birthday=s.substring(0,s.indexOf('-'));//��������
								Mysql.setBirthday(iduser, number, birthday);
								s=s.substring(birthday.length()+1);
								if(s.charAt(0)=='-')
								{
									s=s.substring(1);
									if(s.charAt(0)!=';')
									{
										surplus=s.substring(0,s.indexOf(';'));//ʣ����
										Mysql.setSurplus(iduser, number, surplus);
										String tag=Mysql.getTag(iduser, number);
										Data.explodeTag(tag);
										for(int i=0;i<Data.s.length;i++)
										{
											Mysql.setTag(iduser, Data.s[i]);
										}
									}
								}
								else
								{
	
									guaranteePeriod =s.substring(0,s.indexOf('-'));//������
									Mysql.setGuaranteePeriod(iduser, number, guaranteePeriod);
									s=s.substring(guaranteePeriod.length()+1);
									if(s.charAt(0)!=';')
									{
										surplus=s.substring(0,s.indexOf(';'));//ʣ����
										Mysql.setSurplus(iduser, number, surplus);
										String tag=Mysql.getTag(iduser, number);
										Data.explodeTag(tag);
										for(int i=0;i<Data.s.length;i++)
										{
											Mysql.setTag(iduser, Data.s[i]);
										}
									}
								}
							}
						}
//						birthday=s.substring(0,s.indexOf('-'));//��������
//						if(!birthday.equals(""))
//						{
//							Mysql.setBirthday(iduser, code, birthday);
//						}
//						s=s.substring(birthday.length()+1);
//						guaranteePeriod =s.substring(0,s.indexOf('-'));//������
//						if(!guaranteePeriod.equals(""))
//						{
//							Mysql.setGuaranteePeriod(iduser, code, guaranteePeriod);
//						}
//						surplus=s.substring((guaranteePeriod.length()+1),s.indexOf(';'));//ʣ����
//						if(!surplus.equals(""))
//						{
//							Mysql.setSurplus(iduser, code, surplus);
//						}
						//���سɹ�
						try 
						{
//							out.writeUTF(Mysql.getTable(userTable));
							bufferedWriter.write("s");
							System.out.println("�豸���޸�ҩƷ��Ϣ�ɹ����ɹ�����s");
							bufferedWriter.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//�ر�out,in,socket
						try {
							in.close();
//							out.close();
							bufferedWriter.close();
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					break;
					//�����ʾ�豸��ɾ��ҩƷ
					case "delete":
						System.out.println("����ɾ��ҩƷ");
						number=s.substring(0,s.lastIndexOf(';'));//��ȡ������ɾ��ҩƷ������
						System.out.println("ɾ��ҩƷ��������Ϊ��"+number);
						Mysql.deleteDrug(iduser, number);
					try 
					{
						bufferedWriter.write("s"+"\r\n");
						bufferedWriter.flush();
						System.out.println("ɾ��ҩƷ�ɹ�������s");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//�ر�out,in,socket
					try {
						in.close();
//						out.close();
						bufferedWriter.close();
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
					case "schedule":
						String jjj=Mysql.getSchedule(iduser);
						try {
							bufferedWriter.write(jjj+"\r\n");
							bufferedWriter.flush();
  							System.out.println("���͹�ȥ������Ϊ��"+jjj);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//�ر�out,in,socket
						try {
							in.close();
//							out.close();
							bufferedWriter.close();
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					break;
					case "binding":
						String rfid=s.substring(0,s.indexOf(';'));
						
						number=Mysql.getNumber(iduser);
						Mysql.bindingRfid(iduser, number, rfid);
					break;
					case "put":
						rfid=s.substring(0,s.indexOf(';'));Mysql.setIseaten(iduser, rfid);
					break;
						
					
				}
				
				
				
				
				break;
		 }
	}
}
