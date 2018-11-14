package com.tymurd.finder.models;

public class SimilarElement {

    private String path;
    private Double similarityPercent;

    public SimilarElement(String path, Double similarityPercent) {
        this.path = path;
        this.similarityPercent = similarityPercent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getSimilarityPercent() {
        return similarityPercent;
    }

    public void setSimilarityPercent(Double similarityPercent) {
        this.similarityPercent = similarityPercent;
    }

    @Override
    public String toString() {
        return String.format("path = %s, similarity percent = %.2f%%", path, similarityPercent);
    }
}