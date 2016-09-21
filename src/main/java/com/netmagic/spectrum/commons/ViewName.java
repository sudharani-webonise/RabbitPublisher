package com.netmagic.spectrum.commons;

public enum ViewName {

    HOME("home"), SENT("sent"), ERROR("error"), WARNING("warning");

    private String viewName;

    private ViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

}
