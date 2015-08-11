package com.qzoneparser.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.qzoneparser.bean.Message;
import com.qzoneparser.config.Constants;
import com.qzoneparser.utils.CrawlProcessUtils;
import com.qzoneparser.utils.FileProcessUtils;

public class QZoneParser {
	@SuppressWarnings("unused")
	private static Logger log=Logger.getLogger("com.qzoneparser.domain");
	public static void main(String[] args) throws Exception{
		System.out.print("«Î ‰»Î“™ºÏ≤‚µƒQQ£∫");
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		Constants.HOST_UIN = str;
		ArrayList<Message> messages =  CrawlProcessUtils.getAllMsgs();		
		ArrayList<Long> nicknames = FileProcessUtils.findAllReplyer(messages);
		for(Long nickname:nicknames){
			double point = FileProcessUtils.firstAnalysis(messages, nickname); 
			System.out.println(nickname+"  :"+point);
			}
//	System.out.println(nicknames);
//	System.out.949407411println(FileProcessUtils.getAllMessagesByNickName(messages, nicknames.get(10)));
	}
}
