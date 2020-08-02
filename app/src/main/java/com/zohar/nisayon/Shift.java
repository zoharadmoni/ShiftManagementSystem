package com.zohar.nisayon;

public class Shift {
    String start;
    String end;
    String count;

    public Shift() {

    }

    public Shift(String start, String end, String count) {
        this.start = start;
        this.end = end;
        this.count = count;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
