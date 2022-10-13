package com.dmm.task.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {

	/** ユーザー名 */
	@Id
	private String userName;
	/** パスワード */
	private String password;
	/** 名前 */
	private String name;
	/** ロール名 */
	private String roleName;

	public Users() {
	}

	public Users(String userName, String password, String name, String roleName) {
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.roleName = roleName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Users [userName=" + userName + ", name=" + name + ", roleName=" + roleName + "]";
	}

}
