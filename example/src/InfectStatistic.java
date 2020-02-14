import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * InfectStatistic
 * TODO
 *
 * @author xxx
 * @version xxx
 * @since xxx
 */
/*ʡ�������Ϣ*/
class Province{
	String name;
	public int ip,sp,cure,dead;
	//��Ⱦ���ߣ����ƻ��ߣ���������������
}


class InfectStatistic{
	private static final int PROVINCE_NUM = 31;
	private static final String PROVINCE[] = {"����","����","����","����","����","�㶫","����","����",
				"����","�ӱ�","����","������","����","����","����","����","����","����","���ɹ�","����","�ຣ",
				"ɽ��","ɽ��","����","�Ϻ�","�Ĵ�","���","����","�½�","����","�㽭"
	};
	
	
    public static void main(String[] args) throws IOException{
    	String log = null,data = null,type = null,strLine = null;
		String content = null;
    	
    	Province[] province = new Province[PROVINCE_NUM+1];
    	initProvince(province);//��ʼ��ʡ����Ϣ
    	
    	if(args[0].equals("list")){
    		for(int i = 1;i < args.length;i++){
    			if(args[i].substring(0,1).equals("-")){
    				switch(args[i]){
    					case "-log":
    						log = args[i+1];
    						break;
    					case "-data":
    						data = args[i+1];
    						break;
    					case "-date":
    						break;
    					case "-type":
    						break;
    					case "-province":
    						break;
    				}
    			}
    		}
			/*
			 * log = "D:\\log\\2020-01-22.log.txt";//�������ݣ����Ӧɾ��
			 */
    		if(log == null || data == null){
    			System.out.println("û������log��û������data������������");
    		}
    		else{
    			initProvince(province);
    			List<File> files = searchFiles(new File(log),".log.txt");
    			for (File file:files) {
    				update(file.getAbsolutePath(),province);
                }
  			}
  			
  			province[PROVINCE_NUM] = new Province();
  			province[PROVINCE_NUM].name = "ȫ��";
  			for(int i = 0;i < PROVINCE_NUM;i++){
  				province[PROVINCE_NUM].ip += province[i].ip;
  				province[PROVINCE_NUM].sp += province[i].sp;
  				province[PROVINCE_NUM].cure += province[i].cure;
  				province[PROVINCE_NUM].dead += province[i].dead;
  			}//����ȫ���������
  			
  			content = "ȫ��" + " "
    			+ "��Ⱦ����" + province[PROVINCE_NUM].ip + "��" + " "
    			+ "���ƻ���" + province[PROVINCE_NUM].sp + "��" + " "
    			+ "����" + province[PROVINCE_NUM].cure + "��" + " "
    			+ "����" + province[PROVINCE_NUM].dead + "��" + "\n";//����ȫ���������
  			for(int i = 0;i < PROVINCE_NUM;i++){
 				if(province[i].ip != 0 || province[i].sp != 0 || province[i].cure != 0 || province[i].dead != 0){
 					content = content + province[i].name + " "
  		    			+ "��Ⱦ����" + province[i].ip + "��" + " "
  		    			+ "���ƻ���" + province[i].sp + "��" + " "
  		    			+ "����" + province[i].cure + "��" + " "
  		    			+ "����" + province[i].dead + "��" + "\n";
  				}
  			}//�����ʡ������
  			  	
  			content = content + "// ���ĵ�������ʵ���ݣ���������ʹ��";
  			write("D:\\out.txt",content);
  			  	
    	}
    }
    		
			/*
			 * for(int i = 0;i < PROVINCE_NUM;i++){ System.out.println(province[i].name +
			 * " " + "��Ⱦ����" + province[i].ip + " " + "���ƻ���" + province[i].sp + " " + "����" +
			 * province[i].cure + " " + "����" + province[i].dead); }
			 */

    
    /*��ʼ��ʡ����Ϣ*/
    private static void initProvince(Province province[]){
    	for(int i = 0;i < PROVINCE_NUM;i++){
    		province[i] = new Province();
    		province[i].name = PROVINCE[i];
    	}
    	
    }
    
    
    /*��ȡLog�ļ������ʡ����Ϣ�䶯*/
	private static void update(String log,Province province[]) throws IOException{
		String strLine;
		FileInputStream fstream = new FileInputStream(new File(log));
		InputStreamReader isr = new InputStreamReader(fstream,"UTF-8");
		BufferedReader br = new BufferedReader(isr);//����һ��BufferedReader���������ڿ���̨������ֽ�ת����ɵ��ַ���
		
		while((strLine = br.readLine()) != null){
			String str[] = strLine.split(" ");//�ÿո�ָ�ÿһ���еļ�¼
		  	if(!str[0].substring(0,2).equals("//")){//������"//"��ͷ�ļ�¼��
				int num = 0;//��¼�䶯����
				int i = 0;//��¼�䶯ʡ��
				int length = str.length;
				str[length - 1] = str[length - 1].trim();
				if(str[length - 1] != null && !"".equals(str[length - 1])){
					for(int k = 0;k < str[length - 1].length();k++){
						if(str[length - 1].charAt(k) >= 48 && str[length - 1].charAt(k) <= 57){
							num = 10*num+str[length - 1].charAt(k) - 48;
						}
					}
				}
				
				while(!str[0].equals(province[i].name) && i < PROVINCE_NUM){
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
					while(!str[3].equals(province[j].name) && j < PROVINCE_NUM){
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
	}
	
	
	/*дTXT�ļ����浽ָ��λ��*/
	public static void write(String data,String content){    
		FileOutputStream fstream = null;
		File file = new File(data);
			try{
				if(file.exists()){
					file.createNewFile();
				}
				fstream = new FileOutputStream(file);
				fstream.write(content.getBytes());
				fstream.flush();
				fstream.close();
			}
			catch (Exception e){
				e.printStackTrace();
			}
	}
	
	
	/*���Ҷ�ӦĿ¼�µ��ļ�*/
	public static List<File> searchFiles(File folder,String keyWord){
		List<File> result = new ArrayList<File>();
		if (folder.isFile()){
			result.add(folder);
		}
		
		File[] subFolders = folder.listFiles(new FileFilter(){
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				}
				if (file.getName().toLowerCase().endsWith(keyWord)) {
					return true;
				}
				return false;
			}
		});
 
        if (subFolders != null){
        	for (File file : subFolders){
        		if (file.isFile()){
                    result.add(file);
                } 
            }
        }
        return result;
    }
}