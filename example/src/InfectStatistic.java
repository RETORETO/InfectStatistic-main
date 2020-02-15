import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	private static final int PROVINCE_NUM = 32;
	private static final String PROVINCE[] ={"ȫ��","����","����","����","����","����","�㶫","����","����",
				"����","�ӱ�","����","������","����","����","����","����","����","����","���ɹ�","����","�ຣ",
				"ɽ��","ɽ��","����","�Ϻ�","�Ĵ�","���","����","�½�","����","�㽭"
	};
	
	
    public static void main(String[] args) throws IOException{
    	String log = null,out = null,date = null,content = "";
    	boolean[] type = new boolean[5];//type[0]���ڼ�¼�Ƿ���-type�����в�����1-4���ڼ�¼��������
    	boolean [] prov = new boolean[PROVINCE_NUM+1];//boolean[PROVINCE_NUM]���ڼ�¼�Ƿ�������-province������ǰ��Ĳ��ּ�¼��������
    	
    	Province[] province = new Province[PROVINCE_NUM];
    	initProvince(province);//��ʼ��ʡ����Ϣ
    	
    	if(args.length > 0 && args[0].equals("list")){
    		for(int i = 1;i+1 < args.length;i++){
    			int temp = 0;
    			if(args[i].substring(0,1).equals("-")){
    				switch(args[i]){
    					case "-log":
    						log = args[i+1];
    						break;
    					case "-out":
    						out = args[i+1];
    						break;
    					case "-date":
    						date = args[i+1];
    						break;
    					case "-type":
    						while(i+1 < args.length && !args[i+1].substring(0,1).equals("-")  && !args[i+1].equals(null)){
    							switch(args[i+1]) {
    								case "��Ⱦ����":
    									type[0]=true;
    									type[1] = true;
    									break;
    								case "���ƻ���":
    									type[0]=true;
    									type[2] = true;
    									break;
    								case "����":
    									type[0]=true;
    									type[3] = true;
    									break;
    								case "����":
    									type[0]=true;
    									type[4] = true;
    									break;
    							}
    							i++;
    						}
    						break;
    					case "-province":
    						while(i+1 < args.length && !args[i+1].substring(0,1).equals("-")  && !args[i+1].equals(null)){
    							for(int j = 0;j < PROVINCE_NUM;j++){
    								if(args[i+1].equals(province[j].name)){
    									prov[PROVINCE_NUM] = true;
    									prov[j] = true;
    									j = 32;
    								}
    							}
    							i++;
    						}
    						break;
    				}
    			}
    		}
    		
    		if(log == null || out == null){
    			System.out.println("û������log��û������out������������");
    		}
    		else{
    			initProvince(province);
    			List<File> files = searchFiles(new File(log),".log.txt");
    			for(File file:files){
    				String[] file1 = file.getAbsolutePath().split("\\\\");
    				if(date == null || timeCompare(date,file1[file1.length-1].substring(0,10)) > 0){
    					update(file.getAbsolutePath(),province);
    				}
    				
                }
  			}
  			
    		if(!type[0]){//û������-type����ʱ����������Ա�����
    			type[1] = true;
				type[2] = true;
				type[3] = true;
				type[4] = true;
    		}
    		
  			for(int i = 1;i < PROVINCE_NUM;i++){
  				province[0].ip += province[i].ip;
  				province[0].sp += province[i].sp;
  				province[0].cure += province[i].cure;
  				province[0].dead += province[i].dead;
  			}//����ȫ���������
  			
  			if(!prov[PROVINCE_NUM]){
  				for(int i = 0;i < PROVINCE_NUM;i++){
  	 				if(province[i].ip != 0 || province[i].sp != 0 || province[i].cure != 0 || province[i].dead != 0){
  	 					content = content + province[i].name + " ";
  	 					if(type[1]) {
  	 						content = content + "��Ⱦ����" + province[i].ip + "��" + " ";
  	 					}
  	 					if(type[2]) {
  	 						content = content + "���ƻ���" + province[i].sp + "��" + " ";
  	 					}
  	 					if(type[3]) {
  	 						content = content + "����" + province[i].cure + "��" + " ";
  	 					}
  	 					if(type[4]) {
  	 						content = content + "����" + province[i].dead + "��";
  	 					}  	  		    			
  	  		    		content = content + "\n";
  	  				}
  	  			}//�����ʡ������
  			}
  			else {
  				for(int i = 0;i < PROVINCE_NUM;i++){
  	 				if(prov[i]){
  	 					content = content + province[i].name + " ";
  	 					if(type[1]) {
  	 						content = content + "��Ⱦ����" + province[i].ip + "��" + " ";
  	 					}
  	 					if(type[2]) {
  	 						content = content + "���ƻ���" + province[i].sp + "��" + " ";
  	 					}
  	 					if(type[3]) {
  	 						content = content + "����" + province[i].cure + "��" + " ";
  	 					}
  	 					if(type[4]) {
  	 						content = content + "����" + province[i].dead + "��";
  	 					}  	  		    			
  	  		    		content = content + "\n";
  	  				}
  	  			}//�����ʡ������
  			}
  				
  			content = content + "// ���ĵ�������ʵ���ݣ���������ʹ��";
  			write(out,content);
  			  	
    	}
    }

    
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
				int i = 1;//��¼�䶯ʡ��
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
					int j = 1;
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
	public static void write(String out,String content){    
		FileOutputStream fstream = null;
		File file = new File(out);
			try{
				if(file.exists()){
					file.createNewFile();
				}
				fstream = new FileOutputStream(file);
				fstream.write(content.getBytes());
				fstream.flush();
				fstream.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	
	
	/*���Ҷ�ӦĿ¼�µ��ļ�*/
	public static List<File> searchFiles(File folder,String keyWord){
		List<File> result = new ArrayList<File>();
		if(folder.isFile()){
			result.add(folder);
		}
		
		File[] subFolders = folder.listFiles(new FileFilter(){
			public boolean accept(File file){
				if(file.isDirectory()){
					return true;
				}
				if(file.getName().toLowerCase().endsWith(keyWord)){
					return true;
				}
				return false;
			}
		});
 
        if(subFolders != null){
        	for(File file : subFolders){
        		if(file.isFile()){
                    result.add(file);
                } 
            }
        }
        return result;
    }
	
	
	/*�Ƚ�����ʱ��ǰ��*/
	public static int timeCompare(String time1,String time2){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1=Calendar.getInstance();
		Calendar c2=Calendar.getInstance();
		
		try{
			c1.setTime(formatter.parse(time1));
			c2.setTime(formatter.parse(time2));
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		
		int result=c1.compareTo(c2);
		return result;
	}
}