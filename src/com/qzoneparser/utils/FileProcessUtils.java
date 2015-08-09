package com.qzoneparser.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.ietf.jgss.Oid;

import com.qzoneparser.bean.Message;

public class FileProcessUtils {

	public int getAllCount(ArrayList<Message> messages) {
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

	public ArrayList<Message> getAllMessagesByUin(ArrayList<Message> messages,
			Long uin) {
		ArrayList<Message> specialmessages = new ArrayList<Message>();
		for (int i = 0; i < messages.size(); i++) {
			if (messages.get(i).getUin() == uin) {
				specialmessages.add(messages.get(i));
			}
		}
		return specialmessages;
	}

	/**
	 * TYPE 亲人 一般朋友 同学 男女朋友 陌生人 根据一定的规则，检索某个人的所有留言，与自定义的关键词对比，按一定的比例计算最终的亲密度
	 * 
	 * @param messages
	 *            所有留言
	 * @return 亲密得分
	 */
	public double firstAnalysis(ArrayList<Message> messages, Long uin) {
		ArrayList<Message> messagesfromone = getAllMessagesByUin(messages, uin);
		// 第一个项为留言的占百分比例 percentage
		// 1-10分
		double percentage = messagesfromone.size() / getAllCount(messages);
		double score1 = percentage * 10;

		// 第二项为最新留言与当前时间的比较
		// 1-10
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String replytime = messagesfromone.get(0).getPubtime();// 最新的一条留言
		Date current = null;
		try {
			current = format.parse(replytime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double score2 = timeinternal(current);

		// 第三项为最新留言与最早留言的比较
		String latest = messagesfromone.get(0).getPubtime();
		String oldest = messagesfromone.get(messagesfromone.size())
				.getPubtime();
		Date latest_time = null;
		Date oldest_time = null;
		try {
			latest_time = format.parse(latest);
			oldest_time = format.parse(oldest);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double score3 = maxinternal(latest_time, oldest_time);
		
		//第四项为回复数量占比
		
		double score4 = beReplied(messagesfromone);
		
		return 0;
	}
	/**
	 * 根据回复的数量占比计算分数
	 * @param messagesfromone
	 * @return
	 */
	public double beReplied(ArrayList<Message> messagesfromone) {
		
		return 0;
	}

	/**
	 * 根据最新和最老的留言间隔计算分数
	 * 
	 * @param latest_time
	 * @param oldest_time
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public double maxinternal(Date latest_time, Date oldest_time) {
		int days = 0;
		if (latest_time.compareTo(oldest_time) == 0) {// 第一次留言
			return 1;
		} else {
			int year = latest_time.getYear() - oldest_time.getYear();
			int month = latest_time.getMonth()-oldest_time.getMonth();
			int day = latest_time.getDay()-oldest_time.getDate();
			if (year > 2) {
				return 10;
			} else {
				days = year*365+30*month+day;
				return 1+days/3/365*9;
			}
		}
	}

	/**
	 * 读取txt文件
	 * 
	 * @param filePath
	 * @return
	 */
	public String readTxtFile(String filePath) {
		String result = "";
		try {
			// String encoding="GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					result = result + lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 计算时间间隔的得分
	 * 
	 * 分数的计算规则2015-08-04 13：39:28
	 * 
	 * @param current
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public double timeinternal(Date current) {
		Date now = new Date();
		int year = now.getYear() - current.getYear();
		if (year == 0) {
			int month = now.getMonth() - current.getMonth();
			int day = now.getDay() - current.getDay();
			int days = 30 * month + day;
			return 10 - days / 365 * 10;
		} else {
			return 0;
		}
	}

}
