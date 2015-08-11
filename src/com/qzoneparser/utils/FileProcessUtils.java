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
import com.qzoneparser.bean.Reply;
import com.qzoneparser.config.Constants;

public class FileProcessUtils {

	public static int getAllCount(ArrayList<Message> messages) {
		return messages.size();
	}

	/**
	 * 
	 * @param uin留言人的qq号
	 * @return 返回留言的次数
	 */
	public static int gettimesByUin(String uin, ArrayList<Message> messages) {
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
	public static int gettimeByNickname(String nickname, ArrayList<Message> messages) {
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
	public static ArrayList<Message> findSimilarBean(String content,
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
	public static ArrayList<Long> findAllReplyer(ArrayList<Message> messages) {
		ArrayList<Long> uins = new ArrayList<Long>();
		for (int i = 0; i < messages.size(); i++) {
			if (!uins.contains(messages.get(i).getUin())) {
				uins.add(messages.get(i).getUin());
			}
		}
		return uins;
	}
	

	public static ArrayList<Message> getAllMessagesByNickName(ArrayList<Message> messages,
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
	public static double firstAnalysis(ArrayList<Message> messages, Long uin) {
		ArrayList<Message> messagesfromone = getAllMessagesByNickName(messages, uin);
		// 第一个项为留言的占百分比例 percentage
		// 1-10分
		double percentage = messagesfromone.size() /(double)getAllCount(messages);
		double score1 = percentage * 10;
		// 第二项为最新留言与当前时间的比较
		// 1-10
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String replytime = messagesfromone.get(0).getPubtime();// 最新的一条留言
		//System.out.println(replytime);
		Date current = null;
		try {
			current = format.parse(replytime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double score2 = timeinternal(current);

		// 第三项为最新留言与最早留言的比较
		String latest = messagesfromone.get(0).getPubtime();
		//System.out.println(latest);
		String oldest = messagesfromone.get(messagesfromone.size() - 1)
				.getPubtime();
		//System.out.println(oldest);
		Date latest_time = null;
		Date oldest_time = null;
		try {
			latest_time = format.parse(latest);
			oldest_time = format.parse(oldest);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double score3 = maxinternal(latest_time, oldest_time);

		// 第四项为回复数量占比
		double score4 = beReplied(messagesfromone);
		System.out.println(score1*0.7 + " " + score2*0.05 + "  " + score3*0.05 + "  "
				+ score4*0.2);
		// 第五项为关键字检索加分
		return score1 * 0.7 + score2 * 0.05 + score3 * 0.05 + score4 * 0.2;
	}

	/**
	 * 根据回复的数量占比计算分数
	 * 
	 * @param messagesfromone
	 * @return
	 */
	public static double beReplied(ArrayList<Message> messagesfromone) {
		double count = 0;
		for (int i = 0; i < messagesfromone.size(); i++) {
			ArrayList<Reply> replies = messagesfromone.get(i).getReplyList();
			for (int j = 0; j < replies.size(); j++) {
				if (replies.get(j).getUin().toString().equals(Constants.HOST_UIN)) {
					count = count + 1;
					break;
				}
			}
		}
		return count / (double)messagesfromone.size() * 10;
	}

	/**
	 * 根据最新和最老的留言间隔计算分数
	 * 
	 * @param latest_time
	 * @param oldest_time
	 * @return
	 */

	@SuppressWarnings("deprecation")
	public static double maxinternal(Date latest_time, Date oldest_time) {
		double days = 0;
		double year = latest_time.getYear() - oldest_time.getYear();
		if (latest_time.compareTo(oldest_time) == 0) {// 第一次留言
			return 1;
		} else {
			if (year > 2) {
				return 10;
			} else {
				days = (latest_time.getTime()-oldest_time.getTime())/86400000;
				double p = 1 + days / 3 / 365 * 9;
				return p;
			}
		}
	}

	/**
	 * 读取txt文件
	 * 
	 * @param filePath
	 * @return
	 */
	public  static String readTxtFile(String filePath) {
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
	public static double timeinternal(Date current) {
		Date now = new Date();
		int year = now.getYear() - current.getYear();
		if (year <= 2) {
			double days = (now.getTime()-current.getTime())/86400000;
			double p = 10 - (days / 365.0 / 3) * 10;
			return p;
		} else {
			return 0;
		}
	}

}
