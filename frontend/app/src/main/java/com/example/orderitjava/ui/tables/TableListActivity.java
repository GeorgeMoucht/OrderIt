package com.example.orderitjava.ui.tables;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderitjava.R;
import com.example.orderitjava.utils.Resource;

/**
 * Activity υπεύθυνη για την εμφάνιση της λίστας των τραπεζιών.
 * Χρησιμοποιεί RecyclerView σε μορφή πλέγματος (grid) με 2 στήλες,
 * και ViewModel για την ανάκτηση των δεδομένων μέσω Retrofit API call.
 */
public class TableListActivity extends AppCompatActivity {

    private RecyclerView recyclerTables;
    private TableAdapter adapter;
    private TableViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);

        // Σύνδεση του RecyclerView από το layout
        recyclerTables = findViewById(R.id.recyclerTables);
        // Χρήση GridLayoutManager για εμφάνιση των τραπεζιών σε 2 στήλες
        recyclerTables.setLayoutManager(new GridLayoutManager(this, 2)); // Grid με 2 στήλες

        // Δημιουργία adapter με αρχικά κενή λίστα
        adapter = new TableAdapter(null);
        recyclerTables.setAdapter(adapter);

        // Δημιουργία του ViewModel (μέσω ViewModelProvider για lifecycle awareness)
        viewModel = new ViewModelProvider(this).get(TableViewModel.class);

        observeTables();    // Παρακολούθηση αλλαγών των δεδομένων
        viewModel.fetchTables();    // Εκκίνηση API call για λήψη τραπεζιών
    }

    /**
     * Συνδέει το LiveData των τραπεζιών με το UI.
     * Ανάλογα με το status της απάντησης (loading, success, error),
     * ενημερώνει τον adapter ή εμφανίζει μήνυμα.
     */
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
