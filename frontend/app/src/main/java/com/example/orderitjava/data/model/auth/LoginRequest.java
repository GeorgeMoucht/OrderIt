package com.example.orderitjava.data.model.auth;


/**
 * Request body model for user login API.
 * <p>
 * Represent the credentials required by the backend to authenticate
 * a user. This object is serialized into JSON and sent in the POST
 * request body to the login endpoint.
 * </p>
 *
 * Used by: {@link com.example.orderitjava.data.api.ApiService#loginUser(LoginRequest)}
 */
public class LoginRequest {
    private final String username;
    private final String password;

    /**
     * Constructs a login request with the provided credentials.
     *
     * @param username The user's username or email.
     * @param password The user's password.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return The username or email entered by the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The password entered by the user.
     */
    public String getPassword() {
        return password;
    }
}
