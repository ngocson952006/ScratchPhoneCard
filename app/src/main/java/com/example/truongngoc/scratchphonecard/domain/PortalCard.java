package com.example.truongngoc.scratchphonecard.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Truong Ngoc Son on 6/4/2016.
 */
public class PortalCard implements Parcelable {
    public static final String TAG = PortalCard.class.getSimpleName(); // tag for logging

    private final float price; // cause the maximum of price for a card is 500.000 , float is fit
    private final char[] serialNumber; //
    private final char[] code;
    //private Bitmap photo;

    // convenient constructor
    public PortalCard(float price, char[] serialNumber, char[] code, Bitmap photo) {
        this.price = price;
        this.serialNumber = serialNumber;
        this.code = code;
        // this.photo = photo;
    }


    protected PortalCard(Parcel in) {
        price = in.readFloat();
        serialNumber = in.createCharArray();
        code = in.createCharArray();
    }

    public static final Creator<PortalCard> CREATOR = new Creator<PortalCard>() {
        @Override
        public PortalCard createFromParcel(Parcel in) {
            return new PortalCard(in);
        }

        @Override
        public PortalCard[] newArray(int size) {
            return new PortalCard[size];
        }
    };

    public float getPrice() {
        return price;
    }

//    public void setPrice(float price) {
//        this.price = price;
//    }

    public char[] getSerialNumber() {
        return serialNumber;
    }

//    public void setSerialNumber(char[] serialNumber) {
//        this.serialNumber = serialNumber;
//    }

    public char[] getCode() {
        return code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(price);
        dest.writeCharArray(serialNumber);
        dest.writeCharArray(code);
    }
}
