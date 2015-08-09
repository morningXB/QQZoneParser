package com.qzoneparser.domain;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.qzoneparser.bean.Message;
import com.qzoneparser.utils.CrawlProcessUtils;
import com.qzoneparser.utils.FileProcessUtils;

public class QZoneParser {
	private static Logger log=Logger.getLogger("com.qzoneparser.domain");
	public static void main(String[] args) throws Exception{
		CrawlProcessUtils crawlProcessUtils = new CrawlProcessUtils();
		ArrayList<Message> messages =  crawlProcessUtils.getCurrentPageMsgs(0);
//		for(int i =0;i<messages.size();i++){
//			System.out.println(messages.get(i));
//		}		
		FileProcessUtils fileProcessUtils = new FileProcessUtils();
		//ArrayList<Long> uins = fileProcessUtils.findAllReplyer(messages);
	//	System.out.println(uins);
//		ArrayList<Message> list = fileProcessUtils.findSimilarBean("¸ç¸ç", messages);
//		System.out.println(list.size());
//		for(Message m :list){
//			System.out.println(m);
//		}
//		String s = fileProcessUtils.readTxtFile("txt/relative.txt");
//		System.out.println(s);
	}
}
