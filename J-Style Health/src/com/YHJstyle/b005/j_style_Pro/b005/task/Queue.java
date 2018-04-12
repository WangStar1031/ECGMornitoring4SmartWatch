package com.YHJstyle.b005.j_style_Pro.b005.task;

import java.util.LinkedList;
import java.util.List;

public class Queue<E extends Object> {
	private Object mObject = new Object();
	private final LinkedList<E> mQueue;

	public Queue() {
		mQueue = new LinkedList<E>();
	}

	/**
	 * 增加�?
	 * 
	 * @param object
	 */
	public void addHead(E object) {
		synchronized (mQueue) {
			mQueue.add(0, object);
		}
		synchronized (mObject) {
			mObject.notifyAll();
		}
	}

	/**
	 * 增加�?
	 * 
	 * @param object
	 */
	public void addTail(E object) {
		synchronized (mQueue) {
			mQueue.add(object);
		}
		synchronized (mObject) {
			mObject.notifyAll();
		}
	}

	public void addAllHead(List<E> list) {
		synchronized (mQueue) {
			mQueue.addAll(0, list);
		}
		synchronized (mObject) {
			mObject.notifyAll();
		}
	}

	public void addAllTail(List<E> list) {
		synchronized (mQueue) {
			mQueue.addAll(list);
		}
		synchronized (mObject) {
			mObject.notifyAll();
		}
	}

	public E get() {
		if (mQueue.size() == 0) {
			try {
				synchronized (mObject) {
					mObject.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		}
		synchronized (mQueue) {
			if (mQueue.size() > 0) {
				return mQueue.removeFirst();
			} else {
				return null;
			}
		}
	}
	
	public E getNew() {
		if (mQueue.size() == 0) {
			try {
				synchronized (mObject) {
					mObject.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		}
		synchronized (mQueue) {
			if (mQueue.size() > 0) {
				return mQueue.getFirst();
			} else {
				return null;
			}
		}
	}

	public void remove() {
		if (mQueue.size() != 0) {
			synchronized (mQueue) {
				if (mQueue.size() > 0) {
					mQueue.removeFirst();
				} else {

				}
			}
		}
	}

	public void clear() {
		synchronized (mQueue) {
			mQueue.clear();
		}
	}
}