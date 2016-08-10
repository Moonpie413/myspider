package org.wxh.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Maroon on 2016/8/10.
 * project: MySpider
 */
public class CrawUrl implements Serializable {
	
    public CrawUrl() {
		super();
	}
    
	private String oriUrl; //原始url值
    private String url; //url值
    private int urlNo; //urlNo
    private int statusCode; //状态吗
    private int hitNum; //被其他页面引用的次数
    private String charSet; //页面编码
    private String abstractString; //文章摘要
    private String author; //文章作者
    private int weight; //文章权重
    private String des; //文章描述
    private int fileSize; //文章大小
    private Timestamp lastUpdate;
    private Date timeToLive; //过期时间
    private String title; //文章名称
    private String type; //文章类型
    private String[] urlRefrences; //引用的链接
    private int layer; //爬取层次
    
	public String getOriUrl() {
		return oriUrl;
	}
	public void setOriUrl(String oriUrl) {
		this.oriUrl = oriUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getUrlNo() {
		return urlNo;
	}
	public void setUrlNo(int urlNo) {
		this.urlNo = urlNo;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public int getHitNum() {
		return hitNum;
	}
	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}
	public String getCharSet() {
		return charSet;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	public String getAbstractString() {
		return abstractString;
	}
	public void setAbstractString(String abstractString) {
		this.abstractString = abstractString;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Date getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(Date timeToLive) {
		this.timeToLive = timeToLive;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getUrlRefrences() {
		return urlRefrences;
	}
	public void setUrlRefrences(String[] urlRefrences) {
		this.urlRefrences = urlRefrences;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
    
    
}
