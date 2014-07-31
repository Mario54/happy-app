package com.mariolamontagne.happy.model;

import java.util.Calendar;
import java.util.Date;

public class Day {
	private Date mDate;

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public Day(Date date) {
		mDate = date;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Day) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(mDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(((Day) o).getDate());

			if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
