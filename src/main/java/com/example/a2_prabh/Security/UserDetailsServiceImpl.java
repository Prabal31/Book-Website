package com.example.a2_prabh.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.a2_prabh.Bean.User;
import com.example.a2_prabh.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DataBaseAccess da;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = da.findUserAccount(username);

        if (user == null) {
            System.out.print("Error 404 Not Found: " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }



        List<String> roleNameList = new ArrayList<>();
        roleNameList.addAll(da.getRolesById(user.getUserId()));
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNameList != null) {
            for (String role : roleNameList)
                grantList.add(new SimpleGrantedAuthority(role));
        }

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
