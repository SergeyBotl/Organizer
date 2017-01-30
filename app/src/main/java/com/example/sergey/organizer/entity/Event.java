package com.example.sergey.organizer.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Event implements Serializable, Parcelable {

    private long id;
    private String msgEvent;
    private long date;
    private long time;
    private boolean checkDone;

    public Event(long date, String msgEvent) {
        this.msgEvent = msgEvent;
        this.date = date;
    }

    public Event(String msgEvent, long date, long time) {
        this.msgEvent = msgEvent;
        this.date = date;
        this.time = time;
    }

   /* public Event(String msgEvent, long date, boolean checkDone) {
        this.msgEvent = msgEvent;
        this.date = date;
        this.checkDone = checkDone;
    }*/

    public Event(String msgEvent, long date, long time, boolean checkDone) {
        this.msgEvent = msgEvent;
        this.date = date;
        this.time = time;
        this.checkDone = checkDone;
    }

    public Event(long id, String msgEvent, long date, long time, boolean checkDone) {
        this.id = id;
        this.msgEvent = msgEvent;
        this.date = date;
        this.time = time;
        this.checkDone = checkDone;
    }

    protected Event(Parcel in) {
        id = in.readLong();
        msgEvent = in.readString();
        date = in.readLong();
        time = in.readLong();
        checkDone = in.readByte() != 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMsgEvent() {
        return msgEvent;
    }

    public void setMsgEvent(String msgEvent) {
        this.msgEvent = msgEvent;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isCheckDone() {
        return checkDone;
    }

    public void setCheckDone(boolean checkDone) {
        this.checkDone = checkDone;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (date != event.date) return false;
        return msgEvent != null ? msgEvent.equals(event.msgEvent) : event.msgEvent == null;

    }

    @Override
    public int hashCode() {
        int result = msgEvent != null ? msgEvent.hashCode() : 0;
        result = 31 * result + (int) (date ^ (date >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                ", msgEvent='" + msgEvent + '\'' +
                ", date=" + date +
                ", checkDone=" + checkDone +
                '}';
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(msgEvent);
        parcel.writeLong(date);
        parcel.writeLong(time);
        parcel.writeByte((byte) (checkDone ? 1 : 0));
    }
}
