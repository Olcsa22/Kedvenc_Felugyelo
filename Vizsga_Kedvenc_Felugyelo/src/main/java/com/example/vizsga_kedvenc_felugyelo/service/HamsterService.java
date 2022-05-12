package com.example.vizsga_kedvenc_felugyelo.service;

import com.example.vizsga_kedvenc_felugyelo.Exception.DateAfterNowException;
import com.example.vizsga_kedvenc_felugyelo.Exception.DateBeforePreviousException;
import com.example.vizsga_kedvenc_felugyelo.model.Hamster;
import com.example.vizsga_kedvenc_felugyelo.repository.HamsterRepository;
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
public class HamsterService
{
    @Autowired
    private final HamsterRepository hamsterRepository;
    @Autowired
    private final EntityManager entityManager;

    public HamsterService(HamsterRepository hamsterRepository, UserService userService, EntityManager entityManager)
    {
        this.hamsterRepository = hamsterRepository;
        this.entityManager = entityManager;
    }

    //    public Hamster(Integer owner, Timestamp lastFeeding, Integer feedingInterval, LocalDate lastCleaning, String name, LocalDate toothwearerChanged)
    public Hamster create( Hamster hamster)
    {

        return hamsterRepository.save(hamster);
    }

    public List<Hamster> getByOwner(Integer id)
    {
        return hamsterRepository.findByOwner(id);
    }

    public List<Hamster> getAll()
    {
        return hamsterRepository.findAll();
    }

    public Hamster getById(Integer id)
    {
        Optional<Hamster> hamster=hamsterRepository.findById(id);
        if(hamster.isPresent())
            return hamster.get();
        else
            throw new NoResultException("Nincs ilye id-val höri");
    }

    public List<String> getHamsterNeeds(Integer hamsterid)
    {
        Optional<Hamster> hamster = hamsterRepository.findById(hamsterid);
        if(hamster.isPresent())
        {
            Hamster hamster1 = hamster.get();
            return hamster1.needsList();
        }
        else
            throw new NoResultException("Nincs ilyen höri");
    }

    public ResponseEntity feedHamster(Integer hamsterid, LocalDateTime timestamp)
    {
        try
        {
        if (getById(hamsterid).getLastFeeding().isAfter(timestamp))
        {
            throw new DateBeforePreviousException("Nem adható meg az eddiginél korábbi dátum");
        }
        else if(LocalDateTime.now().isBefore(timestamp))
        {
            throw new DateAfterNowException("Nem adható meg a mai napnál későbbi dátum!");
        }
        else
        {

            StoredProcedureQuery spq = entityManager.createStoredProcedureQuery("updateHamsterFeeding");
            spq.registerStoredProcedureParameter("hamsterIdIn", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("lastFeedingIn", LocalDateTime.class, ParameterMode.IN);

            spq.setParameter("hamsterIdIn", hamsterid);
            spq.setParameter("lastFeedingIn", timestamp);

            spq.execute();

            return new ResponseEntity(getById(hamsterid), HttpStatus.OK);
        }
        }catch (DateAfterNowException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (DateBeforePreviousException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity cleanHamster(Integer hamsterid, LocalDate localDate)
    {
        try
        {
        if(getById(hamsterid).getLastCleaning().isAfter(localDate) )
        {
            throw new DateBeforePreviousException("Nem adható meg az eddiginél korábbi dátum");
        }
        else if(LocalDate.now().isBefore(localDate))
        {
            throw new DateAfterNowException("Nem adható meg a mai napnál későbbi dátum!");
        }
        else
        {

            StoredProcedureQuery spq = entityManager.createStoredProcedureQuery("updateHamsterCleaning");
            spq.registerStoredProcedureParameter("hamsterIdIn", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("lastCleaningIn", LocalDate.class, ParameterMode.IN);

            spq.setParameter("hamsterIdIn", hamsterid);
            spq.setParameter("lastCleaningIn", localDate);

            spq.execute();

            return new ResponseEntity(getById(hamsterid),HttpStatus.OK);
        }
        }catch (DateAfterNowException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (DateBeforePreviousException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity changeToothWearer(Integer hamsterid, LocalDate localDate)
    {

        try
        {
        if(getById(hamsterid).getToothwearerChanged().isAfter(localDate) )
        {
            throw new DateBeforePreviousException("Nem adható meg az eddiginél korábbi dátum");
        }
        else if(LocalDate.now().isBefore(localDate))
        {
            throw new DateAfterNowException("Nem adható meg a mai napnál későbbi dátum!");
        }
        else
        {

            StoredProcedureQuery spq = entityManager.createStoredProcedureQuery("updateHamsterToothWearer");
            spq.registerStoredProcedureParameter("hamsterIdIn", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("toothwearerChanged", LocalDate.class, ParameterMode.IN);

            spq.setParameter("hamsterIdIn", hamsterid);
            spq.setParameter("toothwearerChanged", localDate);

            spq.execute();

            return new ResponseEntity(getById(hamsterid),HttpStatus.OK);
        }
        }catch (DateAfterNowException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (DateBeforePreviousException ex)
        {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String deleteAnimal(Integer hamsterId)
    {
        try
        {

            StoredProcedureQuery spq= entityManager.createStoredProcedureQuery("deleteHamster");
            spq.registerStoredProcedureParameter("hamsterIdIn", Integer.class, ParameterMode.IN);

            spq.setParameter("hamsterIdIn", hamsterId);

            spq.execute();

            return "Sikeres törlés";
        }catch (Exception ex)
        {
            return "A törlés sikertelen";
        }
    }
}
