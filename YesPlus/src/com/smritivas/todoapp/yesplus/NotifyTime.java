package com.smritivas.todoapp.yesplus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotifyTime extends BroadcastReceiver {

	NotificationManager nm;

	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public void onReceive(Context context, Intent intent) {

		Intent myIntent = new Intent(context, CurrentTasks.class);
		nm = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		CharSequence from = "Tasks Today";

		CharSequence message = "Your Today's Tasks are .....";
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				myIntent, 0);
		Notification notif = new Notification(R.drawable.ic_launcher,
				"Task Alert!!", System.currentTimeMillis());
		notif.flags = Notification.FLAG_INSISTENT;
		notif.setLatestEventInfo(context, from, message, contentIntent);
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		nm.notify(1, notif);

	}

}
