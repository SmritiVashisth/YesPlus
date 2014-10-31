package com.smritivas.todoapp.yesplus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


public class Main extends Activity implements OnClickListener,
OnItemClickListener {

Button btnAdd;
ArrayList<Task> tasks;
ListView taskList;

@Override
protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
btnAdd = (Button) findViewById(R.id.bAdd);
btnAdd.setOnClickListener(this);
taskList = (ListView) findViewById(R.id.lvTasks);
tasks = new ArrayList<Task>();
DatabaseHelper dbHelper = new DatabaseHelper(this);
tasks = dbHelper.getAllTasks();

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
ListAdapter adapter = new com.smritivas.todoapp.yesplus.ListAdapter(this, tasks);
taskList.setAdapter(adapter);
taskList.setOnItemClickListener(this);

}

@Override
public void onClick(View v) {
// TODO Auto-generated method stub
switch (v.getId()) {
case R.id.bAdd:
	Intent intent = new Intent(Main.this, AddTask.class);
	finish();
	startActivity(intent);
	break;
}
}

@Override
public void onItemClick(AdapterView<?> arg0, View view, int position,
	long id) {
// TODO Auto-generated method stub
DailogSet((int) id);
}

private void DailogSet(final int id) {
// TODO Auto-generated method stub
final String actions[] = { "Modify", "Delete" };
AlertDialog.Builder ab = new AlertDialog.Builder(Main.this);
ab.setTitle("Delete");
ab.setItems(actions, new DialogInterface.OnClickListener() {

	public void onClick(DialogInterface d, int choice) {

		int id_delete = tasks.get(id).id;
		if (choice == 0) {
			Bundle b = new Bundle();
			Intent i = new Intent(Main.this, ModifyClass.class);
			b.putString("id", Integer.toString(id_delete));
			b.putString("edit", tasks.get(id).getTaskName());
			b.putString("date", tasks.get(id).getDate());
			b.putString("priority",
					Integer.toString(tasks.get(id).getPriority()));

			i.putExtras(b);
			finish();
			startActivity(i);
		} else if (choice == 1) {
			try {
				DatabaseHelper entry = new DatabaseHelper(Main.this);
				// set the parameters of the task
				entry.deleteTask(id_delete);
				finish();
				Intent intent = new Intent(Main.this, Main.class);
				startActivity(intent);
			} catch (Exception e) {

			}
		}

	}
});
ab.show();
}

// set a dialog to exit the application when back is pressed on the device

// dialog.setOnKeyListener(new Dialog.OnKeyListener() {
//
// @Override
// public boolean onKey(DialogInterface arg0, int keyCode,
// KeyEvent event) {
// // TODO Auto-generated method stub
// if (keyCode == KeyEvent.KEYCODE_BACK) {
// finish();
// dialog.dismiss();
// }
// return true;
// }
// });

// also, set the menu items/options

}
