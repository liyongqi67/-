package drugs;

public class Data 
{
	static String[] s;
	//�ֽ���״�ַ���
	static String[] explodeTag(String tag)
	{

		tag=tag+",";
		char c = ',';
		int num = 0;
		char[] chars = tag.toCharArray();
		for(int i = 0; i < chars.length; i++)
		{
			if(c == chars[i])
		    {
				num++;
		    }
		}
		System.out.println("�ֽ����Ӧ֢�ĸ���Ϊ"+num);
		s=new String[num];
		for(int i=0;i<s.length;i++)
		{
			s[i]=tag.substring(0, tag.indexOf(','));
			tag=tag.substring((s[i].length()+1));
		}
		return s;
	}
	public static void main(String args[])
	{
		Data.explodeTag("��ʹ��к,����,����,С��йк��ҩ,��������,����ҩƷ,ʳ������");
		for(int i=0;i<s.length;i++)
		{
			System.out.println(s[i]);
		}
	}
		
		
}
