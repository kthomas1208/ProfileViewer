package com.dreammist.profileviewer.db;

import io.realm.RealmObject;

/**
 * Created by kevinthomas on 3/16/17.
 */

public class Photo extends RealmObject {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
