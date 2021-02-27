package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemid;

    @Column(nullable = false)
    private String name;


    @ManyToOne
    @JoinColumn(name = "potluckid",
        nullable = false)
    @JsonIgnoreProperties(value = "items", allowSetters = true)
    private Potluck potluck;

    @ManyToOne
    @JoinColumn(name = "userid",
        nullable = false)
    @JsonIgnoreProperties(value = "potlucks", allowSetters = true)
    private User guest;

    public Item()
    {
    }


    public Item(
        String name,
        User guest
        )
    {
        this.name = name;
        this.guest = guest;
    }

    public long getItemid()
    {
        return itemid;
    }

    public void setItemid(long itemid)
    {
        this.itemid = itemid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public User getGuest()
    {
        return guest;
    }

    public void setGuest(User guest)
    {
        this.guest = guest;
    }


}
