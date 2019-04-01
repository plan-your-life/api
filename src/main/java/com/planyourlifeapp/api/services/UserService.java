package com.planyourlifeapp.api.services;


import com.planyourlifeapp.api.models.Role;
import com.planyourlifeapp.api.models.User;
import com.planyourlifeapp.api.models.VerifyUser;
import com.planyourlifeapp.api.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

@Service(value="userService")
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    public PasswordEncoder encoder;

    @Autowired
    public UserRepository repo;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username){
        if(loginAttemptService.isBlocked(username)){
            throw new RuntimeException("Blocked");
        }
        User user = repo.getByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        if (!user.isActive()){
            throw new UsernameNotFoundException("User not active");
        }
        Role role = new Role(user.getAdminLevel());
        //return the user
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), role.getRole());
    }

    //Create a user in database
    public User createUser(User user){
        user.setPassword(this.encoder.encode(user.getPassword()));
        VerifyUser verify = new VerifyUser(user);
        user.setVerify(verify);
        user.setAdminLevel(0);
        user.setDate(new Date());
        user.setActive(false); //Email verification
        return repo.save(user);
    }

}
