package com.example.vizsga_kedvenc_felugyelo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dog")
public class Dog extends Animal implements DogInterface
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm", timezone="Europe/Berlin")
    @Column(name = "lastFeeding", nullable = false)
    private LocalDateTime lastFeeding;

    @Column(name = "feedingInterval", nullable = false)
    private Integer feedingInterval;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "ownerID", nullable = false)
    private Integer owner;

    @Column(name = "indoor", nullable = false)
    private Boolean indoor = false;

/*    @Column(name = "lastBath")
    private LocalDate lastBath;*/

    @Column(name = "vaccinated")
    private LocalDate vaccinated;

    @Column(name = "lastWalking")
    private LocalDate lastWalking;

    @Override
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDateTime getLastFeeding()
    {
        return lastFeeding;
    }

    public void setLastFeeding(LocalDateTime lastFeeding)
    {
        this.lastFeeding = lastFeeding;
    }

    public Integer getFeedingInterval()
    {
        return feedingInterval;
    }


    public void setFeedingInterval(Integer feedingInterval)
    {
        this.feedingInterval = feedingInterval;
    }

    public Integer getOwner()
    {
        return owner;
    }

    public void setOwner(Integer owner)
    {
        this.owner = owner;
    }

    public Boolean getIndoor()
    {
        return indoor;
    }

    public void setIndoor(Boolean indoor)
    {
        this.indoor = indoor;
    }

    /*public LocalDate getLastBath()
    {
        return lastBath;
    }

    public void setLastBath(LocalDate lastBath)
    {
        this.lastBath = lastBath;
    }*/

    public LocalDate getVaccinated()
    {
        return vaccinated;
    }

    public void setVaccinated(LocalDate vaccinated)
    {
        this.vaccinated = vaccinated;
    }

    public LocalDate getLastWalking()
    {
        return lastWalking;
    }

    public void setLastWalking(LocalDate lastWalking)
    {
        this.lastWalking = lastWalking;
    }

    public Boolean needsFeeding()
    {
        if(LocalDateTime.now().isAfter(this.lastFeeding.plus(feedingInterval, ChronoUnit.HOURS)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean needsVaccinating()
    {
        if(LocalDate.now().isAfter(vaccinated.plusYears(1)))
            return true;
        else
            return false;
    }

    public Boolean needsWalking()
    {
        if(LocalDate.now().isAfter(lastWalking.plusDays(1)) || LocalDate.now().isEqual(lastWalking.plusDays(1)))
            return true;
        else
            return false;
    }

    public List<String> needsList()
    {
        List<String> dogNeeds = new ArrayList<>();
        if(needsFeeding())
            dogNeeds.add(this.name + " éhes/szomjas lehet. Nézz rá a víz, és ételszintjére");
        if(needsVaccinating())
            dogNeeds.add(this.name + " kutyusnak esedékes az éves oltás");
        if(this.needsWalking())
            dogNeeds.add(this.name + " kutyusnak hátra van a napi séta");
        return dogNeeds;
    }
}