package com.mariolamontagne.happy.model;

public class Reminder implements Comparable<Reminder> {
    
    private int mHour;
    private int mMinute;
    
    public Reminder(int hour, int minute) {
        mHour = hour;
        mMinute = minute;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }

    @Override
    public int compareTo(Reminder another) {
        if (another.getHour() > getHour()) {
            return -1;
        } else if (another.getHour() == getHour()) {
            if (another.getMinute() > getMinute()) {
                return -1;
            } else if (another.getMinute() == getMinute()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

}
