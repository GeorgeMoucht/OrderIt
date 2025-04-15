package com.example.orderitjava.ui.tables;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderitjava.R;
import com.example.orderitjava.data.model.tables.Table;

import java.util.List;




/**
 * Adapter class για το RecyclerView που εμφανίζει τα τραπέζια του μαγαζιού.
 * Κάθε στοιχείο της λίστας είναι ένα Table αντικείμενο.
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    // Η λίστα με τα τραπέζια που θα εμφανιστεί στο RecyclerView
    private List<Table> tableList;

    // Constructor με αρχικοποίηση της λίστας
    public TableAdapter(List<Table> tableList) {
        this.tableList = tableList;
    }

    /**
     * Ενημερώνει τη λίστα των τραπεζιών με νέα δεδομένα και κάνει refresh το UI
     */
    public void setTableList(List<Table> newList) {
        this.tableList = newList;
        notifyDataSetChanged();
    }

    /**
     * Καλείται όταν το RecyclerView χρειάζεται ένα νέο ViewHolder για ένα στοιχείο της λίστας.
     */
    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_item, parent, false);
        return new TableViewHolder(view);
    }

    /**
     * Δέσμευση (binding) δεδομένων για κάθε στοιχείο του RecyclerView.
     */

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        Table table = tableList.get(position);
        holder.tvTableName.setText(table.getName());

        String status = table.getStatus();
        int color;

        if (status.equalsIgnoreCase("Ελεύθερο")) {
            color = Color.parseColor("#4CAF50"); // Πράσινο
        } else if (status.equalsIgnoreCase("Κατειλημμένο") ||
                status.equalsIgnoreCase("Κατειλημμένο (με κράτηση)")) {
            color = Color.parseColor("#F44336"); // Κόκκινο
        } else if (status.equalsIgnoreCase("Κρατημένο")) {
            color = Color.parseColor("#FF9800"); // Πορτοκαλί
        } else {
            color = Color.LTGRAY; // Άγνωστο status
        }

        GradientDrawable background = (GradientDrawable) holder.layout.getBackground();
        background.setColor(color);
    }


    /**
     * Επιστρέφει τον συνολικό αριθμό στοιχείων στη λίστα
     */
    @Override
    public int getItemCount() {
        return tableList != null ? tableList.size() : 0;
    }

    /**
     * ViewHolder class που κρατάει τα views για ένα table item.
     */
    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView tvTableName;
        View layout;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTableName = itemView.findViewById(R.id.tvTableName);
            layout = itemView.findViewById(R.id.tableItemLayout);
        }
    }
}
