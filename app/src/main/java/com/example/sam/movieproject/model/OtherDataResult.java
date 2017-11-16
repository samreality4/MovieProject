package com.example.sam.movieproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sam on 10/12/17.
 */

public class OtherDataResult {

    OtherData otherData;

    private String id;

    private List<OtherData> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OtherData> getResults() {
        return results;
    }

    public void setResults(List<OtherData> results) {
        this.results = results;
    }
}

