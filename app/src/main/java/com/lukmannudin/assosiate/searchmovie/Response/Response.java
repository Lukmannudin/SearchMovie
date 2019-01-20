package com.lukmannudin.assosiate.searchmovie.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response<T> {
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<T> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
