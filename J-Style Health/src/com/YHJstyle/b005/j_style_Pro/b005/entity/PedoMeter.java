package com.YHJstyle.b005.j_style_Pro.b005.entity;

import java.io.Serializable;

public class PedoMeter implements Serializable {

	public static final String ACCOMPLISHGOAL = "accpmlish_goal";// 累加的目标率
	public static final String ACTIVITY_TIME = "activity_time";
	public static final String AGE = "age";
	public static final String CALORIES = "calories";
	public static final String DAY = "pedometer_day";
	public static final String DEVICEID = "device_id";
	public static final String DISTANCE = "distance";
	public static final String GOALD = "goald";
	public static final String HEIGHT = "height";
	public static final String MONTH = "pedometer_month";
	public static final String PEDOMETER = "pedometer";
	public static final String PEDOMETERID = "_id";
	public static final String RUNSTEPS = "run_steps";
	public static final String SEX = "sex";
	public static final int[] SLEPT = new int[96];
	public static final String SLEPT_TIME = "slept_time";
	public static final String STEPLENGTH = "step_length";
	public static final String STEPS = "steps";
	public static final String WEEK = "week";
	public static final String WEIGHT = "weight";
	public static final String YEAR = "pedometer_year";
	public static final String YEAR_MONTH_DAY = "year_month_day";
	private static final long serialVersionUID = 1L;
	public int activityTtime;
	public int calories;
	public int dailyGoal;
	public int day;
	public int distance;
	public int fuel;
	public String id;
	public int month;
	public String pedometer;
	public int sleepTime;// 睡眠时间
	public int[] slept;
	public int steps;
	public int week;
	public int year;
	
	
	
	public int getActivityTtime() {
		return activityTtime;
	}

	public void setActivityTtime(int activityTtime) {
		this.activityTtime = activityTtime;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getDailyGoal() {
		return dailyGoal;
	}

	public void setDailyGoal(int dailyGoal) {
		this.dailyGoal = dailyGoal;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getPedometer() {
		return pedometer;
	}

	public void setPedometer(String pedometer) {
		this.pedometer = pedometer;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public int[] getSlept() {
		return slept;
	}

	public void setSlept(int[] slept) {
		this.slept = slept;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
