package com.example.core.model.tables;

import com.example.core.model.reserved.Reserved;
import com.google.gson.annotations.SerializedName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Table)) return false;
        Table that = (Table) o;
        return id == that.id &&
                status.equals(that.status);
    }
}
