package com.YHJstyle.b005.j_style_Pro.b005.task;

import com.YHJstyle.b005.j_style_Pro.b005.tool.ResolveData;

import android.content.Context;

public class WriteOneDataTask implements ITask {
	private byte type;
	private Context context;

	public WriteOneDataTask() {
	}

	public WriteOneDataTask(byte bype) {
		this.type = bype;
	}

	public WriteOneDataTask(byte type, Context context) {
		this.type = type;
		this.context = context;
	}

	@Override
	public void task() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ResolveData.syncDataThread(type);
	}

	public byte getType() {
		return type;
	}

}
