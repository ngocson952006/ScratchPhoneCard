package com.example.truongngoc.scratchphonecard.domain;

/**
 * Created by Truong Ngoc Son on 6/11/2016.
 */
public enum TelecommunicationPortal {

    VIETTEL("VietTel"),
    MOBIFONE("Mobifone"),
    VINAPHONE("VinaPhone"),
    VIETNAMOBILE("VietnamMobile");

    private String name; // name of portal

    private TelecommunicationPortal(String name) {
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