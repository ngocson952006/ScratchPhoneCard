package com.example.truongngoc.scratchphonecard.domain;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Truong Ngoc Son on 6/4/2016.
 */
public class ScratchCard implements Serializable {
    public static final String TAG = ScratchCard.class.getSimpleName(); // tag for logging

    private float price; // cause the maximum of price for a card is 500.000 , float is fit
    private char[] serialNumber; //
    private char[] code;
    private Bitmap photo;

    // convenient constructor
    public ScratchCard(float price, char[] serialNumber, char[] code, Bitmap photo) {
        this.price = price;
        this.serialNumber = serialNumber;
        this.code = code;
        this.photo = photo;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public char[] getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(char[] serialNumber) {
        this.serialNumber = serialNumber;
    }

    public char[] getCode() {
        return code;
    }

    public void setCode(char[] code) {
        this.code = code;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScratchCard)) return false;

        ScratchCard that = (ScratchCard) o;

        if (Float.compare(that.getPrice(), getPrice()) != 0) return false;
        if (!Arrays.equals(getSerialNumber(), that.getSerialNumber())) return false;
        if (!Arrays.equals(getCode(), that.getCode())) return false;
        return getPhoto().equals(that.getPhoto());

    }

    @Override
    public int hashCode() {
        int result = (getPrice() != +0.0f ? Float.floatToIntBits(getPrice()) : 0);
        result = 31 * result + Arrays.hashCode(getSerialNumber());
        result = 31 * result + Arrays.hashCode(getCode());
        result = 31 * result + getPhoto().hashCode();
        return result;
    }
}
