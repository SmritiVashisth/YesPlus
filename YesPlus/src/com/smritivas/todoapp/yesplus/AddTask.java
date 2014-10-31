package com.smritivas.todoapp.yesplus;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddTask extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	Button add, datepick;
	boolean urgent = false;
	boolean important = false;
	int key; // priority
	int year, month, day;
	EditText etTask;
	String date;
	CheckBox chkUrgent, chkImportant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtask);
		final Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		add = (Button) findViewById(R.id.bAddTask);
		datepick = (Button) findViewById(R.id.bDate);
		etTask = (EditText) findViewById(R.id.etTask);
		chkUrgent = (CheckBox) findViewById(R.id.checkUrg);
		chkImportant = (CheckBox) findViewById(R.id.checkImp);
		chkUrgent.setOnCheckedChangeListener(this);
		chkImportant.setOnCheckedChangeListener(this);
		add.setOnClickListener(this);
		datepick.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// set entries for the database table
		DatabaseHelper db = new DatabaseHelper(this);
		Task task = new Task();

		switch (v.getId()) {
		case R.id.bAddTask:
			task.setTaskName(etTask.getText().toString());
			Intent intent = new Intent(AddTask.this, Main.class);
			finish();
			startActivity(intent);
			break;
		case R.id.bDate:
			showDialog(999);
			task.setDueDate(date);
			break;
		}
		// set priority
		key = (urgent ? 1 : 0) * 2 + (important ? 1 : 0);
		task.setPriority(key);
		db.addTask(task);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 999:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			// set the date variable/string
			date = month + 1 + " - " + day + " - " + year;

			// set selected date into textview
			// tvDate.setText(new StringBuilder().append(month + 1).append("-")
			// .append(day).append("-").append(year).append(" "));

		}
	};

	@Override
	public void onCheckedChanged(CompoundButton chkBox, boolean isChecked) {
		// TODO Auto-generated method stub

		switch (chkBox.getId()) {
		case R.id.checkUrg:
			if (isChecked) {
				urgent = true;
			} else {
				urgent = false;
			}
			break;
		case R.id.checkImp:
			if (isChecked) {
				important = true;
			} else {
				important = false;
			}
			break;
		}

	}

}
