package com.example.personalcounter;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;

public class DateTime {

    int second;
    int minute;
    int hour;
    int day;
    int month;
    int year;

    DateTime(LocalDateTime localDateTime){
        this.second = localDateTime.getSecond();
        this.minute = localDateTime.getMinute();
        this.hour = localDateTime.getHour();
        this.day = localDateTime.getDayOfMonth();
        this.month = localDateTime.getMonthValue();
        this.year = localDateTime.getYear();
    }

    DateTime(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @NonNull
    @Override
    public String toString() {
        return year + "-" + month + "-" + day + "--" + hour + ":" + minute + ":" + second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateTime dateTime = (DateTime) o;
        return second == dateTime.second && minute == dateTime.minute && hour == dateTime.hour && day == dateTime.day && month == dateTime.month && year == dateTime.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(second, minute, hour, day, month, year);
    }
}
