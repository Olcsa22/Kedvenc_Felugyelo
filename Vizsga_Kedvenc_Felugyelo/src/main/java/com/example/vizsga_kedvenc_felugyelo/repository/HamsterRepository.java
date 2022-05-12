package com.example.vizsga_kedvenc_felugyelo.repository;

import com.example.vizsga_kedvenc_felugyelo.model.Hamster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HamsterRepository extends JpaRepository<Hamster, Integer>
{
    @Query(value = "Select h from Hamster h where h.owner=:ownerid")
    public List<Hamster> findByOwner(@Param("ownerid") Integer id);
}