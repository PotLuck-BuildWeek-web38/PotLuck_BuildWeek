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
    @JoinColumn(name = "userid",
        nullable = false)
    @JsonIgnoreProperties(value = "items", allowSetters = true)
    private User guest;

    @OneToMany(mappedBy = "item",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "item",
        allowSetters = true)
    private Set<PotluckItems> items = new HashSet<>();


    public Item()
    {
    }

    public Item(
        String name,
        User guest)
    {
        this.name = name;
        this.guest = guest;
    }

    public Item(
        String name,
        User guest,
        Set<PotluckItems> items)
    {
        this.name = name;
        this.guest = guest;
        this.items = items;
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

    public Set<PotluckItems> getItems()
    {
        return items;
    }

    public void setItems(Set<PotluckItems> items)
    {
        this.items = items;
    }
}
