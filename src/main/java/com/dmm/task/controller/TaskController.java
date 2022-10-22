package com.dmm.task.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TaskRepository;
import com.dmm.task.form.EditForm;
import com.dmm.task.form.RegisterForm;
import com.dmm.task.service.AccountUserDetails;
import com.dmm.task.service.CalenderService;
import com.dmm.task.service.TaskService;

@Controller
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskService taskService;

	@Autowired
	private CalenderService calenderService;

	/**
	 * トップページ表示
	 * 
	 * @param model
	 * @param user
	 * @return
	 */
	@GetMapping("/main")
	public String main(Model model, @AuthenticationPrincipal AccountUserDetails user) {

		// カレンダーを表示させる処理
		List<List<LocalDate>> matrix = new ArrayList<List<LocalDate>>();
		// 当月の1日を取得
		LocalDate day = LocalDate.now().withDayOfMonth(1);
		model.addAttribute("month", day.getYear() + "年" + day.getMonthValue() + "月");
		
		// 前月分
		LocalDate prev = day.minusMonths(1);
		System.out.println(prev);
		model.addAttribute("prev", prev);
		
		// 来月分
		LocalDate next = day.plusMonths(1);
		System.out.println(next);
		model.addAttribute("next", next);

		matrix = calenderService.getCalender(day);
		model.addAttribute("matrix", matrix);

		// カレンダーにタスクを表示させる処理
		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();

		int lastDay = day.lengthOfMonth();

		LocalDate from = LocalDate.now().withDayOfMonth(1);
		LocalDate to = LocalDate.now().withDayOfMonth(lastDay);

		tasks = taskService.getTask(from, to, user);
		model.addAttribute("tasks", tasks);
		return "main";
	}

	@GetMapping("/main/changeMonth")
	public String chageMonth(@RequestParam("date") String date, Model model, @AuthenticationPrincipal AccountUserDetails user) {
		
		// カレンダーを表示させる処理
		List<List<LocalDate>> matrix = new ArrayList<List<LocalDate>>();
		// 当月の1日を取得
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate day = LocalDate.parse(date, dtf).withDayOfMonth(1);
		model.addAttribute("month", day.getYear() + "年" + day.getMonthValue() + "月");
		
		// 前月分
		LocalDate prev = day.minusMonths(1);
		System.out.println(prev);
		model.addAttribute("prev", prev);
		
		// 来月分
		LocalDate next = day.plusMonths(1);
		System.out.println(next);
		model.addAttribute("next", next);

		matrix = calenderService.getCalender(day);
		model.addAttribute("matrix", matrix);

		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();

		LocalDate day1 = LocalDate.parse(date, dtf).withDayOfMonth(1);
		int lastDay = day1.lengthOfMonth();

		LocalDate from = LocalDate.parse(date, dtf).withDayOfMonth(1);
		LocalDate to = LocalDate.parse(date, dtf).withDayOfMonth(lastDay);

		tasks = taskService.getTask(from, to, user);
		model.addAttribute("tasks", tasks);

		return "main";
	}

	/**
	 * 登録画面表示
	 * 
	 * @return
	 */
	@GetMapping("/main/create/{date}")
	public String create(@PathVariable String date, Model model) {
		model.addAttribute("date", LocalDate.parse(date));
		return "create";
	}

	/**
	 * 新規登録
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/main/delete/{id}")
	public String delete(@PathVariable Integer id) {
		taskRepository.deleteById(id);
		return "redirect:/main";
	}

}
