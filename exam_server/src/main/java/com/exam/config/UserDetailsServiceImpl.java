package com.exam.config;

import com.exam.model.User;
import com.exam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    User user;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Not found user");
        }
        return user;
    }
    public User getUserDetail(){
        return user;
    }

}
