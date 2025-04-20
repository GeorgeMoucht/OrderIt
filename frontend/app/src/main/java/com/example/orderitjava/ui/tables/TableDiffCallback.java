package com.example.orderitjava.ui.tables;

import androidx.recyclerview.widget.DiffUtil;

import com.example.orderitjava.data.model.tables.Table;

import java.util.List;

public class TableDiffCallback extends DiffUtil.Callback {

    private final List<Table> oldList;
    private final List<Table> newList;

    public TableDiffCallback(List<Table> oldList, List<Table> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Table oldTable = oldList.get(oldItemPosition);
        Table newTable = newList.get(newItemPosition);
        return oldTable.equals(newTable); // You can override equals in Table model if needed
    }
}
