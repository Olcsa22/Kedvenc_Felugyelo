package com.example.vizsga_kedvenc_felugyelo.service;

import com.example.vizsga_kedvenc_felugyelo.Exception.DateAfterNowException;
import com.example.vizsga_kedvenc_felugyelo.Exception.DateBeforePreviousException;
import com.example.vizsga_kedvenc_felugyelo.model.Cat;
import com.example.vizsga_kedvenc_felugyelo.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CatService
{
    private final CatRepository catRepository;
    @PersistenceContext
    @Autowired
    private final EntityManager entitymanager;

    public CatService(CatRepository catRepository, EntityManager entitymanager)
    {
        this.catRepository = catRepository;
        this.entitymanager = entitymanager;
    }

    public Cat create(Cat cat)
    {
        return catRepository.save(cat);
    }

    public List<Cat> getByOwner(Integer id)
    {
        List<Cat> catList = catRepository.findByOwner(id);
        if(catList.isEmpty())
            throw new NoResultException("Nincs cica ilyen gazdával");
        else
            return catList;
    }

    public List<Cat> getAll()
    {
        return catRepository.findAll();
    }

    public Cat getById(Integer id)
    {
        Optional<Cat> cat=catRepository.findById(id);
        if(cat.isPresent())
        return cat.get();
        else
        throw new NoResultException("Nincs ilye id-val cica");
    }

    public List<String> getCatNeeds(Integer catid)
    {
        Optional<Cat> cat = catRepository.findById(catid);
        if(cat.isPresent())
        {
            Cat cat1 = cat.get();
            return cat1.needsList();
        }
        else
            throw new NoResultException("Nincs ilyen cicus");
    }

    public ResponseEntity vaccinateCat(Integer catid, LocalDate date)
    {
        try
        {
        if(getById(catid).getVaccinated().isAfter(date) )
        {
            throw new DateBeforePreviousException("Nem adható meg az eddiginél korábbi dátum");
        }
        else if(LocalDate.now().isBefore(date))
        {
            throw new DateAfterNowException("Nem adható meg a mai napnál későbbi dátum!");
        }
        else
        {

            StoredProcedureQuery spq = entitymanager.createStoredProcedureQuery("updateCatVaccinated");
            spq.registerStoredProcedureParameter("catIdIn", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("lastVaccinatedIn", LocalDate.class, ParameterMode.IN);

            spq.setParameter("catIdIn", catid);
            spq.setParameter("lastVaccinatedIn", date);

            spq.execute();

            return new ResponseEntity(getById(catid), HttpStatus.OK);
        }
        }catch (DateAfterNowException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (DateBeforePreviousException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity feedCat(Integer catid, LocalDateTime timestamp)
    {
        if(getById(catid).getLastFeeding().isAfter(timestamp) )
        {
            return new ResponseEntity("Nem adható meg az eddiginél korábbi dátum", HttpStatus.BAD_REQUEST);
        }
        else if(LocalDateTime.now().isBefore(timestamp))
        {
            return new ResponseEntity("Nem adható meg a mai napnál későbbi dátum!", HttpStatus.BAD_REQUEST);
        }
        else
        {

            StoredProcedureQuery spq = entitymanager.createStoredProcedureQuery("updateCatFeeding");
            spq.registerStoredProcedureParameter("catIdIn", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("lastFeedingIn", LocalDateTime.class, ParameterMode.IN);

            spq.setParameter("catIdIn", catid);
            spq.setParameter("lastFeedingIn", timestamp);

            spq.execute();

            return new ResponseEntity(getById(catid),HttpStatus.OK);
        }
    }

    public String deleteAnimal(Integer catId)
    {
        try
        {

            StoredProcedureQuery spq= entitymanager.createStoredProcedureQuery("deleteCat");
            spq.registerStoredProcedureParameter("catIdIn", Integer.class, ParameterMode.IN);

            spq.setParameter("catIdIn", catId);

            spq.execute();

            return "Sikeres törlés";
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return "A törlés sikertelen";
        }
    }


}
