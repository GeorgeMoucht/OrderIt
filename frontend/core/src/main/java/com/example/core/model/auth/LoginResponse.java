package com.example.core.model.auth;

/**
 * Response model for a successful login API call.
 * <p>
 * Reprisents the JSON object returned by the backend after valid
 * authenticatio, containing JWT tokens for authenticated access.
 * </p>
 *
 * JSON Example:
 * <pre>
 *     {
 *         "access": "eyJ0eXAiOiJKV1QiLCJhbGciOi...",
 *         "refresh": "eyJ0eXAiOiJKV1QiLCJhbGciOi.."
 *     }
 * </pre>
 *
 * This class is typically used in API calls for user authentication.
 */
public class LoginResponse {
    private String access;
    private String refresh;

    /**
     * @return The short-lived access token used for
     *         authenticated requests.
     */
    public String getAccessToken() {
        return access;
    }

    /**
     * @return The long-lived refresh token used to obtain new
     *         access tokens.
     */
    public String getRefreshToken() {
        return refresh;
    }
}
