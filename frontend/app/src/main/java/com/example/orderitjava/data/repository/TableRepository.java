package com.example.orderitjava.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.orderitjava.data.api.RetrofitClient;
import com.example.orderitjava.data.model.tables.Table;
import com.example.orderitjava.utils.NetworkUtils;
import com.example.orderitjava.utils.Resource;

import java.util.List;
/**
 * Repository υπεύθυνο για την επικοινωνία με το API που αφορά τα τραπέζια.
 * <p>
 * Εδώ γίνεται η κλήση στο κατάλληλο endpoint μέσω Retrofit.
 * Η απάντηση τυλίγεται σε LiveData<Resource<T>> ώστε να μπορούμε
 * να παρακολουθούμε την κατάσταση του αιτήματος (loading, success, error).
 */

public class TableRepository {

    /**
     * Κάνει αίτημα στο backend για να πάρει τη λίστα των τραπεζιών.
     * Χρησιμοποιεί το helper class {@link NetworkUtils} για να τυλίξει
     * την απάντηση σε LiveData<Resource<List<Table>>>.
     *
     * @return LiveData που περιέχει την απάντηση από το backend
     *         μαζί με την κατάστασή της (loading, success, error).
     */
    public LiveData<Resource<List<Table>>> getTables() {
        return NetworkUtils.performRequest(RetrofitClient.getApiService().getTables());
    }
}
