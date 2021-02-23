package com.task.genderdetector.domain;

import java.util.List;

public class NameList {
    private List<String> availableNames;

    public NameList(List<String> availableNames) {
        this.availableNames = availableNames;
    }

    public List<String> getAvailableNames() {
        return availableNames;
    }

    public void setAvailableNames(List<String> availableNames) {
        this.availableNames = availableNames;
    }
}
