package com.example.vizsga_kedvenc_felugyelo.repository;

import com.example.vizsga_kedvenc_felugyelo.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Integer>
{
    @Query(value = "select c from Cat c where c.owner = :ownerid")
    public List<Cat> findByOwner(@Param("ownerid") Integer id);


}