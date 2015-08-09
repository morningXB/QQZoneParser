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
	 * @param uin留言人的qq号
	 * @return 返回留言的次数
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
	 *            留言人的昵称
	 * @return 次数
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
	 * 根据有关内容检索相似留言
	 * 
	 * @param content
	 *            检索内容
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
	 * 得到所有回复人的qq
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
     * TYPE 亲人       一般朋友        同学            男女朋友          陌生人
     * 根据一定的规则，检索某个人的所有留言，与自定义的关键词对比，按一定的比例计算最终的亲密度
     * @param messages 所有留言
     * @return  亲密得分
     */
	public double firstAnalysis(ArrayList<Message> messages,Long uin){
		ArrayList<Message> messagesfromone = getAllMessagesByUin(messages, uin); 
		//第一个项为留言的占百分比例      percentage
		//  1-10分 
		double percentage = messagesfromone.size()/getAllCount(messages);
		double score1 = percentage*10;
		
		//第二项为留言的频率
		//
		
		
		return 0;
	}
	
	/**
	 * 读取txt文件 
	 * @param filePath
	 * @return
	 */
	 public String readTxtFile(String filePath){
		  String result ="";
	        try {
	              //  String encoding="GBK";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){ //判断文件是否存在
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file));
	                    //考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                        result=result+lineTxt;
	                    }
	                    read.close();
	        }else{
	            System.out.println("找不到指定的文件");
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	        }
	        	return result;
	    }
	
	
}
