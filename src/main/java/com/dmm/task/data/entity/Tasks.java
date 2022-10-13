package com.dmm.task.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tasks {

	/** ID */
	@Id
	private Integer id;
	/** タイトル */
	private String title;
	/** 名前 */
	private String name;
	/** テキスト */
	private String text;
	/** 日にち */
	private LocalDate date;
	/** 完了フラグ */
	private boolean done = false; // 完了：true 未完了：false

	public Tasks() {
	}

	public Tasks(Integer id, String title, String name, String text, LocalDate date) {
		this.id = id;
		this.title = title;
		this.name = name;
		this.text = text;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "Tasks [id=" + id + ", title=" + title + ", name=" + name + ", text=" + text + ", date=" + date
				+ ", done=" + done + "]";
	}

}
