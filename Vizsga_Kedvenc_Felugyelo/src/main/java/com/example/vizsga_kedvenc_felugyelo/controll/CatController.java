package com.example.vizsga_kedvenc_felugyelo.controll;

import com.example.vizsga_kedvenc_felugyelo.model.Cat;
import com.example.vizsga_kedvenc_felugyelo.service.CatService;
import com.example.vizsga_kedvenc_felugyelo.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "localhost:4200")
@RequestMapping("/api")
public class CatController
{
    @Autowired
    private final CatService catService;
    @Autowired
    private final UserService userService;

    public CatController(CatService catService, UserService userService)
    {
        this.catService = catService;
        this.userService = userService;
    }


    //Minden user saját macskáját elérheti vele
    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("profile/animals/mycats")
    public List<Cat> getUserCats(HttpServletRequest request)
    {
        return catService.getByOwner(userService.getLoggedInUserId(request));
    }

    //Összes létező macska megtekintése
    //@Secured("ROLE_ADMIN")
    @GetMapping("admin/animals/getallcats")
    public List<Cat> getAllCats()
    {
        return catService.getAll();
    }

    //Adminok által bármely macskát lekérni
    //@Secured("ROLE_ADMIN")
    @PostMapping("admin/animals/getcatbyid")
    public Cat getCatById(Integer id)
    {
        return catService.getById(id);
    }

    //Saját állat szükségleteinek lekérdezése
    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/getcatneeds")
    public List<String> getCatNeeds(@RequestBody Integer catid)
    {
        List<String> needs = catService.getCatNeeds(catid);
        if(needs.isEmpty())
        {
            needs.add("minden rendben");
        }
        return needs;
    }

    //Oltottság állapotának frissítése
    //@Secured("ROLE_USER,ROLE_ADMIN")
    @PostMapping("profile/animals/vaccinatecat")
    public ResponseEntity vaccinateCat(@RequestBody String data)
    {
        ObjectMapper mapper = new ObjectMapper();

        Integer catid = null;
        LocalDate time = null;

        try {

            JsonNode root = mapper.readTree(data);

            catid=root.path("catid").asInt();
            time= LocalDate.parse(root.path("data").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return catService.vaccinateCat(catid, time);
    }

    //Állat etetésének/itatásának frissítése
    //@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("profile/animals/feedcat")
    public ResponseEntity feedCat( @RequestBody String data)
    {
        ObjectMapper mapper = new ObjectMapper();

        Integer catID = null;
        LocalDateTime timestamp = null;

        try {

            JsonNode root = mapper.readTree(data);

            catID=root.path("catid").asInt();
            timestamp= LocalDateTime.parse(root.path("data").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return catService.feedCat(catID, timestamp);
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("profile/animals/deletecat")
    public String deleteCat(@RequestBody Integer catid)
    {
        System.out.println("Meghívták a catservicet");
        System.out.println("Válasz: ");
        String valasz = catService.deleteAnimal(catid);
        return valasz;
    }

    @PostMapping("add/addcat")
    public Cat addCat(@RequestBody Cat cat) {
        System.out.println(cat.toString());
        return catService.create(cat); }

}
