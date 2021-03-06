package com.qzoneparser.bean;

import java.util.ArrayList;
import java.util.Date;

public class Message {
	
	private String id;
	private int secret;
	private String pasterid;
	private String bmp;
	private String pubtime;
	private int modifytime;
	private long effect;
	private int type;
	private long uin;
	private String nickname;
	private int capacity;
	private String htmlContent;
	private String ubbContent;
	private String signature;
	private ArrayList<Reply> replyList=new ArrayList<Reply>();
	@Override
	public String toString() {
		return "Message [id=" + id + ", secret=" + secret + ", pasterid="
				+ pasterid + ", bmp=" + bmp + ", pubtime=" + pubtime
				+ ", modifytime=" + modifytime + ", effect=" + effect
				+ ", type=" + type + ", uin=" + uin + ", nickname=" + nickname
				+ ", capacity=" + capacity + ", htmlContent=" + htmlContent
				+ ", ubbContent=" + ubbContent + ", signatrue=" + signature
				+ ", replyList=" + replyList + "]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSecret() {
		return secret;
	}
	public void setSecret(int secret) {
		this.secret = secret;
	}
	public String getPasterid() {
		return pasterid;
	}
	public void setPasterid(String pasterid) {
		this.pasterid = pasterid;
	}
	public String getBmp() {
		return bmp;
	}
	public void setBmp(String bmp) {
		this.bmp = bmp;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	public int getModifytime() {
		return modifytime;
	}
	public void setModifytime(int modifytime) {
		this.modifytime = modifytime;
	}
	public long getEffect() {
		return effect;
	}
	public void setEffect(long effect) {
		this.effect = effect;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getUin() {
		return uin;
	}
	public void setUin(long uin) {
		this.uin = uin;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getUbbContent() {
		return ubbContent;
	}
	public void setUbbContent(String ubbContent) {
		this.ubbContent = ubbContent;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public ArrayList<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(ArrayList<Reply> replyList) {
		this.replyList = replyList;
	}
}
