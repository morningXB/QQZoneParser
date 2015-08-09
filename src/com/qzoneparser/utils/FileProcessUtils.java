package com.qzoneparser.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.qzoneparser.bean.Message;

public class FileProcessUtils {
	
	
	
	
	public int getAllCount(ArrayList<Message> messages){
		return messages.size();
	}

	/**
	 * 
	 * @param uin�����˵�qq��
	 * @return �������ԵĴ���
	 */
	public int gettimesByUin(String uin, ArrayList<Message> messages) {
		int count = 0;
		if (uin != null) {
			for (int i = 0; i < messages.size(); i++) {
				if (uin.equals(messages.get(i).getUin())) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 
	 * @param nickname
	 *            �����˵��ǳ�
	 * @return ����
	 */
	public int gettimeByNickname(String nickname, ArrayList<Message> messages) {
		int count = 0;
		if (nickname != null) {
			for (int i = 0; i < messages.size(); i++) {
				if (nickname.equals(messages.get(i).getNickname())) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * �����й����ݼ�����������
	 * 
	 * @param content
	 *            ��������
	 * @return
	 */
	public ArrayList<Message> findSimilarBean(String content,
			ArrayList<Message> messages) {
		ArrayList<Message> result = new ArrayList<Message>();
		if (content != null && !"".equals(content.trim())) {
			for (int i = 0; i < messages.size(); i++) {
				if (messages.get(i).getHtmlContent().contains(content)) {
					result.add(messages.get(i));
				}
			}
		}
		return result;
	}

	/**
	 * �õ����лظ��˵�qq
	 * 
	 * @param messages
	 * @return
	 */
	public ArrayList<Long> findAllReplyer(ArrayList<Message> messages) {
		ArrayList<Long> uins = new ArrayList<Long>();
		for (int i = 0; i < messages.size(); i++) {
			if (!uins.contains(messages.get(i).getUin())) {
				uins.add(messages.get(i).getUin());
			}
		}
		return uins;
	}
	
	public ArrayList<Message> getAllMessagesByUin(ArrayList<Message> messages,Long uin){
		ArrayList<Message> specialmessages  = new ArrayList<Message>();
		for(int i =0;i<messages.size();i++){
			if(messages.get(i).getUin()==uin){
				specialmessages.add(messages.get(i));
			}
		}
		return specialmessages;
	}

    /**
     * TYPE ����       һ������        ͬѧ            ��Ů����          İ����
     * ����һ���Ĺ��򣬼���ĳ���˵��������ԣ����Զ���Ĺؼ��ʶԱȣ���һ���ı����������յ����ܶ�
     * @param messages ��������
     * @return  ���ܵ÷�
     */
	public double firstAnalysis(ArrayList<Message> messages,Long uin){
		ArrayList<Message> messagesfromone = getAllMessagesByUin(messages, uin); 
		//��һ����Ϊ���Ե�ռ�ٷֱ���      percentage
		//  1-10�� 
		double percentage = messagesfromone.size()/getAllCount(messages);
		double score1 = percentage*10;
		
		//�ڶ���Ϊ���Ե�Ƶ��
		//
		
		
		return 0;
	}
	
	/**
	 * ��ȡtxt�ļ� 
	 * @param filePath
	 * @return
	 */
	 public String readTxtFile(String filePath){
		  String result ="";
	        try {
	              //  String encoding="GBK";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file));
	                    //���ǵ������ʽ
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                        result=result+lineTxt;
	                    }
	                    read.close();
	        }else{
	            System.out.println("�Ҳ���ָ�����ļ�");
	        }
	        } catch (Exception e) {
	            System.out.println("��ȡ�ļ����ݳ���");
	            e.printStackTrace();
	        }
	        	return result;
	    }
	
	
}
