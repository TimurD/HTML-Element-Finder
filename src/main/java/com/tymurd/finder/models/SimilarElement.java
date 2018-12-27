package com.tymurd.finder.models;

import java.util.Objects;

public final class SimilarElement {

    private final String path;
    private final Double similarityPercent;

    public SimilarElement(String path, Double similarityPercent) {
        this.path = path;
        this.similarityPercent = similarityPercent;
    }

    public String getPath() {
        return path;
    }

    public Double getSimilarityPercent() {
        return similarityPercent;
    }

    @Override
    public String toString() {
        return String.format("path = %s, similarity percent = %.2f%%", path, similarityPercent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimilarElement that = (SimilarElement) o;
        return Objects.equals(path, that.path) &&
                Objects.equals(similarityPercent, that.similarityPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, similarityPercent);
    }
}