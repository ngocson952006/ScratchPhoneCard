package com.example.truongngoc.scratchphonecard.domain;

/**
 * Created by Truong Ngoc Son on 6/11/2016.
 */
public enum TelecommuncationPortal {

    VIETTEL("VietTel"),
    MOBIFONE("Mobifone"),
    VINAPHONE("VinaPhone");

    private String name; // name of portal

    private TelecommuncationPortal(String name) {
        this.name = name;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}