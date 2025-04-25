package com.example.orderitjava.ui.tables;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.model.tables.Table;
import com.example.orderitjava.R;

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
        if (newList == null) {
            Log.e("Adapter", "setTableList: Received NULL list");
        } else {
            Log.d("Adapter", "setTableList: Received list of size " + newList.size());
        }
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TableDiffCallback(this.tableList, newList));
        this.tableList = newList;
//        diffResult.dispatchUpdatesTo(this);
//        this.tableList = newList;
        notifyDataSetChanged();
        Log.d("Adapter", "Data set updated, total: " + getItemCount());
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
        Log.d("Adapter", "Binding table: " + table.getName());
        holder.tvTableName.setText(table.getName());
        Context context = holder.itemView.getContext();

        String status = table.getStatus();
        int color;

        switch (table.getStatus()) {
            case "Ελεύθερο":
                color = ContextCompat.getColor(context, R.color.table_free);
                break;
            case "Κατειλημμένο":
            case "Κατειλημμένο (με κράτηση)":
                color = ContextCompat.getColor(context, R.color.table_busy);
                break;
            case "Κρατημένο":
                color = ContextCompat.getColor(context, R.color.table_reserved);
                break;
            default:
                color = ContextCompat.getColor(context, R.color.table_unkown);
                break;
        }


//        if (status.equalsIgnoreCase("Ελεύθερο")) {
//            color = Color.parseColor("#4CAF50"); // Πράσινο
//        } else if (status.equalsIgnoreCase("Κατειλημμένο") ||
//                status.equalsIgnoreCase("Κατειλημμένο (με κράτηση)")) {
//            color = Color.parseColor("#F44336"); // Κόκκινο
//        } else if (status.equalsIgnoreCase("Κρατημένο")) {
//            color = Color.parseColor("#FF9800"); // Πορτοκαλί
//        } else {
//            color = Color.LTGRAY; // Άγνωστο status
//        }

        GradientDrawable background = (GradientDrawable) holder.layout.getBackground();
        background.setColor(color);
    }


    /**
     * Επιστρέφει τον συνολικό αριθμό στοιχείων στη λίστα
     */
    @Override
    public int getItemCount() {
//        return tableList != null ? tableList.size() : 0;
        int count = tableList != null ? tableList.size() : 0;
        Log.d("Adapter", "getItemCount = " + count);
        return count;
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
