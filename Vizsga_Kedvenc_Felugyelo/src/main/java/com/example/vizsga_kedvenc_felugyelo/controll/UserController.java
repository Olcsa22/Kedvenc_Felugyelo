package com.example.vizsga_kedvenc_felugyelo.controll;

import com.example.vizsga_kedvenc_felugyelo.model.User;
import com.example.vizsga_kedvenc_felugyelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController
{
    @Autowired
    public UserService userService;

    public UserController (UserService userService)
    {
        this.userService=userService;
    }

    @PostMapping("user/add")
    public User addUser(@RequestBody User user) throws Exception
    {
        return userService.create(user);
    }


  //  @Secured("ROLE_ADMIN")
    @PostMapping(path = "admin/getuserbyid/{id}")
    public User findUserById(@PathVariable("id") Integer id, HttpServletRequest request)
    {
            return userService.findById(id);
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping(path = "admin/getuserbyname/{username}")
    public User findUserByUsername(@PathVariable("username") String username, HttpServletRequest request)
    {
        return userService.findByUsername(username);
    }

    @GetMapping(path = "admin/getallusers")
    public List<User> getAllUsers()
    {
        return userService.getAll();
    }

    @PostMapping(path = "admin/deleteuser")
    public String deleteUser(@RequestBody Integer userid)
    {
        String s= userService.deleteUser(userid);
        return s;
    }


}
