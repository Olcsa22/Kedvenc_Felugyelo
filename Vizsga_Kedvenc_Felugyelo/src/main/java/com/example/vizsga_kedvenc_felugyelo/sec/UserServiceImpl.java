package com.example.vizsga_kedvenc_felugyelo.sec;

import com.example.vizsga_kedvenc_felugyelo.model.User;
import com.example.vizsga_kedvenc_felugyelo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);
        if(user==null)
        {
        }
        return new UserDetailsImpl(user);
    }
}
