package com.smritivas.todoapp.yesplus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CurrentTasks extends Activity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewtasks);
		listView = (ListView) findViewById(R.id.lvTasks);
		ArrayList<Task> tasks = new ArrayList<Task>();
		DatabaseHelper db = new DatabaseHelper(this);
		final Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		tasks = db.getAllTasks();

		class Sort implements Comparator<Task> {

			@SuppressWarnings("deprecation")
			@Override
			public int compare(Task task1, Task task2) {
				// TODO Auto-generated method stub
				String day1 = task1.dueDate.split("-")[0];
				String month1 = task1.dueDate.split("-")[1];
				String year1 = task1.dueDate.split("-")[2];
				Date date1 = new Date();
				date1.setDate(Integer.parseInt(day1));
				date1.setMonth(Integer.parseInt(month1));
				date1.setYear(Integer.parseInt(year1));

				String day2 = task2.dueDate.split("-")[0];
				String month2 = task2.dueDate.split("-")[1];
				String year2 = task2.dueDate.split("-")[2];
				Date date2 = new Date();
				date2.setDate(Integer.parseInt(day2));
				date2.setMonth(Integer.parseInt(month2));
				date2.setYear(Integer.parseInt(year2));

				return date1.compareTo(date2);
			}

		}
		Collections.sort(tasks, new Sort());
		ArrayList<Task> viewList = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			int lday = Integer.parseInt(tasks.get(i).dueDate.split("-")[0]);
			int lmonth = Integer.parseInt(tasks.get(i).dueDate.split("-")[1]);
			int lyear = Integer.parseInt(tasks.get(i).dueDate.split("-")[2]);
			if (day == lday && (month + 1) == lmonth && year == lyear) {
				viewList.add(tasks.get(i));
			}
		}
		ListAdapter adapter = new com.smritivas.todoapp.yesplus.ListAdapter(
				this, viewList);
		listView.setAdapter(adapter);
	}

}
