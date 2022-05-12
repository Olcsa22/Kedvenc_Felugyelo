package com.example.vizsga_kedvenc_felugyelo.repository;

import com.example.vizsga_kedvenc_felugyelo.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Integer>
{
    @Query(value = "select d from Dog d where d.owner =:ownerid")
    public List<Dog> findByOwner(@Param("ownerid") Integer id);
}