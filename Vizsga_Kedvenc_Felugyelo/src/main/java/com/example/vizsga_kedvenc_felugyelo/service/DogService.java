package com.example.vizsga_kedvenc_felugyelo.service;
import com.example.vizsga_kedvenc_felugyelo.Exception.DateAfterNowException;
import com.example.vizsga_kedvenc_felugyelo.Exception.DateBeforePreviousException;
import com.example.vizsga_kedvenc_felugyelo.model.Dog;
import com.example.vizsga_kedvenc_felugyelo.repository.DogRepository;
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
public class DogService
{
    private final DogRepository dogRepository;
    @Autowired
    private final EntityManager entityManager;

    public DogService(DogRepository dogRepository, EntityManager entityManager)
    {
        this.dogRepository = dogRepository;
        this.entityManager = entityManager;
    }

    public Dog create(Dog dog) {return dogRepository.save(dog);}

    public List<Dog> getByOwner(Integer id)
    {
        return dogRepository.findByOwner(id);
    }

    public List<Dog> getAll()
    {
        return dogRepository.findAll();
    }

    public Dog getById(Integer id)
    {
        Optional<Dog> cat=dogRepository.findById(id);
        if(cat.isPresent())
            return cat.get();
        else
            throw new NoResultException("Nincs ilye id-val kutyus");
    }

    public List<String> getDogNeeds(Integer dogid)
    {
        Optional<Dog> dog = dogRepository.findById(dogid);
        if(dog.isPresent())
        {
            Dog dog1 = dog.get();
            return dog1.needsList();
        }
        else
        throw new NoResultException("Nincs ilyen kutya");
    }

    public ResponseEntity vaccinateDog(Integer dogid, LocalDate date)
    {
        try
        {
            if (dogRepository.findById(dogid).get().getVaccinated().isAfter(date))
            {
                throw new DateBeforePreviousException("Nem adható meg az eddiginél korábbi dátum");
            } else if (LocalDate.now().isBefore(date))
            {
                throw new DateAfterNowException("Nem adható meg a mai napnál későbbi dátum!");
            } else
            {
                StoredProcedureQuery spq = entityManager.createStoredProcedureQuery("updateDogVaccinated");
                spq.registerStoredProcedureParameter("dogIdIn", Integer.class, ParameterMode.IN);
                spq.registerStoredProcedureParameter("lastVaccinatedIn", LocalDate.class, ParameterMode.IN);

                spq.setParameter("dogIdIn", dogid);
                spq.setParameter("lastVaccinatedIn", date);

                spq.execute();

                return new ResponseEntity<>(getById(dogid), HttpStatus.OK);
            }
        }catch (DateAfterNowException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (DateBeforePreviousException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity feedDog(Integer dogid, LocalDateTime timestamp)
    {
        try
        {
        if(dogRepository.findById(dogid).get().getLastFeeding().isAfter(timestamp) )
        {
            throw new DateBeforePreviousException("Nem adható meg az eddiginél korábbi dátum");
        }
        else if(LocalDateTime.now().isBefore(timestamp))
        {
            throw new DateAfterNowException("Nem adható meg a mai napnál későbbi dátum!");
        }
        else
        {
            StoredProcedureQuery spq = entityManager.createStoredProcedureQuery("updateDogFeeding");
            spq.registerStoredProcedureParameter("dogIdIn", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("lastFeedingIn", LocalDateTime.class, ParameterMode.IN);

            spq.setParameter("dogIdIn", dogid);
            spq.setParameter("lastFeedingIn", timestamp);

            spq.execute();

            return new ResponseEntity<>(getById(dogid), HttpStatus.OK);
        }
        }catch (DateAfterNowException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (DateBeforePreviousException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity walkDog(Integer dogid, LocalDate localDate)
    {
        try
        {
        if(dogRepository.findById(dogid).get().getLastWalking().isAfter(localDate) )
        {
            throw new DateBeforePreviousException("Nem adható meg az eddiginél korábbi dátum");
        }
        else if(LocalDate.now().isBefore(localDate))
        {
            throw new DateAfterNowException("Nem adható meg a mai napnál későbbi dátum!");
        }
        else
        {

            StoredProcedureQuery spq = entityManager.createStoredProcedureQuery("updateDogWalking");
            spq.registerStoredProcedureParameter("dogIdIn", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("lastWalkingIn", LocalDate.class, ParameterMode.IN);

            spq.setParameter("dogIdIn", dogid);
            spq.setParameter("lastWalkingIn", localDate);

            spq.execute();

            return new ResponseEntity<>(getById(dogid), HttpStatus.OK);
        }
        }catch (DateAfterNowException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (DateBeforePreviousException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String deleteAnimal(Integer dogId)
    {
        try
        {
            StoredProcedureQuery spq= entityManager.createStoredProcedureQuery("deleteDog");
            spq.registerStoredProcedureParameter("dogIdIn", Integer.class, ParameterMode.IN);

            spq.setParameter("dogIdIn", dogId);

            spq.execute();

            return "Sikeres törlés";
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "A törlés sikertelen";
        }
    }
}
