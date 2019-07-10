package drugs;

import drugs.Mysql;

public class PushMassage 
{

	public static String findMassages(String user)
	{
		StringBuffer pushMassage = new StringBuffer("");

		//��ȡ�û�����Ƶ�ʡ�ʱ���tag����
		String[] tagsByFre = Mysql.getTAGbyFre(user);
		String[] tagsByTime = Mysql.getTAGbyTime(user);
		//����ǩ����Ϊ��
		if(tagsByFre.length == 0){
			String[] massages = Mysql.getTAGmassages("all");
			for(int i = 0;i<massages.length;i++){
				pushMassage.append(Mysql.getMassageDetail(massages[i]));
			}
			return pushMassage.toString();
		}

		//������ı�ǩ���з�װ������
		TAG[] tags = new TAG[tagsByTime.length];
		for(int i = 0;i<tags.length;i++){
			tags[i] = new TAG(tagsByTime[i], (tagsByTime.length-i)/tagsByTime.length*0.5*Mysql.getFrebyTAG(user, tagsByTime[i])+tagsByTime.length-i);
		}
	    TAG temp;

	    for(int i=0;i<tags.length;i++){//����

	      for(int j=0;j<tags.length-i-1;j++){//�Ƚϴ���

	        if(tags[j].weight>tags[j+1].weight){

	          temp=tags[j];

	          tags[j]=tags[j+1];

	          tags[j+1]=temp;

	        }

	      }

	    }
	    
	    String[] massages;
	    for(int i = 0;i<tags.length;i++){
	    	massages = Mysql.getTAGmassages(tags[i].tag);
	    	for(int j = 0;j<massages.length;j++){
	    		pushMassage.append(Mysql.getMassageDetail(massages[j]));
	    	}
	    }
	    
	    massages = Mysql.getTAGmassages("all");
		for(int i = 0;i<massages.length;i++){
			pushMassage.append(Mysql.getMassageDetail(massages[i]));
		}
		return pushMassage.toString();

		
	}
	
	private static class TAG
	{
		String tag;
		double weight;
		
		public TAG(String tag, double weight) {
			this.tag = tag;
			this.weight = weight;
		}
	}
	public static String[] handleTags(String tag){
		String[] tags = Divider.transJe(tag, "gb2312", "utf-8").split(",");

		for(int i = 0; i < tags.length; i++){
			if(tags[i].matches("����|��ʹ|ҩƷ")){
				tags[i] = "none";
			}
			else if(tags[i].equals("����")){
				tags[i] = "Ť��";
			}
			else if(tags.equals("����")){
				tags[i] = "����";
			}
			else if(tags[i].matches(".*����.*")){
				tags[i] = "����";
			}
			else if(tags[i].matches(".*��ʪ.*")){
				tags[i] = "��ʪ";
			}
			
		}
		
		//��÷�none����
		int size = 0;
		for(int i = 0;i < tags.length;i++){
			if(!tags[i].equals("none")){
				size++;
			}
		}
		
		//���Ʒ�none����
		String[] result = new String[size];
		int j = 0;
		for(int i = 0; i<tags.length;i++){
			if(!tags[i].equals("none")){
				result[j] = tags[i];
				j++;
			}
		}

		
		return result;


	}

	public static void main(String[]args)
	{
		System.out.println(PushMassage.findMassages("li"));
		
	}
}
