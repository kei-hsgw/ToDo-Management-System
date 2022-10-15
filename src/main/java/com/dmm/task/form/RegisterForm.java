package com.dmm.task.form;

public class RegisterForm {

	/** タイトル */
	private String title;
	/** 日にち */
	private String date;
	/** テキスト */
	private String text;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "RegisterForm [title=" + title + ", date=" + date + ", text=" + text + "]";
	}

}
