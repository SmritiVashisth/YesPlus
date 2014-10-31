package com.smritivas.todoapp.yesplus;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter implements OnClickListener {

	LayoutInflater inflater;
	ArrayList<Task> list;
	CheckBox chkBox;
	Context ctx;
	CheckBox chkDone;

	ListAdapter(Context context, ArrayList<Task> tasks) {
		ctx = context;
		list = tasks;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.customlist, parent, false);
		}
		Task task = getTask(position);
		((TextView) view.findViewById(R.id.tvTask)).setText("Task : "
				+ task.getTaskName());
		((TextView) view.findViewById(R.id.tvPriority)).setText("Priority : "
				+ task.getPriority());
		((TextView) view.findViewById(R.id.tvDate))
				.setText("Completion date : " + task.getDate());
		chkDone = (CheckBox) view.findViewById(R.id.checkDone);
		chkDone.setOnCheckedChangeListener(checkBoxListener);
		chkDone.setTag(position);
		chkDone.setChecked(task.done);
		return view;
	}

	public void SetDone() {
		chkDone.setChecked(true);
	}

	OnCheckedChangeListener checkBoxListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(final CompoundButton buttonView,
				boolean isChecked) {
			getTask((Integer) buttonView.getTag()).done = isChecked;

			Log.i("msg", "list generated");

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					ctx);

			alertDialogBuilder.setTitle("Delete");

			alertDialogBuilder
					.setMessage("Do you want to delete this task ?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									DatabaseHelper entry = new DatabaseHelper(
											ctx);
									Task task = new Task();
									task.getTaskName();
									task.getPriority();
									task.getDate();
									task.getId();
									entry.updateTask(task);
									((Activity) ctx).finish();
									Bundle b = new Bundle();
									b.putBoolean("done", true);
									Intent intent = new Intent(ctx, Main.class);
									intent.putExtras(b);
									ctx.startActivity(intent);

								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									getTask((Integer) buttonView.getTag()).done = false;
									((Activity) ctx).finish();
									Intent intent = new Intent(ctx, Main.class);
									ctx.startActivity(intent);
									dialog.cancel();
								}
							});

			AlertDialog alertDialog = alertDialogBuilder.create();

			alertDialog.show();
		}

	};

	private Task getTask(int position) {
		// TODO Auto-generated method stub
		return ((Task) getItem(position));
	}

	ArrayList<Task> getBool() {
		ArrayList<Task> bool = new ArrayList<Task>();
		for (Task task : list) {
			if (task.done)
				bool.add(task);
		}
		return bool;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
