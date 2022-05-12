package com.example.vizsga_kedvenc_felugyelo.controll;

import com.example.vizsga_kedvenc_felugyelo.model.Dog;
import com.example.vizsga_kedvenc_felugyelo.model.User;
import com.example.vizsga_kedvenc_felugyelo.service.DogService;
//import org.springframework.security.access.annotation.Secured;
import com.example.vizsga_kedvenc_felugyelo.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class DogController
{
    @Autowired
    private final DogService dogService;
    @Autowired
    private final UserService userService;

    public DogController(DogService dogService, UserService userService)
    {
        this.dogService = dogService;
        this.userService = userService;
    }

  //  @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("profile/animals/mydogs")
    public List<Dog> getUserDogs(HttpServletRequest request)
    {
        System.out.println("Bejelentkezve: "+request.getUserPrincipal().getName());
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println(authorities.stream().collect(Collectors.toList()).get(0));
        List<Dog> dogs= dogService.getByOwner(userService.getLoggedInUserId(request));
        return dogs;
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("admin/animals/getalldogs")
    public List<Dog> getAllDogs()
    {
        return dogService.getAll();
    }

    //@Secured("ROLE_ADMIN")
    @PostMapping("admin/animals/getdogbyid")
    public Dog getDogById(@RequestBody Integer id)
    {
        return dogService.getById(id);
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/getdogneeds")
    public List<String> getDogNeeds(@RequestBody Integer dogid)
    {
        List<String> needs = dogService.getDogNeeds(dogid);
        if(needs.isEmpty())
        {
            needs.add("minden rendben");
        }
        return needs;
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/vaccinatedog")
    public ResponseEntity vaccinateDog(@RequestBody String data)
    {

        ObjectMapper mapper = new ObjectMapper();

        Integer value = null;
        LocalDate time = null;

        try {

            JsonNode root = mapper.readTree(data);

            value=root.path("dogid").asInt();
            time= LocalDate.parse(root.path("data").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dogService.vaccinateDog(value,time);
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping(value = "profile/animals/feeddog")
    public ResponseEntity feedDog(@RequestBody String data)
    {

        ObjectMapper mapper = new ObjectMapper();

        Integer value = null;
        LocalDateTime time = null;

        try {

            JsonNode root = mapper.readTree(data);

            value=root.path("dogid").asInt();
            time= LocalDateTime.parse(root.path("data").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dogService.feedDog(value,time);
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/walkdog")
    public ResponseEntity walkDog(@RequestBody String data)
    {

        ObjectMapper mapper = new ObjectMapper();

        Integer value = null;
        LocalDate time = null;

        try {

            JsonNode root = mapper.readTree(data);

            value=root.path("dogid").asInt();
            time= LocalDate.parse(root.path("data").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dogService.walkDog(value,time);
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/deletedog")
    public String deletedog(@RequestBody Integer dogid)
    {
        String valasz= dogService.deleteAnimal(dogid);
        return valasz;
    }

    @PostMapping("add/adddog")
    public Dog addDog(@RequestBody Dog dog){
        return dogService.create(dog);
        }
}
