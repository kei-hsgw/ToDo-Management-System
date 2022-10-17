package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TaskRepository;
import com.dmm.task.form.EditForm;
import com.dmm.task.form.RegisterForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class TaskController {
	
	@Autowired
	private TaskRepository taskRepository;

	@GetMapping("/main")
	public String main(Model model, @AuthenticationPrincipal AccountUserDetails user) {
		
		// カレンダーを表示させる処理
		List<List<LocalDate>> matrix = new ArrayList<List<LocalDate>>();
		List<LocalDate> week = new ArrayList<LocalDate>();
		
		// 当月の1日を取得
		LocalDate day = LocalDate.now().withDayOfMonth(1);
		model.addAttribute("month", day.getYear() + "年" + day.getMonthValue() + "月");
		// 曜日を取得
		DayOfWeek w = day.getDayOfWeek();
		// 前月分を取得
		day = day.minusDays(w.getValue());
		
		// 1週間分
		for (int i = 1; i <= 7; i++) {
			week.add(day);
			day = day.plusDays(1);
		}
		// 全体に追加
		matrix.add(week);
		// 次週分を作成
		week = new ArrayList<LocalDate>();
		
		// 当月の日数を取得
		int lastDay = day.lengthOfMonth();
		
		// 2週目以降
		for (int i = day.getDayOfMonth(); i < lastDay; i++) {
			week.add(day);
			w = day.getDayOfWeek();
			if (w == DayOfWeek.SATURDAY) {
				matrix.add(week);
				week = new ArrayList<LocalDate>();
			}
			day = day.plusDays(1);
		}
		
		// 翌月分を取得
		w = day.getDayOfWeek();
		int nextMonthDays = 7 - w.getValue();
		
		for (int i = 1; i <= nextMonthDays; i++) {
			week.add(day);
			w = day.getDayOfWeek();
			if (w == DayOfWeek.SATURDAY) {
				matrix.add(week);
				break;
			}
			day = day.plusDays(1);
		}
		model.addAttribute("matrix", matrix);
		
		// カレンダーにタスクを表示させる処理
		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();
		
		LocalDate from = matrix.get(0).get(0);
		LocalDate to = matrix.get(5).get(6);
		
		List<Tasks> taskList = taskRepository.findByDateBetween(from, to, user.getName());
		for (Tasks task : taskList) {
			tasks.add(task.getDate(), task);
		}
		model.addAttribute("tasks", tasks);
		return "main";
	}
	
	/**
	 * 登録画面表示
	 * @return
	 */
	@GetMapping("/main/create/{date}")
	public String create() {
		return "create";
	}
	
	/**
	 * 新規登録
	 * @param registerForm
	 * @param user
	 * @return
	 */
	@PostMapping("/main/create")
	public String registerTask(RegisterForm registerForm, @AuthenticationPrincipal AccountUserDetails user) {
		Tasks task = new Tasks();
		BeanUtils.copyProperties(registerForm, task);
		task.setDate(LocalDate.parse(registerForm.getDate()));
		task.setName(user.getName());
		taskRepository.save(task);
		return "redirect:/main";
	}
	
	/**
	 * 編集・削除画面表示
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/main/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Optional<Tasks> taskOpt = taskRepository.findById(id);
		Tasks task = taskOpt.get();
		model.addAttribute("task", task);
		return "edit";
	}
	
	/**
	 * 編集
	 * @param id
	 * @param editForm
	 * @param user
	 * @return
	 */
	@PostMapping("/main/edit/{id}")
	public String editTask(@PathVariable Integer id, EditForm editForm, @AuthenticationPrincipal AccountUserDetails user) {
		Optional<Tasks> taskOpt = taskRepository.findById(id);
		Tasks task = taskOpt.get();
		BeanUtils.copyProperties(editForm, task);
		task.setDate(LocalDate.parse(editForm.getDate()));
		task.setName(user.getName());
		taskRepository.save(task);
		return "redirect:/main";
	}
	
	/**
	 * 削除
	 * @param id
	 * @return
	 */
	@PostMapping("/main/delete/{id}")
	public String delete(@PathVariable Integer id) {
		taskRepository.deleteById(id);
		return "redirect:/main";
	}
	
}
