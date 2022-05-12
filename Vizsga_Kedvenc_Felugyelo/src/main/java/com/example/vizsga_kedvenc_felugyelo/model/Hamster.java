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
@Table(name = "hamster")
public class Hamster extends Animal implements HamsterInterface
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "ownerID", nullable = false)
    private Integer owner;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm", timezone="Europe/Berlin")
    @Column(name = "lastFeeding", nullable = false)
    private LocalDateTime lastFeeding;

    @Column(name = "feedingInterval", nullable = false)
    private Integer feedingInterval;

    @Column(name = "lastCleaning", nullable = false)
    private LocalDate lastCleaning;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "toothwearerChanged", nullable = false)
    private LocalDate toothwearerChanged;

    public Hamster(Integer owner, LocalDateTime lastFeeding, Integer feedingInterval, LocalDate lastCleaning, String name, LocalDate toothwearerChanged)
    {
        this.owner = owner;
        this.lastFeeding = lastFeeding;
        this.feedingInterval = feedingInterval;
        this.lastCleaning = lastCleaning;
        this.name = name;
        this.toothwearerChanged = toothwearerChanged;
    }

    public Hamster()
    {

    }

    public Integer getOwner()
    {
        return owner;
    }

    public void setOwner(Integer owner)
    {
        this.owner = owner;
    }

    public LocalDate getToothwearerChanged()
    {
        return toothwearerChanged;
    }

    public void setToothwearerChanged(LocalDate toothwearerChanged)
    {
        this.toothwearerChanged = toothwearerChanged;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getLastCleaning()
    {
        return lastCleaning;
    }

    public void setLastCleaning(LocalDate lastCleaning)
    {
        this.lastCleaning = lastCleaning;
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

    public Integer getUser()
    {
        return owner;
    }

    public void setUser(Integer user)
    {
        this.owner = user;
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

    public Boolean changeToothWearer()
    {
        if(LocalDate.now().isAfter(toothwearerChanged.plusWeeks(6)) || LocalDate.now().isEqual(toothwearerChanged.plusMonths(6)))
            return true;
        else
            return false;
    }

    public Boolean needsCleaning()
    {
        if(LocalDate.now().isAfter(lastCleaning.plusWeeks(2)) || LocalDate.now().isEqual(lastCleaning.plusWeeks(2)))
            return true;
        else
            return false;
    }

    public List<String> needsList()
    {
        List<String> dogNeeds = new ArrayList<>();
        if(needsFeeding())
            dogNeeds.add(this.name + " éhes/szomjas lehet. Nézz rá a víz, és ételszintjére");
        if(needsCleaning())
            dogNeeds.add(this.name + " hörid alomja egy ideje nem volt kitakarítva");
        if(this.changeToothWearer())
            dogNeeds.add(this.name + " hörid fogkoptatója kezd elöregedni");
        return dogNeeds;
    }
}