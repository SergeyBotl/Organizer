package com.example.sergey.organizer.entity;

import android.support.v4.app.Fragment;

import java.util.Map;

public class Config{

    private int id;
    private int title;
    private String remark;
    private Fragment fragment;
    private boolean itemHome;
    private Map<Integer, String> paramsMenu;
    private int actionHomeItem;

    public Config(int id, String remark,int title, Fragment fragment, boolean itemHome, Map<Integer, String> paramsMenu,int actionHomeItem) {
        this.id = id;
        this.title = title;
        this.remark = remark;
        this.fragment = fragment;
        this.itemHome = itemHome;
        this.paramsMenu = paramsMenu;
        this.actionHomeItem = actionHomeItem;
    }

    public int getId() {
        return id;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isItemHome() {
        return itemHome;
    }

    public Map<Integer, String> getParamsMenu() {
        return paramsMenu;
    }

    public int getActionHomeItem() {
        return actionHomeItem;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

}
