package com.example.orderitjava.ui.tables;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderitjava.R;
import com.example.orderitjava.utils.Resource;

public class TableListActivity extends AppCompatActivity {

    private RecyclerView recyclerTables;
    private TableAdapter adapter;
    private TableViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);

        recyclerTables = findViewById(R.id.recyclerTables);
        recyclerTables.setLayoutManager(new GridLayoutManager(this, 2)); // Grid με 2 στήλες

        adapter = new TableAdapter(null); // ξεκινάμε με κενή λίστα
        recyclerTables.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(TableViewModel.class);

        observeTables();
        viewModel.fetchTables(); // κάνει το API call
    }

    private void observeTables() {
        viewModel.getTables().observe(this, resource -> {
            if (resource.status == Resource.Status.LOADING) {
                // Optional: show loading spinner
            } else if (resource.status == Resource.Status.SUCCESS) {
                adapter.setTableList(resource.data); // δίνουμε τα τραπέζια στον adapter
            } else if (resource.status == Resource.Status.ERROR) {
                Toast.makeText(this, "Σφάλμα: " + resource.message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
