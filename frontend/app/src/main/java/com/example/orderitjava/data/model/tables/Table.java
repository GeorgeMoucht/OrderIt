package com.example.orderitjava.data.model.tables;

import com.google.gson.annotations.SerializedName;
import com.example.orderitjava.data.model.reserved.Reserved;

public class Table {
    private int id;
    private String name;
    private int state;

    @SerializedName("reserved")
    private Reserved reserved;

    @SerializedName("status")
    private String status;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public Reserved getReserved() {
        return reserved;
    }

    public String getStatus() {
        return status;
    }
}
