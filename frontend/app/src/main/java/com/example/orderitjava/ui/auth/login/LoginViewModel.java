package com.example.orderitjava.ui.auth.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.core.model.auth.LoginResponse;
import com.example.auth.repository.AuthRepository;
import com.example.core.utils.Resource;

import com.example.orderitjava.utils.SessionManager;
import com.example.orderitjava.utils.TokenProvider;

/**
 * ViewModel for the Login screen.
 * <p>
 * Handles interaction between the UI layer ({@link LoginActivity}) and
 * the data layer ({@link AuthRepository}).
 * It performs the login operation and exposes the login
 * result as {@link LiveData} wrapped in a {@link Resource}
 * to represent loading, success, or error states.
 * </p>
 *
 * Responsibilities:
 * <ul>
 *     <li>Initiates login requests via the repository</li>
 *     <li>Observes login results and updated LiveData</li>
 *     <li>Saves access and refresh tokens using {@link SessionManager} upon success</li>
 * </ul>
 */
public class LoginViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final SessionManager sessionManager;
    private final TokenProvider tokenProvider;

    private final MutableLiveData<Resource<LoginResponse>> loginResponseLiveData = new MutableLiveData<>();

    /**
     * Constructs the ViewModel and initializes dependencies.
     * @param application Application context required for {@link SessionManager}
     */
    public LoginViewModel(Application application) {
        super(application);
        sessionManager = new SessionManager(application);
        tokenProvider = TokenProvider.getInstance(sessionManager);
        authRepository = new AuthRepository();
    }

    /**
     * Initiates the login process using provided credentials.
     * <p>
     * The result is observed from the repository and then posted to LiveData.
     * On successful login, the JWT access and refresh tokens are stored locally.
     * </p>
     * @param username The user's username.
     * @param password The user's password.
     */
    public void login(String username, String password) {
        authRepository.loginUser(username, password).observeForever(resource -> {
            if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                sessionManager.saveTokens(
                        resource.data.getAccessToken(),
                        resource.data.getRefreshToken()
                );

                // Store in TokenProvider
                tokenProvider.updateTokens(
                        resource.data.getAccessToken(),
                        resource.data.getRefreshToken()
                );
//                TokenProvider.getInstance(getApplication())
//                        .updateTokens(
//                                resource.data.getAccessToken(),
//                                resource.data.getRefreshToken()
//                        );
            }

            loginResponseLiveData.setValue(resource);
        });
    }

    /**
     * @return LiveData that emits the current state of the login
     *          process (loading, success, error).
     */
    public LiveData<Resource<LoginResponse>> getLoginResponse() {
        return loginResponseLiveData;
    }
}
