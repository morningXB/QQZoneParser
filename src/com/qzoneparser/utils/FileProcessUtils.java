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
	 * TYPE ���� һ������ ͬѧ ��Ů���� İ���� ����һ���Ĺ��򣬼���ĳ���˵��������ԣ����Զ���Ĺؼ��ʶԱȣ���һ���ı����������յ����ܶ�
	 * 
	 * @param messages
	 *            ��������
	 * @return ���ܵ÷�
	 */
	public double firstAnalysis(ArrayList<Message> messages, Long uin) {
		ArrayList<Message> messagesfromone = getAllMessagesByUin(messages, uin);
		// ��һ����Ϊ���Ե�ռ�ٷֱ��� percentage
		// 1-10��
		double percentage = messagesfromone.size() / getAllCount(messages);
		double score1 = percentage * 10;

		// �ڶ���Ϊ���������뵱ǰʱ��ıȽ�
		// 1-10
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String replytime = messagesfromone.get(0).getPubtime();// ���µ�һ������
		Date current = null;
		try {
			current = format.parse(replytime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double score2 = timeinternal(current);

		// ������Ϊ�����������������ԵıȽ�
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
		
		//������Ϊ�ظ�����ռ��
		
		double score4 = beReplied(messagesfromone);
		
		return 0;
	}
	/**
	 * ���ݻظ�������ռ�ȼ������
	 * @param messagesfromone
	 * @return
	 */
	public double beReplied(ArrayList<Message> messagesfromone) {
		
		return 0;
	}

	/**
	 * �������º����ϵ����Լ���������
	 * 
	 * @param latest_time
	 * @param oldest_time
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public double maxinternal(Date latest_time, Date oldest_time) {
		int days = 0;
		if (latest_time.compareTo(oldest_time) == 0) {// ��һ������
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
	 * ��ȡtxt�ļ�
	 * 
	 * @param filePath
	 * @return
	 */
	public String readTxtFile(String filePath) {
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
