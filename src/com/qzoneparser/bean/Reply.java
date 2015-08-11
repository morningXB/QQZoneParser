package com.qzoneparser.bean;

public class Reply {
	String content;
	Long uin;
	String time;
	String nick;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getUin() {
		return uin;
	}
	public void setUin(Long uin) {
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
