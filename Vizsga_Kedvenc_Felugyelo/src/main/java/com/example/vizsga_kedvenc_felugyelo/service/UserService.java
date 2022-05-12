package com.example.vizsga_kedvenc_felugyelo.service;

import com.example.vizsga_kedvenc_felugyelo.model.User;
import com.example.vizsga_kedvenc_felugyelo.repository.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService //implements UserDetailsService
{
    private final UserRepository userRepository;
    @PersistenceContext
    private final EntityManager entitymanager;

    public UserService(UserRepository userRepository, EntityManager entitymanager)
    {
        this.userRepository = userRepository;
        this.entitymanager = entitymanager;
    }

    public User create(User user) throws Exception
    {
        User user1 = userRepository.findByUsername(user.getUsername());
        User user2 = userRepository.findByEmail(user.getEmail());
        if(user1==null && user2==null)
        {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRegister(LocalDate.now());

            User user3=userRepository.save(user);
            StoredProcedureQuery spq = entitymanager.createStoredProcedureQuery("insertUserWithRole");
            spq.registerStoredProcedureParameter("idIn", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("roleIdIn", Integer.class, ParameterMode.IN);

            spq.setParameter("idIn", user3.getId());
            spq.setParameter("roleIdIn", 2);

            spq.execute();

            return user3;
        }
        else
            throw new Exception("A felhasználónév/email cím foglalt");
    }

    public User findById(Integer id)
    {
        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent())
        {
            return optional.get();
        } else
        {
            throw new NoResultException("Nincs ilyen id-val felhasználó");
        }
    }

    public User findByUsername(String username)
    {
        User user = userRepository.findByUsername(username);
        if(user!=null)
        {
            return user;
        }
        else
        {
            return null;
        }
    }

    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    public Integer getLoggedInUserId(HttpServletRequest request)
    {
        User user = findByUsername(request.getUserPrincipal().getName());
        return user.getId();
    }

    public String deleteUser(Integer userid)
    {
        try
        {
            StoredProcedureQuery spq = entitymanager.createStoredProcedureQuery("deleteUser");
            spq.registerStoredProcedureParameter("userIdIn", Integer.class, ParameterMode.IN);
            spq.setParameter("userIdIn",userid);
            spq.execute();
            return "Sikeres törlés";
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return "Sikertelen törlés";
        }
    }
}
