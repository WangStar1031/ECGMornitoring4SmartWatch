package com.YHJstyle.b005.j_style_Pro.b005.task;

import android.util.Log;

public class BackgroundThread extends Thread {
	private static final String LOG_TAG = BackgroundThread.class.getSimpleName();
	private Queue<ITask> queue;
	int index;
	public BackgroundThread(String threadName, Queue<ITask> queue) {
		super(threadName);
		this.queue = queue;
	}
	
	public void run() {
		while (true) {
			if (!Thread.interrupted()) {
				ITask task = (ITask) this.queue.get();
				if (task != null) {
					Log.d(LOG_TAG, getName() + ": task.task()");
					task.task();
				}
				Log.w(LOG_TAG, getName() + ": task is null.");
			}
		}
	}
}