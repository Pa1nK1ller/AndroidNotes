package com.example.androidnotes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NotesData implements Parcelable {
    private String id;

    private final String title;

    private final String description;
    private final boolean like;
    private final Date date;

    public NotesData(String title, String description, boolean like, Date date) {
        this.title = title;
        this.description = description;
        this.like = like;
        this.date = date;
    }

    protected NotesData(Parcel in) {
        title = in.readString();
        description = in.readString();
        like = in.readByte() != 0;
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotesData> CREATOR = new Creator<NotesData>() {
        @Override
        public NotesData createFromParcel(Parcel in) {
            return new NotesData(in);
        }

        @Override
        public NotesData[] newArray(int size) {
            return new NotesData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLike() {
        return like;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}