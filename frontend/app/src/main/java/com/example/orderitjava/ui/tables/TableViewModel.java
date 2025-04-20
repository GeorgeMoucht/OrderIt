package com.example.orderitjava.ui.tables;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.orderitjava.data.model.tables.Table;
import com.example.orderitjava.data.repository.TableRepository;
import com.example.orderitjava.utils.Resource;

import java.util.List;

/**
 * ViewModel υπεύθυνο για την ανάκτηση και παροχή των δεδομένων των τραπεζιών στο UI.
 * <p>
 * Λειτουργεί ως "γέφυρα" ανάμεσα στο UI (π.χ. TableListActivity) και στο data layer (TableRepository).
 * Διασφαλίζει ότι τα δεδομένα επιβιώνουν από αλλαγές στο lifecycle, όπως για παράδειγμα την περιστροφή της οθόνης.
 */
public class TableViewModel extends AndroidViewModel {

    // Το repository που αναλαμβάνει την επικοινωνία με το API
    private final TableRepository repository;
    // LiveData που κρατάει την κατάσταση και τα δεδομένα των τραπεζιών (loading, success, error)
    private final MutableLiveData<Resource<List<Table>>> tables = new MutableLiveData<>();

    // Constructor που αρχικοποιεί το repository
    public TableViewModel(@NonNull Application application) {
        super(application);
        repository = new TableRepository();
    }

    // Παρέχει το LiveData ώστε το UI να μπορεί να κάνει observe για αλλαγές στα τραπέζια
    public LiveData<Resource<List<Table>>> getTables() {
        return tables;
    }

    /**
     * Ενεργοποιεί το κάλεσμα στο API για να φέρει τη λίστα τραπεζιών.
    * Όταν φτάσει η απάντηση, ενημερώνεται το LiveData ώστε να ενημερωθεί αυτόματα και το UI.
    */
    public void fetchTables() {
        // Κάνουμε observe στο αποτέλεσμα του repository και ενημερώνουμε το δικό μας LiveData
        repository.getTables().observeForever(resource -> tables.setValue(resource));
    }
}
