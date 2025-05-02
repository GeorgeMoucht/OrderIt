package com.example.core.model.auth;


/**
 * Request body model for user login API.
 * <p>
 * Represent the credentials required by the backend to authenticate
 * a user. This object is serialized into JSON and sent in the POST
 * request body to the login endpoint.
 * </p>
 *
 * This class is typically used in API calls for user authentication.
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
