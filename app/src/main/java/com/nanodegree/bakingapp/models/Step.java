package com.nanodegree.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Step implements Parcelable {
    @JsonProperty("videoURL")
    private String videoURL;
    @JsonProperty("description")
    private String description;
    @JsonProperty("id")
    private int id;
    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("thumbnailURL")
    private String thumbnailURL;

    public Step() {
        this.videoURL = "";
        this.description = "";
        this.id = 0;
        this.shortDescription = "";
        this.thumbnailURL = "";
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoURL);
        dest.writeString(this.description);
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.thumbnailURL);
    }

    protected Step(Parcel in) {
        this.videoURL = in.readString();
        this.description = in.readString();
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    // Getters
    public String getVideoURL() {
        return videoURL;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public String toString() {
        return "Step{" +
                "videoURL='" + videoURL + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}