import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


/**
 * InfectStatistic
 * TODO
 *
 * @author xxx
 * @version xxx
 * @since xxx
 */
//ʡ��
class Province{
	String name;
	public int ip,sp,cure,dead;
	//��Ⱦ���ߣ����ƻ��ߣ���������������
}


class InfectStatistic{
    public static void main(String[] args) throws FileNotFoundException{
    	String strLine;
    	Province[] province = new Province[31];
    	String result = "F:\\Users\\lenovo\\Documents\\GitHub\\InfectStatistic-main\\example\\log\\ʡ��.txt";
    	//ʡ�����ļ���ַ
    	FileInputStream fstream = new FileInputStream(new File(result));
		InputStreamReader isr = null;
		
		try{
			isr = new InputStreamReader(fstream,"UTF-8");
		}
		catch(UnsupportedEncodingException e1){
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		} //ָ����UTF-8�������
    	BufferedReader br = new BufferedReader(isr);//����һ��BufferedReader���������ڿ���̨������ֽ�ת����ɵ��ַ���
    	try{
    		int i = 0; 
			while((strLine = br.readLine()) != null){
				province[i] = new Province();
				province[i].name = strLine;
				i++;
			}
		}
    	catch(IOException e){
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
    	
    	result = "F:\\Users\\lenovo\\Documents\\GitHub\\InfectStatistic-main\\example\\log\\2020-01-22.log.txt";
    	//log�ļ���ַ
    	fstream = new FileInputStream(new File(result));
		try{
			isr = new InputStreamReader(fstream,"UTF-8");
		}
		catch(UnsupportedEncodingException e1){
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		} //ָ����UTF-8�������
		br = new BufferedReader(isr);//����һ��BufferedReader���������ڿ���̨������ֽ�ת����ɵ��ַ���
    	try{
			while((strLine = br.readLine()) != null){
				String str[] = strLine.split(" ");//�ÿո�ָ�ÿһ���еļ�¼
				if(!str[0].substring(0,2).equals("//")){
					Update(str,province,str.length);
				}
			}
		}
    	catch(IOException e){
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
    	
    	for(int i = 0;i<31;i++){
    		System.out.println(province[i].name + " "
    				 + "��Ⱦ����" + province[i].ip + " "
    				 + "���ƻ���" + province[i].sp + " "
    				 + "����" + province[i].cure + " "
    				 + "����" + province[i].dead);
    	}
    }

    
	private static void Update(String str[],Province province[],int length){//��ȡLog�ļ������ʡ����Ϣ�䶯
		int num = 0;//��¼�䶯����
		int i = 0;//��¼�䶯ʡ��
		str[length - 1] = str[length - 1].trim();
		if(str[length - 1] != null&&!"".equals(str[length - 1])){
			for(int k = 0;k<str[length - 1].length();k++){
				if(str[length - 1].charAt(k) >= 48 && str[length - 1].charAt(k) <= 57){
					num = 10*num+str[length - 1].charAt(k) - 48;
				}
			}
		}
		
		while(!str[0].equals(province[i].name)&&i<31){
			i++;
		}
		
		if(length == 3){
			if(str[1].equals("����")){//xxʡ�ݻ�������
				province[i].ip -= num;
				province[i].cure += num;
			}
			else if(str[1].equals("����")){//xxʡ�ݻ�������
				province[i].ip -= num;
				province[i].dead += num;
			}	
		}
		else if(length == 4){
			if(str[1].equals("����")){
				if(str[2].equals("��Ⱦ����")){//xxʡ��������Ⱦ����
					province[i].ip += num;
				}
				else if(str[2].equals("���ƻ���")){//xxʡ���������ƻ���
					province[i].sp += num;
				}
			}
			else if(str[1].equals("���ƻ���")){////xxʡ�����ƻ���ȷ��
				province[i].sp -= num;
				province[i].ip += num;
			}
			else if(str[1].equals("�ų�")){//xxʡ�����ƻ����ų�
				province[i].sp -= num;
			}
		}
		else if(length == 5){
			int j = 0;
			while(!str[3].equals(province[j].name)&&j<31){
				j++;
			}
			if(str[1].equals("���ƻ���")){//aʡ���ƻ�������bʡ
				province[i].sp -= num;
				province[j].sp += num;
			}
			else if(str[1].equals("��Ⱦ����")){//aʡ��Ⱦ��������bʡ
				province[i].ip -= num;
				province[j].ip += num;
			}
		}
	}
	

}