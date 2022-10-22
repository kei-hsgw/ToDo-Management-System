package com.dmm.task.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalenderService {

	// 当月
	public List<List<LocalDate>> getCalender(LocalDate day) {
		
		// カレンダーを表示させる処理
		List<List<LocalDate>> matrix = new ArrayList<List<LocalDate>>();
		List<LocalDate> week = new ArrayList<LocalDate>();

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
		return matrix;
	}
}
