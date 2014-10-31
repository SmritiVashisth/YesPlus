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

public class ModifyClass extends Activity implements OnCheckedChangeListener,
		OnClickListener {

	Button addM, datepickM;
	boolean urgentM = false;
	boolean importantM = false;
	int keyM; // priority
	int yearM, monthM, dayM;
	EditText etTaskM;
	CheckBox chkUrgentM, chkImportantM;
	String idM, editM, dateM, priorityM, str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtask);
		Bundle gotBasket = getIntent().getExtras();
		idM = gotBasket.getString("id");
		editM = gotBasket.getString("edit");
		dateM = gotBasket.getString("date");
		priorityM = gotBasket.getString("priority");
		final Calendar calendar = Calendar.getInstance();
		yearM = calendar.get(Calendar.YEAR);
		monthM = calendar.get(Calendar.MONTH);
		dayM = calendar.get(Calendar.DAY_OF_MONTH);
		str = Integer.toString(dayM) + "-" + Integer.toString(monthM + 1) + "-"
				+ Integer.toString(yearM);
		addM = (Button) findViewById(R.id.bAddTask);
		datepickM = (Button) findViewById(R.id.bDate);
		etTaskM = (EditText) findViewById(R.id.etTask);
		chkUrgentM = (CheckBox) findViewById(R.id.checkUrg);
		chkImportantM = (CheckBox) findViewById(R.id.checkImp);
		etTaskM.setText(editM);
		chkUrgentM.setOnCheckedChangeListener(this);
		chkImportantM.setOnCheckedChangeListener(this);
		addM.setOnClickListener(this);
		datepickM.setOnClickListener(this);

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.checkUrg:
			if (isChecked) {
				urgentM = true;
			} else {
				urgentM = false;
			}
			break;
		case R.id.checkImp:
			if (isChecked) {
				importantM = true;
			} else {
				importantM = false;
			}
			break;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		DatabaseHelper db = new DatabaseHelper(this);
		Task task = new Task();

		switch (view.getId()) {
		case R.id.bAddTask:
			task.setTaskName(etTaskM.getText().toString());
			Intent intent = new Intent(ModifyClass.this, Main.class);
			startActivity(intent);
			break;
		case R.id.bDate:
			showDialog(999);
			task.setDueDate(dateM);
			break;
		}
		// set priority
		keyM = (urgentM ? 1 : 0) * 2 + (importantM ? 1 : 0);
		task.setPriority(keyM);
		db.addTask(task);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 999:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, yearM,
					monthM, dayM);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			yearM = selectedYear;
			monthM = selectedMonth;
			dayM = selectedDay;
			// set the date variable/string
			dateM = monthM + 1 + " - " + dayM + " - " + yearM;

			// set selected date into textview
			// tvDate.setText(new StringBuilder().append(month + 1).append("-")
			// .append(day).append("-").append(year).append(" "));

		}
	};

}
