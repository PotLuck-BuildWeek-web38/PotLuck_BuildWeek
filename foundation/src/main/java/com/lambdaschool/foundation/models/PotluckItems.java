package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "potluckitems")
@IdClass(PotluckItemsId.class)
public class PotluckItems
    extends Auditable
    implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "itemid")
    @JsonIgnoreProperties(value = "potlucks",
        allowSetters = true)
    private Item item;


    @Id
    @ManyToOne
    @JoinColumn(name = "potluckid")
    @JsonIgnoreProperties(value = "items",
        allowSetters = true)
    private Potluck potluck;

    public PotluckItems()
    {
    }

    public PotluckItems(
        Item item,
        Potluck potluck)
    {
        this.item = item;
        this.potluck = potluck;
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public Potluck getPotluck()
    {
        return potluck;
    }

    public void setPotluck(Potluck potluck)
    {
        this.potluck = potluck;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof PotluckItems))
        {
            return false;
        }
        PotluckItems that = (PotluckItems) o;
        return ((item == null) ? 0 : item.getItemid()) == ((that.item == null) ? 0 : that.item.getItemid()) &&
            ((potluck == null) ? 0 : potluck.getPotluckid()) == ((that.potluck == null) ? 0 : that.potluck.getPotluckid());
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
