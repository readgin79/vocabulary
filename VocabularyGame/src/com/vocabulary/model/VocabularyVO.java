package com.vocabulary.model;

public class VocabularyVO implements java.io.Serializable{
	
	private String voc_no;
	private String voc_name;
	private String voc_translate;
	private String voc_desc;
	private String voc_sentence;
	private byte[] voc_pic;
	private String cate_no;
	
	public String getVoc_no() {
		return voc_no;
	}
	public void setVoc_no(String voc_no) {
		this.voc_no = voc_no;
	}
	public String getVoc_name() {
		return voc_name;
	}
	public void setVoc_name(String voc_name) {
		this.voc_name = voc_name;
	}
	public String getVoc_translate() {
		return voc_translate;
	}
	public void setVoc_translate(String voc_translate) {
		this.voc_translate = voc_translate;
	}
	public String getVoc_desc() {
		return voc_desc;
	}
	public void setVoc_desc(String voc_desc) {
		this.voc_desc = voc_desc;
	}
	public String getVoc_sentence() {
		return voc_sentence;
	}
	public void setVoc_sentence(String voc_sentence) {
		this.voc_sentence = voc_sentence;
	}
	public byte[] getVoc_pic() {
		return voc_pic;
	}
	public void setVoc_pic(byte[] voc_pic) {
		this.voc_pic = voc_pic;
	}
	public String getCate_no() {
		return cate_no;
	}
	public void setCate_no(String cate_no) {
		this.cate_no = cate_no;
	}
}
