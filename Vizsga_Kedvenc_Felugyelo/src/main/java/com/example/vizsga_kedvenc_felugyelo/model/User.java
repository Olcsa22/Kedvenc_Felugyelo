package com.example.vizsga_kedvenc_felugyelo.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User
{
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_generator")
    //@SequenceGenerator(name = "user_generator", sequenceName = "user_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<Role>();

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "register", nullable = false)
    private LocalDate register;

    public User()
    {

    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public LocalDate getRegister()
    {
        return register;
    }

    public void setRegister(LocalDate register)
    {
        this.register = register;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public User (String username, String password, String email, LocalDate register)
    {
        this.username=username;
        this.password=password;
        this.email=email;
        this.register=register;
    }
}