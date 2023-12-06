package com.example.prabh.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * The User class represents a user entity with various attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * The unique identifier for the user.
     */
    private Long userId;

    /**
     * The first name of the user.
     */
    @NonNull
    private String first_name;

    /**
     * The last name of the user.
     */
    @NonNull
    private String last_name;

    /**
     * The email address of the user.
     */
    @NonNull
    private String email;

    /**
     * The encrypted password associated with the user.
     */
    @NonNull
    private String encryptedPassword;

    /**
     * A flag indicating whether the user account is enabled or not.
     */
    @NonNull
    private Boolean enabled;
}
