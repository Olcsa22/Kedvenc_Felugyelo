package com.example.vizsga_kedvenc_felugyelo.controll;

import com.example.vizsga_kedvenc_felugyelo.model.Animal;
import com.example.vizsga_kedvenc_felugyelo.service.AnimalService;
//import org.springframework.security.access.annotation.Secured;
import com.example.vizsga_kedvenc_felugyelo.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "localhost:4200")
@RequestMapping("/api")
public class AnimalController
{
    private final AnimalService animalService;
    private final UserService userService;

    public AnimalController(AnimalService animalService, UserService userService)
    {
        this.animalService = animalService;
        this.userService = userService;
    }

   // @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("profile/animals/needs")
    public List<String> getAllUserAnimalNeeds(HttpServletRequest request)
    {

        return animalService.getAllUserAnimalNeeds(userService.getLoggedInUserId(request));
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("profile/animals/myanimals")
    public List<Animal> getAllUserAnimals(HttpServletRequest request)
    {

        return animalService.getAllUserAnimals(userService.getLoggedInUserId(request));
    }

}
