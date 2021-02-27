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

    private String guest;

    @Column(nullable = false)
    private Boolean picked;

    @ManyToOne
    @JoinColumn(name = "potluckid",
        nullable = false)
    @JsonIgnoreProperties(value = "items", allowSetters = true)
    private Potluck potluck;


    public Item()
    {
    }

    public Item(
        String name,
        String guest,
        Boolean picked,
        Potluck potluck)
    {
        this.name = name;
        this.guest = guest;
        this.picked = picked;
        this.potluck = potluck;
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

    public String getGuest()
    {
        return guest;
    }

    public void setGuest(String guest)
    {
        this.guest = guest;
    }

    public Boolean getPicked() {
        return picked;
    }

    public void setPicked(Boolean picked) {
        this.picked = picked;
    }

    public Potluck getPotluck() {
        return potluck;
    }

    public void setPotluck(Potluck potluck) {
        this.potluck = potluck;
    }

}