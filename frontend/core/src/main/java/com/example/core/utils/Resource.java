package com.example.core.utils;

/**
 * Generic wrapper class that represents the state of a resource
 * (data) being loaded.
 * <p>
 * This is commonly used in MVVM architecture to communicate data + UI
 * state (e.g. loading, success, error) from repositories to the
 * UI via LiveData.
 * </p>
 *
 * @param <T> The type of data being wrapped.
 *
 * Example usage:
 * <pre>
 * LiveData&lt;Resource&lt;LoginResponse&gt;&gt; login Result = authRepository.loginUser(...);
 * </pre>
 */
public class Resource<T> {
    /**
     * Status that represents the current state of the resource.
     */
    public enum Status {
        /**
         * Data was successfully loaded
         */
        SUCCESS,
        /**
         * An error occurred while we loading the data.
         */
        ERROR,
        /**
         * Data is currently being loaded.
         */
        LOADING
    }

    /**
     * The status of the resource (SUCCESS, ERROR, LOADING)
     */
    public final Status status;

    /**
     * The actual data that are being returned from the operation
     * (may be null in case of ERROR or LOADING).
     */
    public final T data;

    /**
     * Optional message associated with the state
     * (usually used for errors).
     */
    public final String message;

    public Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /**
     * Creates a {@link Resource} with status SUCCESS and provided data.
     * @param data The data returned from the successful operation.
     * @param <T> Type of the data.
     * @return A Resource instance representing success.
     */
    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    /**
     * Creates a {@link Resource} with status ERROR and an error message.
     *
     * @param msg The error message
     * @param <T> Type of the data (will be null in this case)
     * @return A Resource instance representing an error
     */
    public static <T> Resource<T> error(String msg) {
        return new Resource<>(Status.ERROR, null, msg);
    }

    /**
     * Creates a {@link Resource} with status LOADING.
     *
     * @param <T> Type of the data (usually null while loading)
     * @return A Resource instance representing loading state
     */
    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING, null, null);
    }
}
