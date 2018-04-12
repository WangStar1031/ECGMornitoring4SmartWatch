package com.YHJstyle.b005.j_style_Pro.b005.task;

public class NewAgreementBackgroundThreadManager {
	private static final int THREAD_COUNTS =1;
	private static NewAgreementBackgroundThreadManager instance;
	private int mIndex = 0;
	private Queue<ITask> queue;
	private BackgroundThread[] threadArray;
	private NewAgreementBackgroundThreadManager() {
		queue = new Queue<ITask>();
		threadArray = new BackgroundThread[THREAD_COUNTS];
		for (int i = 0; i < THREAD_COUNTS; i++) {
			mIndex++;
			threadArray[i] = new BackgroundThread("Thread - " + mIndex, queue);
			threadArray[i].start();
		}
	}

	public synchronized static NewAgreementBackgroundThreadManager getInstance() {
		if (instance == null) {
			instance = new NewAgreementBackgroundThreadManager();
		}
		return instance;
	}
	
	public void addTask(ITask task) {
		queue.addTail(task);
	}
	
	public void removeTask(){
		queue.remove();
	}
	
	public void clearQueue(){
		queue.clear();
	}
	
}
