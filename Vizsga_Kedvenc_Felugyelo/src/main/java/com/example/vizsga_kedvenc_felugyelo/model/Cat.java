package com.example.vizsga_kedvenc_felugyelo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cat")
public class Cat extends Animal
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "indoor", nullable = false)
    private Boolean indoor = false;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm", timezone="Europe/Berlin")
    @Column(name = "lastFeeding", nullable = false)
    private LocalDateTime lastFeeding;

    @Column(name = "feedingInterval", nullable = false)
    private Integer feedingInterval;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "ownerID", nullable = false)
    private Integer owner;

    @Column(name = "vaccinated")
    private LocalDate vaccinated;



    public LocalDate getVaccinated()
    {
        return vaccinated;
    }

    public void setVaccinated(LocalDate vaccinated)
    {
        this.vaccinated = vaccinated;
    }

    public Integer getOwner()
    {
        return owner;
    }

    public void setOwner(Integer ownerID)
    {
        this.owner = ownerID;
    }

    public Integer getFeedingInterval()
    {
        return feedingInterval;
    }

    public void setFeedingInterval(Integer feedingInterval)
    {
        this.feedingInterval = feedingInterval;
    }

    public LocalDateTime getLastFeeding()
    {
        return lastFeeding;
    }

    public void setLastFeeding(LocalDateTime lastFeeding)
    {
        this.lastFeeding = lastFeeding;
    }

    public Boolean getIndoor()
    {
        return indoor;
    }

    public void setIndoor(Boolean indoor)
    {
        this.indoor = indoor;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
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
        if(LocalDate.now().isAfter(vaccinated.plus(1, ChronoUnit.YEARS)))
            return true;
        else
            return false;
    }

    public List<String> needsList()
    {
        List<String> catNeeds = new ArrayList<>();
        if(needsFeeding())
            catNeeds.add(this.name + " éhes/szomjas lehet. Nézz rá a víz, és ételszintjére");
        if(needsVaccinating())
            catNeeds.add(this.name + " cicusnak esedékes az éves oltás");
        return catNeeds;
    }
}
