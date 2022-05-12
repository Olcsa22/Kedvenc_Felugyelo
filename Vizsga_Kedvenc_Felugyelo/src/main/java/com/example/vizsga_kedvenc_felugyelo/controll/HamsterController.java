package com.example.vizsga_kedvenc_felugyelo.controll;

import com.example.vizsga_kedvenc_felugyelo.model.Hamster;
import com.example.vizsga_kedvenc_felugyelo.service.HamsterService;
//import org.springframework.security.access.annotation.Secured;
import com.example.vizsga_kedvenc_felugyelo.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "localhost:4200")
@RequestMapping("/api")
public class HamsterController
{

    @Autowired
    private final HamsterService hamsterService;
    @Autowired
    private final UserService userService;

    public HamsterController(HamsterService hamsterService, UserService userService)
    {
        this.hamsterService = hamsterService;
        this.userService = userService;
    }


  //  @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("profile/animals/myhamsters")
    public List<Hamster> getUserHamsters(HttpServletRequest request)
    {
            return hamsterService.getByOwner(userService.getLoggedInUserId(request));
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("admin/animals/getallhamsters")
    public List<Hamster> getAllHamsters()
    {
        return hamsterService.getAll();
    }

    //@Secured("ROLE_ADMIN")
    @PostMapping("admin/animals/gethamsterbyid")
    public Hamster getHamsterById(@RequestBody Integer id)
    {
        return hamsterService.getById(id);
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/gethamsterneeds")
    public List<String> getHamsterNeeds(@RequestBody Integer hamsterid)
    {
        List<String> needs = hamsterService.getHamsterNeeds(hamsterid);
        if(needs.isEmpty())
        {
            needs.add("minden rendben");
        }
        return needs;
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/feedhamster")
    public ResponseEntity feedHamster(@RequestBody String data)
    {
        ObjectMapper mapper = new ObjectMapper();

        Integer hamsterid = null;
        LocalDateTime timestamp = null;

        try {

            JsonNode root = mapper.readTree(data);

            hamsterid=root.path("hamsterid").asInt();
            timestamp= LocalDateTime.parse(root.path("data").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hamsterService.feedHamster(hamsterid,timestamp);
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/cleanhamster")
    public ResponseEntity cleanHamster(@RequestBody String data)
    {
        ObjectMapper mapper = new ObjectMapper();

        Integer hamsterid = null;
        LocalDate localDate = null;

        try {

            JsonNode root = mapper.readTree(data);

            hamsterid=root.path("hamsterid").asInt();
            localDate= LocalDate.parse(root.path("data").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hamsterService.cleanHamster(hamsterid,localDate);
    }

    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/changetoothwearer")
    public ResponseEntity changeToothWearer(@RequestBody String data)
    {
        ObjectMapper mapper = new ObjectMapper();

        Integer hamsterid = null;
        LocalDate localDate = null;

        try {

            JsonNode root = mapper.readTree(data);

            hamsterid=root.path("hamsterid").asInt();
            localDate= LocalDate.parse(root.path("data").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hamsterService.changeToothWearer(hamsterid, localDate);
    }



    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/deletehamster")
    public String deleteHamster(@RequestBody Integer hamsterId)
    {
        return hamsterService.deleteAnimal(hamsterId);
    }
//    public Hamster(Integer owner, Timestamp lastFeeding, Integer feedingInterval, LocalDate lastCleaning, String name, LocalDate toothwearerChanged)
    @PostMapping("add/addhamster")
    public Hamster addHamster(@RequestBody Hamster hamster)
    { return hamsterService.create(hamster); }
}
