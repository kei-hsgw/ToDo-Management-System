package com.dmm.task.form;

public class EditForm {

	/** タイトル */
	private String title;
	/** 日にち */
	private String date;
	/** テキスト */
	private String text;
	/** 完了フラグ */
	private boolean done;

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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "EditForm [title=" + title + ", date=" + date + ", text=" + text + ", done=" + done + "]";
	}

}
