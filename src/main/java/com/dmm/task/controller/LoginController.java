package com.dmm.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	/**
	 * ログイン画面表示
	 * @return
	 */
	@GetMapping("loginForm")
	public String loginForm() {
		return "login";
	}
}
