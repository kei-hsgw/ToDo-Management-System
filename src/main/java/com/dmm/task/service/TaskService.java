package com.dmm.task.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	public MultiValueMap<LocalDate, Tasks> getTask(LocalDate from, LocalDate to, AccountUserDetails user) {
		
		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();
		
		Collection<? extends GrantedAuthority> list = user.getAuthorities();

		if (list.stream().anyMatch(l -> l.getAuthority().equals("ROLE_ADMIN"))) {
			List<Tasks> taskList = taskRepository.findAll();
			for (Tasks task : taskList) {
				tasks.add(task.getDate(), task);
			}
		} else {
			List<Tasks> taskList = taskRepository.findByDateBetween(from, to, user.getName());
			for (Tasks task : taskList) {
				tasks.add(task.getDate(), task);
			}
		}
		return tasks;
	}
}
