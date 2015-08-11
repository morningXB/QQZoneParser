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
	 * @param uin�����˵�qq��
	 * @return �������ԵĴ���
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
	 *            �����˵��ǳ�
	 * @return ����
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
	 * �����й����ݼ�����������
	 * 
	 * @param content
	 *            ��������
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
	 * �õ����лظ��˵�qq
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
	 * TYPE ���� һ������ ͬѧ ��Ů���� İ���� ����һ���Ĺ��򣬼���ĳ���˵��������ԣ����Զ���Ĺؼ��ʶԱȣ���һ���ı����������յ����ܶ�
	 * 
	 * @param messages
	 *            ��������
	 * @return ���ܵ÷�
	 */
	public static double firstAnalysis(ArrayList<Message> messages, Long uin) {
		ArrayList<Message> messagesfromone = getAllMessagesByNickName(messages, uin);
		// ��һ����Ϊ���Ե�ռ�ٷֱ��� percentage
		// 1-10��
		double percentage = messagesfromone.size() /(double)getAllCount(messages);
		double score1 = percentage * 10;
		// �ڶ���Ϊ���������뵱ǰʱ��ıȽ�
		// 1-10
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String replytime = messagesfromone.get(0).getPubtime();// ���µ�һ������
		//System.out.println(replytime);
		Date current = null;
		try {
			current = format.parse(replytime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double score2 = timeinternal(current);

		// ������Ϊ�����������������ԵıȽ�
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

		// ������Ϊ�ظ�����ռ��
		double score4 = beReplied(messagesfromone);
		System.out.println(score1*0.7 + " " + score2*0.05 + "  " + score3*0.05 + "  "
				+ score4*0.2);
		// ������Ϊ�ؼ��ּ����ӷ�
		return score1 * 0.7 + score2 * 0.05 + score3 * 0.05 + score4 * 0.2;
	}

	/**
	 * ���ݻظ�������ռ�ȼ������
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
	 * �������º����ϵ����Լ���������
	 * 
	 * @param latest_time
	 * @param oldest_time
	 * @return
	 */

	@SuppressWarnings("deprecation")
	public static double maxinternal(Date latest_time, Date oldest_time) {
		double days = 0;
		double year = latest_time.getYear() - oldest_time.getYear();
		if (latest_time.compareTo(oldest_time) == 0) {// ��һ������
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
	 * ��ȡtxt�ļ�
	 * 
	 * @param filePath
	 * @return
	 */
	public  static String readTxtFile(String filePath) {
		String result = "";
		try {
			// String encoding="GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					result = result + lineTxt;
				}
				read.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ����ʱ�����ĵ÷�
	 * 
	 * �����ļ������2015-08-04 13��39:28
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
