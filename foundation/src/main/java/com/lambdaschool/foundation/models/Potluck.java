package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="potlucks")
public class Potluck extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long potluckid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

//   Figure out right data type later, use as string temporarily
    @Column(nullable = false)
    private String date;

  // Figure out right data type later, use as string temporarily
    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String organizer;

    @OneToMany(mappedBy = "potluck",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonIgnoreProperties(value = "potlucks", allowSetters = true)
    private List<User> guests = new ArrayList<>();

    @OneToMany(mappedBy = "potluck",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "potluck", allowSetters = true)
    private List <Item> items = new ArrayList<>();

    public Potluck()
    {
    }

    public Potluck(
        long potluckid,
        String name,
        String location,
        String date,
        String time,
        String organizer,
        List<User> guests,
        List<Item> items)
    {
        this.potluckid = potluckid;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.organizer = organizer;
        this.guests = guests;
        this.items = items;
    }

    public Potluck(
        String name,
        String location,
        String date,
        String time,
        String organizer)
    {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.organizer = organizer;
    }



    public long getPotluckid()
    {
        return potluckid;
    }

    public void setPotluckid(long potluckid)
    {
        this.potluckid = potluckid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }


    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getOrganizer()
    {
        return organizer;
    }

    public void setOrganizer(String organizer)
    {
        this.organizer = organizer;
    }

    public List<User> getGuests()
    {
        return guests;
    }

    public void setGuests(List<User> guests)
    {
        this.guests = guests;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }
}
