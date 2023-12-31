package com.example.prabh.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    @NonNull
    private String first_name;
    @NonNull
    private String last_name;
    @NonNull
    private String email;
    @NonNull
    private String encryptedPassword;
    @NonNull
    private Boolean enabled;
}
