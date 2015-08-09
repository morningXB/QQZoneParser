package com.qzoneparser.bean;

public class Reply {
	String content;
	String uin;
	String time;
	String nick;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUin() {
		return uin;
	}
	public void setUin(String uin) {
		this.uin = uin;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	@Override
	public String toString() {
		return "Reply [content=" + content + ", uin=" + uin + ", time=" + time
				+ ", nick=" + nick + "]";
	}
	
}
