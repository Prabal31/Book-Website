package com.example.BookStore.Security;

import java.util.ArrayList;
import java.util.List;

import com.example.BookStore.Bean.User;
import com.example.BookStore.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private DataBaseAccess da;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = da.findUserAccount(username);


        // explicite package name allows adding more user, since complier woill get ambigous which user class are you referring to
        if(user == null) {
            System.out.print("Error 404 Not Found: "+ username);
            throw new UsernameNotFoundException("User "+username+ " was not found in the database");
        }
        List<String> roleNameList = new ArrayList<>();
        roleNameList.addAll(da.getRolesById(user.getUserId()));
        List<GrantedAuthority> grantList = new ArrayList<>();
        if(roleNameList != null) {
            for(String role: roleNameList)
                grantList.add(new SimpleGrantedAuthority(role));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), grantList);
        return userDetails;
    }

}
