package com.example.orderitjava.ui.auth;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.orderitjava.data.model.auth.LoginResponse;
import com.example.orderitjava.utils.Resource;

/**
 * Abstract base activity for authentication-related screens.
 * <p>
 * Provides shared UI behavior for observing authentication state and
 * displaying appropriate feedback based on the {@link Resource} status.
 * </p>
 *
 * This class improves the consistency across auth screens by centralizing
 * logic for:
 * <ul>
 *     <li>Showing a loading toast message</li>
 *     <li>Handling success state</li>
 *     <li>Displaying error messages</li>
 * </ul>
 *
 * Child classes (like {@link com.example.orderitjava.ui.auth.login.LoginActivity})
 * can call {@link #observeAuthResult(LiveData)} to plug in their LiveData results.
 */
public abstract class BaseAuthActivity extends AppCompatActivity {

    /**
     * Observes the authentication result emitted as LiveData and
     * handles UI feedback (loading, success, error) in a shared way.
     * @param liveData
     */
    protected void observeAuthResult(LiveData<Resource<LoginResponse>> liveData) {
        liveData.observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    Toast.makeText(
                        this, "Loading...",
                        Toast.LENGTH_SHORT
                    ).show();
                    break;
                case SUCCESS:
                    Toast.makeText(
                        this,
                        "Success!",
                        Toast.LENGTH_SHORT
                    ).show();
                    onAuthSuccess(resource.data);
                    break;
                case ERROR:
                    Toast.makeText(
                        this,
                        resource.message,
                        Toast.LENGTH_SHORT
                    ).show();
                    break;
            }
        });
    }

    /**
     * Hook method called when authentication is successful.
     * Override this in the child activities to handle navigation
     * or other logic.
     *
     * @param response The login response containing tokens.
     */
    protected void onAuthSuccess(LoginResponse response) {

    }
}
