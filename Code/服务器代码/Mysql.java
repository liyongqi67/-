package drugs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.SynchronousQueue;

public class Mysql 
{
	static Statement statement;
	static Connection connection;
	static String[] drugTable=new String[100];
	
	
	 public Mysql()
	{

	
	}
	 //�����������ݿ�
	 private static void connect()
	 {
		 try
			{
				try
				{
					Class.forName( "com.mysql.jdbc.Driver");//�������ݿ�����
					System.out.println("���ݿ��������سɹ�");
				}catch(ClassNotFoundException e){}
			
				//�������ݿ�
				 connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/drug?characterEncoding=utf8","root","");
				//����121.250.223.51Ҫ�������ݿ�����ڵ�IP��ַ��3306��Ϊ���ݿ�Ķ˿ں� 
				 statement=connection.createStatement();
				
		
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("sorry,����ʧ��");}
	 }
	 //�����û�ע��
	 public static void register(String iduser,String user ,String password)
	 {
		 connect();
		
		 try 
		 {
			 if(!Mysql.haveIduser(iduser))//����Ѿ������豸�ţ���ʾ�û�֮ǰ��ҩ��ʹ�ù�
			 {
				 statement.executeUpdate("CREATE TABLE "+"t"+iduser+"(code CHAR(20),name CHAR(20),price CHAR(10),birthday char(10), guaranteePeriod CHAR(10),tag CHAR(50),type CHAR(20),surplus CHAR(10),number CHAR(10),properties TEXT,functions TEXT,us CHAR(200),attentions TEXT,specifications TEXT,eaten CHAR(10),drugHour CHAR(20),drugMinute CHAR(20),attentionTime CHAR(20),dosage CHAR(20),rfid CHAR(20),isEaten CHAR(20))");
				
			 }
			 } catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("�½�ҩƷ��ʧ��");
			e1.printStackTrace();
		 }
		 try 
		 {
			statement.executeUpdate("INSERT INTO user VALUES ('"+iduser+"','"+user+"','"+password+"')");
			statement.executeUpdate("CREATE TABLE "+"s"+iduser+"(tag CHAR(20) ,frequency INT ,time INT)");
			
		 } catch (SQLException e) {
			System.out.println("�û��Ѵ���");
			e.printStackTrace();
			return;
		 	}
//		 try 
//		 {
//			 if(!Mysql.haveIduser(iduser))//����Ѿ������豸�ţ���ʾ�û�֮ǰ��ҩ��ʹ�ù�
//				 statement.executeUpdate("CREATE TABLE "+"t"+iduser+"(code CHAR(20),name CHAR(20),price CHAR(10),birthday char(10), guaranteePeriod CHAR(10),tag CHAR(50),type CHAR(20),surplus CHAR(10))");
//		 } catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			System.out.println("�½�ҩƷ��ʧ��");
//			e1.printStackTrace();
//		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 	}
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 	}
	 }
	 //�û��ĵ�¼
	public static boolean login(String user ,String password)
	{
		connect();
		ResultSet set=null;
		try 
		{
			set=statement.executeQuery("SELECT password FROM user where name='"+user+"'" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(set.next())
			{
				if(password.equals(set.getString(1)))
						 return true;
					 else
						 return false;
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		return false;
	}
	//��õ�ǰҩƷ���ķ���
	public static String getNumber(String iduser)
	{
		connect();
		ResultSet set=null;
		String number="0";
		try 
		{
			set=statement.executeQuery("select number from t"+iduser+"  order by number DESC limit 1 ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			while(set.next())
			{
				
				 number=set.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("��ȡ��ǰҩƷ��������");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("��ǰҩƷ����Ϊ��"+number);
		 
		return number;
	}
	 //����ҩƷ
	 public static void addDrug(String iduser,String code,String name,String price,String birthday,String guaranteePeriod,String tag,String type,String surplus,String properties,String functions,String usage,String attentions,String specifications) throws SQLException
	 {
		
		 connect();
		 String number=Mysql.getNumber(iduser);
		 int num=Integer.parseInt(number);
		 num++;
		 number=num+"";
		
		 try 
		 {
			 
			statement.executeUpdate("INSERT INTO "+"t"+iduser+" VALUES ('"+code+"','"+name+"','"+price+"','"+birthday+"','"+guaranteePeriod+"','"+tag+"','"+type+"','"+surplus+"','"+number+"','"+properties+"','"+functions+"','"+usage+"','"+attentions+"','"+specifications+"','no',"+"'δ����',"+"'δ����' ,"+"'δ����' ,"+"'δ����' ,"+"'δ����' ,"+"'no'"+")");
		 } catch (SQLException e) {
			System.out.println("����ҩƷ����");
			e.printStackTrace();
		 	}
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 	}
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 	}
	 }
	//ɾ��ҩƷ
		 public static void deleteDrug(String iduser,String number )
		 {
			 connect();
			 try 
			 {
				statement.executeUpdate("DELETE FROM "+"t"+iduser+" WHERE number='"+number+"'");
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 	}
			 try 
			 {
				statement.close();
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 	}
			 try 
			 {
				connection.close();
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 	}
		 }
	 //�������豸�˷���ҩƷ��Ϣ�ķ���
	 public static String getTable(String iduser)
	 {
		 connect();
		 ResultSet set=null;
		 drugTable=new String[100];
		 //����ӱ��л�ȡҩƷ����
		 try 
		 {
			 set=statement.executeQuery("SELECT ALL code,name,price,birthday,guaranteePeriod,tag,type,surplus,properties,functions,us,attentions,specifications,number FROM "+"t"+iduser);
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 //		 ********��ÿһ��ҩ����Ϣ���浽������
		 try 
		 {
			
			 int i=0;
			while(set.next())
			 {
				 System.out.println(i);
				 String code=set.getString(1);
				 String name=set.getString(2);
				 String price=set.getString(3);
				 String birthday=set.getString(4);
				 String guaranteePeriod=set.getString(5);
				 String tag=set.getString(6);
				 String type=set.getString(7);
				 String surplus=set.getString(8);
				 String properties=set.getString(9);
				 String functions=set.getString(10);
				 String usage=set.getString(11);
				 String attentions=set.getString(12);
				 String specifications=set.getString(13);
				 String number=set.getString(14);
				 String drugMessage=code+"-"+name+"-"+type+"-"+tag+"-"+price+"-"+birthday+"-"+guaranteePeriod+"-"+surplus+"-"+properties+"-"+functions+"-"+usage+"-"+attentions+"-"+specifications+"-"+number+";";
				 System.out.println(drugMessage);
				 drugTable[i]=drugMessage;
				 i++;
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 String message=drugTable[0];//����Ҫ���ص��ַ���
		 for(int i=1;i<drugTable.length;i++)
		 {
			 if(drugTable[i]!=null)
				 message+=drugTable[i];
		 }
		 System.out.println(message);
		 return message;
	 }
//������ͨ���û�����ö�Ӧ�豸�ŵķ���
	 public static String getIduser(String name)
	 {
		 connect();
		 ResultSet set=null;
		 try 
		 {
			 	set=statement.executeQuery("SELECT iduser FROM user WHERE name='"+name+"'");
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			while(set.next())
			 {
				 return set.getString(1);
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return "none";
	 }
	 //�������ֻ��˷���ҩƷ��Ϣ�ķ���
	 public static String getPTable(String iduser)
	 {
		 connect();
		 ResultSet set=null;
		 drugTable=new String[100];
		 //����ӱ��л�ȡҩƷ����
		 try 
		 {
			 set=statement.executeQuery("SELECT ALL code,name,price,birthday,guaranteePeriod,tag,type,surplus,properties,functions,us,attentions,specifications,number,eaten FROM "+"t"+iduser);
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 //		 ********��ÿһ��ҩ����Ϣ���浽������
		 try 
		 {
			
			 int i=0;
			while(set.next())
			 {
				 System.out.println(i);
				 String code=set.getString(1);
				 String name=set.getString(2);
				 String price=set.getString(3);
				 String birthday=set.getString(4);
				 String guaranteePeriod=set.getString(5);
				 String tag=set.getString(6);
				 String type=set.getString(7);
				 String surplus=set.getString(8);
				 String properties=set.getString(9);
				 String functions=set.getString(10);
				 String usage=set.getString(11);
				 String attentions=set.getString(12);
				 String specifications=set.getString(13);
				 String eaten=set.getString(14);
				 String num=set.getString(15);
				 String drugMessage=code+"-"+name+"-"+type+"-"+tag+"-"+price+"-"+birthday+"-"+guaranteePeriod+"-"+surplus+"-"+properties+"-"+functions+"-"+usage+"-"+attentions+"-"+specifications+"-"+eaten+"-"+num+";";
				 System.out.println(drugMessage);
				 drugTable[i]=drugMessage;
				 i++;
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 String message=drugTable[0];//����Ҫ���ص��ַ���
		 for(int i=1;i<drugTable.length;i++)
		 {
			 if(drugTable[i]!=null)
				 message+=drugTable[i];
		 }
		 System.out.println(message);
		 return message;
		 
	 }
	 //�����ʾ���ҩƷ��Ӧ��Ӧ֢�ķ���
	 public static String getTag(String iduser,String number)
	 {
		 connect();
		 ResultSet set=null;
		 try 
		 {
			 	set=statement.executeQuery("SELECT tag FROM t"+iduser+" WHERE number='"+number+"'");
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			while(set.next())
			 {
				 return set.getString(1);
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return "none";
	 }
	 //�����ʾ����û��Ƿ��ҩ�ķ���
	 public static String getIsEaten(String iduser,String number)
	 {
		 connect();
		 ResultSet set=null;
		 try 
		 {
			 	set=statement.executeQuery("SELECT isEaten FROM t"+iduser+" WHERE number='"+number+"'");
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			while(set.next())
			 {
				 return set.getString(1);
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return "no";
	 }
	 //�����ʾ�޸��豸�Ű󶨵��û�������ķ���
	 public static void setName(String iduser,String name,String password)
	 {
		 connect();
		 try 
		 {
			statement.executeUpdate("UPDATE user SET name='"+name+"', password='"+password+"' WHERE iduser='"+iduser+"'");
		 } catch (SQLException e) {
			System.out.println("����ҩƷ�������ڳ���");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 
	 }
	 //�������޸�ҩƷ�б��������ڵķ���
	 public static void setBirthday(String iduser,String number,String birthday)
	 {
		 connect();
		 
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET birthday='"+birthday+"' WHERE number='"+number+"'");
		 } catch (SQLException e) {
			System.out.println("����ҩƷ�������ڳ���");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 
	 }
	 //�����ǻ����ҩ��ϢP�û�����drugEate;  
	 public static String getDrugEate(String iduser,String number)
	 {
		 connect();
		 ResultSet set=null;
		 try 
		 {
			 	set=statement.executeQuery("SELECT drugHour,drugMinute,attentionTime,dosage FROM t"+iduser+" WHERE number='"+number+"'");
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			while(set.next())
			 {
				 String drugHour=set.getString(1);
				 String drugMinute=set.getString(2);
				 String attentionTime=set.getString(3);
				 String dosage=set.getString(4);
				 return drugHour+"-"+drugMinute+"-"+attentionTime+"-"+dosage+";";
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return "no";
	 }
	 //�������޸�ҩƷ�б����ڵķ���
	 public static void setGuaranteePeriod(String iduser,String number,String guaranteePeriod)
	 {
		 connect();
		
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET guaranteePeriod='"+guaranteePeriod+"' WHERE number='"+number+"'");
		 } catch (SQLException e) {
			System.out.println("����ҩƷ�����ڳ���");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 
	 }
	 //�������޸�ҩƷ�����Ƶķ���
	 public static void setDrugname(String iduser,String number,String name)

	 {
		 connect();
		
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET name='"+name+"' WHERE number='"+number+"'");
		 } catch (SQLException e) {
			System.out.println("�޸�ҩƷ��ʧ��");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 
	 }
	 //������ҩƷ��RFID�ķ���
	 public static void bindingRfid(String iduser,String number,String rfid)
	 {
		 connect();
		 
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET rfid='"+rfid+"' WHERE number='"+number+"'");
		 } catch (SQLException e) {
			System.out.println("��RFIDʧ��");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 }
	 //�����ʾ��ҩƷ����ҩƷ��û�гԵķ���
	 public static void setIseaten(String iduser,String rfid)
	 {
		 connect();
		
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET isEaten='"+"yes"+"' WHERE rfid='"+rfid+"'");
		 } catch (SQLException e) {
			System.out.println("����ҩƷ�Ѿ�����ʧ��");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 }
	 //�������޸�ҩƷdrugHour�ķ���
	 public static void setDrugHour(String iduser,String number,String drugHour)
	 {
		 connect();
			
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET drugHour='"+drugHour+"' WHERE number='"+number+"'");
		 } catch (SQLException e) {
			System.out.println("����ҩƷdrugHourʧ��");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 }
	 //�������޸�ҩƷdrugMinute����
	 public static void setDrugMinute(String iduser,String number,String drugMinute)
	 {
		 connect();
			
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET drugMinute='"+drugMinute+"' WHERE number='"+number+"'");
		 } catch (SQLException e) {
			System.out.println("����ҩƷdrugMinuteʧ��");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 }
	 //�������޸�ҩƷ����ʱ��ķ���
	 public static void setAttentionTime(String iduser,String number,String attentionTime)
	 {
		 connect();
			
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET attentionTime='"+attentionTime+"' WHERE number='"+number+"'");
		 } catch (SQLException e) {
			System.out.println("����ҩƷattentionTimeʧ��");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 }
	 //�������޸�ҩƷ��ҩ�����ķ���
	 public static void setDosage(String iduser,String number,String dosage)
	 {
		 connect();
			
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET dosage='"+dosage+"' WHERE number='"+number+"'");
		 } catch (SQLException e) {
			System.out.println("����ҩƷdosageʧ��");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 }
	 //�������޸�ҩƷ�б�ʣ�����ķ���
	 public static void setSurplus(String iduser,String number,String surplus)
	 {
		 connect();
		
		 try 
		 {
			statement.executeUpdate("UPDATE "+"t"+iduser+" SET surplus='"+surplus+"' WHERE number='"+number+"'");
		 } catch (SQLException e) {
			System.out.println("����ҩƷʣ��������");
			e.printStackTrace();
		 }
		 try 
		 {
			statement.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			connection.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 
	 }
	//�������޸�ҩƷ�б�eaten�ķ���
		 public static void setEaten(String iduser,String number,String eaten)
		 {
			 connect();
			 
			 try 
			 {
//				 System.out.println("UPDATE "+"t"+iduser+" SET eaten='"+eaten+"' WHERE number='"+number+"'");
				statement.executeUpdate("UPDATE "+"t"+iduser+" SET eaten='"+eaten+"' WHERE number='"+number+"'");
			 } catch (SQLException e) {
				System.out.println("����eaten����");
				e.printStackTrace();
			 }
			 try 
			 {
				statement.close();
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
			 try 
			 {
				connection.close();
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
		 
		 }
	 //�����ǲ鿴�Ƿ��Ѵ����豸�ŵķ���
	 public static boolean haveIduser(String iduser)
	 {
		 boolean exist=false;
		 connect();
		 ResultSet set=null;
		 //����ӱ��л�ȡҩƷ����
		 try 
		 {
			 set=statement.executeQuery("SELECT iduser FROM user");
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			 System.out.println("��ѯ�Ƿ����豸�ų���");
			e.printStackTrace();
		 }
		 try 
		 {
			while(set.next())
			 {
				 if(set.getString(1).equals(iduser))
					 exist=true;
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return exist;
	 }
	 //�������ж���Ӧ֢�б����Ƿ��Ѵ��ڴ���Ӧ֢�ķ���
	 public static boolean haveTag(String iduser,String tag)
	 {
		 boolean exist=false;
		 connect();
		 ResultSet set=null;
		 //����ӱ��л�ȡ��Ӧ֢
		 try 
		 {
			 set=statement.executeQuery("SELECT tag FROM s"+iduser);
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			 System.out.println("��ѯ�Ƿ���Ӧ֢����");
			e.printStackTrace();
		 }
		 try 
		 {
			while(set.next())
			 {
				 if(set.getString(1).equals(tag))
					 exist=true;
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return exist;
	 }
	 //�����ʾ��ȡĳ����Ӧ֢Ƶ�ʵķ���
	 public static int getFrequency(String iduser,String tag)
	 {
		 	connect();
			ResultSet set=null;
			int number=1;
			try 
			{
				System.out.println("SELECT frequency "+"FROM s"+iduser+" where tag='"+tag+"'");
				set=statement.executeQuery("SELECT frequency FROM s"+iduser+" where tag ='"+tag+"'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try 
			{
				while(set.next())
				{
					
					 number=Integer.parseInt(set.getString(1));
				}
			} catch (SQLException e) {
				System.out.println("��ȡƵ�ʳ���");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
			return number;
	 }
	 //�����ʾ�豸�˻�ȡʱ���
	 public static String getSchedule(String iduser)
	 {
		 connect();
		 ResultSet set=null;
		 drugTable=new String[100];
		 //����ӱ��л�ȡҩƷ����
		 try 
		 {
//			 System.out.println("SELECT drugHour,drugMinute,attentionTime,dosage FROM "+"t"+iduser+" WHERE eaten='yes'");
			 set=statement.executeQuery("SELECT drugHour,drugMinute,attentionTime,dosage,name FROM "+"t"+iduser+" WHERE eaten ='yes'"+"ORDER BY drugHour");
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 //		 ********��ÿһ��ҩ����Ϣ���浽������
		 try 
		 {
			
			 int i=0;
			while(set.next())
			 {
				
				 String drugHour=set.getString(1);
				 String drugMinute=set.getString(2);
				 String attentionTime=set.getString(3);
				 String dosage=set.getString(4);
				 String name=set.getString(5);
				
				
				 String drugMessage=drugHour+"-"+drugMinute+"-"+attentionTime+"-"+name+"-"+dosage+";";
				 System.out.println(drugMessage);
				 drugTable[i]=drugMessage;
				 i++;
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 String message=drugTable[0];//����Ҫ���ص��ַ���
		 for(int i=1;i<drugTable.length;i++)
		 {
			 if(drugTable[i]!=null)
				 message+=drugTable[i];
		 }
		 System.out.println(message);
		 return message;
	 }
	 //�������޸��û���Ӧ֢�б�ķ���
	 public static void setTag(String iduser,String tag)
	 {
		 connect();
		 //�����ʾ��������Ӧ֢�Ѿ�����
		 if(Mysql.haveTag(iduser, tag))
		 {
			 int frequency=Mysql.getFrequency(iduser, tag);
			 frequency++;
			 try 
			 { 
				 System.out.println("UPDATE "+"s"+iduser+" SET frequency='"+frequency+"' where tag="+tag);
				statement.executeUpdate("UPDATE "+"s"+iduser+" SET frequency='"+frequency+"' where tag='"+tag+"'");
			 } catch (SQLException e) {
				System.out.println("������Ӧ֢Ƶ�ʳ���");
				e.printStackTrace();
			 }
			 try 
			 {
				statement.close();
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
			 try 
			 {
				connection.close();
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
			 
		 }
		 //�����ʾ��Ӧ֢������
		 else
		 {
			 try 
			 {
				 
				statement.executeUpdate("INSERT INTO "+"s"+iduser+" VALUES ('"+tag+"','"+1+"','"+1+"')");
			 } catch (SQLException e) {
				System.out.println("������Ӧ֢����");
				e.printStackTrace();
			 	}
			 try 
			 {
				statement.close();
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 	}
			 try 
			 {
				connection.close();
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
		 }
	 }
	 //�����ʾ��Ƶ�������ȡtag
	 public static String[] getTAGbyFre(String user)
	 {
		 String[] s=new String[10];
		 String iduser=Mysql.getIduser(user);
		 connect();
		 ResultSet set=null;
		 try 
		 {
			 set=statement.executeQuery("SELECT*FROM s"+iduser+" ORDER BY frequency LIMIT 10" );
			
		 } catch (SQLException e) {
			
			e.printStackTrace();
		 }
		 int i=0;
		 try 
		 {
			while(set.next())
			 {
				 s[i]=set.getString(1);
				 i++;
//				 System.out.println(s[i]);
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 String [] ss=new String[i];
		 for(int j=0;j<i;j++)
		 {
			 ss[j]=s[j];
//			 System.out.println(ss[j]);
		 }
		 return ss;
		 
	 }
	 //�����ʾ��ȡ��7���tag�ķ���
	 public static String[] getTAGbyTime(String user)
	 {
		 String[] s=new String[1000];
		 String iduser=Mysql.getIduser(user);
		 connect();
		 ResultSet set=null;
		 try 
		 {
			 set=statement.executeQuery("select * from s"+iduser );
			
		 } catch (SQLException e) {
			
			e.printStackTrace();
		 }
		 int i=0;
		 try 
		 {
			while(set.next())
			 {
				 s[i]=set.getString(1);
				 i++;
//				 System.out.println(s[i]);
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 String [] ss=new String[i];
		 for(int j=0;j<i;j++)
		 {
			 ss[j]=s[j];
//			 System.out.println(ss[j]);
		 }
		 return ss;
		 
	 }
	 //�����ʾ����ĳ��tag��Ƶ��
	 public static int getFrebyTAG(String user,String tag)
	 {
		 String iduser=Mysql.getIduser(user);
		 connect();
		 ResultSet set=null;
		 try 
		 {
			 	set=statement.executeQuery("SELECT frequency FROM s"+iduser+" where tag='"+tag+"'");
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 try 
		 {
			while(set.next())
			 {
				 return Integer.parseInt(set.getString(1));
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return 0 ;
	 }
	 //�����ʾ��ȡĳ��tag���������ķ���
	 public static String[] getTAGmassages(String tag)
	 {
		 connect();
		 String[] ss=new String[100];
		 ResultSet set=null;
		 String checkTable="show tables like '"+tag+"'"; 
		 try 
		 {
			set= connection.getMetaData().getTables(null, null,tag, null );
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } 
		 int i=0;
		 try {
			if (set.next()) 
			 {  
				 set=statement.executeQuery("SELECT * FROM `"+tag+"`");
				 if(set.next())
				 {
					 ss[i]=set.getString(1);
					 i++;
				 }
			     
			 }
			 else
			 {  
			      String[] s=new String[0];
			      return s;
			 
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }  
		 String[] s=new String[i];
		 for(int j=0;j<i;j++)
		 {
			 s[j]=ss[j];
		 }
		 return s;
	 }
	 //�����ǻ�ȡĳ��������ϸ���ݵķ���
	 public static String getMassageDetail(String name)
	 {
		 connect();
		 ResultSet set=null;
		 try 
		 {
			 set=statement.executeQuery("select articleName,introduce,contents,picture from article where articleName='"+name+"'" );
			
		 } catch (SQLException e) {
			
			e.printStackTrace();
		 }
		String message="";
		 try 
		 {
			while(set.next())
			 {
				 message=set.getString(1)+"-"+set.getString(2)+"-"+set.getString(3)+"-"+set.getString(4)+";";
//				 System.out.println(s[i]);
			 }
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	
		return message;
	 }
	 
	 public static void main(String args[])
	 {
//		 System.out.println(Mysql.getNumber("001"));
//		 Mysql.deleteDrug("001", "1");
//		 System.out.println(Mysql.getNumber("001"));
		 //********************
//		 Mysql.setBirthday("001", "4", "151203");
//		 Mysql.setGuaranteePeriod("001", "4", "12");
//		 Mysql.setSurplus("001", "4", "6");
		 //**************************
//		 Mysql.setEaten("001", "1", "yes");
		 	Mysql.setIseaten("001", "11111");
//	Mysql.bindingRfid("001", "1", "11111");
		 
		 //************************
//		 Mysql.setDrugMinute("001", "8", "30");
//		 Mysql.setDosage("001", "8", "2");
//		 Mysql.setAttentionTime("001", "8", "20");
//		 Mysql.setDrugHour("001", "8", "20");
		 //****************************
//		 Mysql.getSchedule("001");
//		Mysql.setSurplus("li", "122121","0");
//		Mysql.getPTable("001");
//		try
//		{
//			Mysql.addDrug("33", "122121", "޽��","12.0","121123","12����","���θ�ð","�г�ҩ","19");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 System.out.println(Mysql.haveIduser("1"));
//		 Mysql.register("33", "asda", "gdfg");
		 
//		Mysql.setBirthday("001", "3", "123456");
//		for(int i=0;i<Mysql.getTAGmassages("all").length;i++)
//		{		
//			System.out.println(Mysql.getTAGmassages("all")[i]);
//		}
//		Mysql.setTag("001", "��ʹ��к");
//		 System.out.println(Mysql.getMassageDetail("��������֢������"));
	 }
	
}
	