package com.example.vizsga_kedvenc_felugyelo.service;

import com.example.vizsga_kedvenc_felugyelo.model.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService
{

    @PersistenceContext
    @Autowired
    private final EntityManager entitymanager;

    public AnimalService(EntityManager entitymanager)
    {
        this.entitymanager = entitymanager;
    }

    public List<String> getAllUserAnimalNeeds(Integer userid)
    {

        List<Animal> animals = getAllUserAnimals(userid);
        List<String> needs = new ArrayList<>();
        for(Animal a:animals)
        {
            needs.addAll(a.needsList());
        }

        return needs;
    }

    public List<Animal> getAllUserAnimals(Integer user)
    {

        Query query = entitymanager.createQuery( "Select c from Cat c WHERE c.owner="+user );
        List<Animal> list = (List<Animal>)query.getResultList();

        query = entitymanager.createQuery( "Select d from Dog d WHERE d.owner="+user );
        list.addAll((List<Animal>)query.getResultList());

        query= entitymanager.createQuery("Select h from Hamster h Where h.owner="+user);
        list.addAll((List<Animal>)query.getResultList());

        return list;
    }

}
