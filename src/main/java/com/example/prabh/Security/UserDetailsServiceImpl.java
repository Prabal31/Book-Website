package com.example.prabh.Security;

import java.util.ArrayList;
import java.util.List;

import com.example.prabh.Bean.User;
import com.example.prabh.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Autowire the database access object
    @Autowired
    private DataBaseAccess da;

    // Load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user in the database by username
        User user = da.findUserAccount(username);

        // Check if the user exists
        if (user == null) {
            System.out.print("Error 404 Not Found: " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        // Get the user's roles from the database
        List<String> roleNameList = new ArrayList<>();
        roleNameList.addAll(da.getRolesById(user.getUserId()));

        // Convert role names to GrantedAuthority objects
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNameList != null) {
            for (String role : roleNameList) {
                grantList.add(new SimpleGrantedAuthority(role));
            }
        }

        // Create and return UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getEncryptedPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                grantList
        );
    }
}
