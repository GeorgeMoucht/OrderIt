package com.example.core.model.tables;

import java.util.List;

public class TableResponse {
    private boolean success;
    private List<Table> data;

    public boolean isSuccess() {
        return success;
    }

    public List<Table> getData() {
        return data;
    }
}
