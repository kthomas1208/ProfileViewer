package com.dreammist.profileviewer.db;

import io.realm.RealmObject;

/**
 * Created by kevinthomas on 3/16/17.
 */

public class Post extends RealmObject {

    /**
     * The text of the status update
     */
    private String text;

    /**
     * The date the status update was posted
     */
    private String date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
