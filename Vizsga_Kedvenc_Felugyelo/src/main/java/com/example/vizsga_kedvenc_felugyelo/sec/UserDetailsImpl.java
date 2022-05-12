package com.example.vizsga_kedvenc_felugyelo.sec;

import com.example.vizsga_kedvenc_felugyelo.model.Role;
import com.example.vizsga_kedvenc_felugyelo.model.User;
import com.example.vizsga_kedvenc_felugyelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class UserDetailsImpl implements UserDetails
{
    private User user;
    public UserDetailsImpl(User user)
    {
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Set<Role> roles= user.getRoles();
        for(Role role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public Integer getId() {
        return  user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
