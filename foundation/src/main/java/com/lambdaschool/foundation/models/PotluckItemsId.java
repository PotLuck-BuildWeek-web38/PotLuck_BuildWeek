package com.lambdaschool.foundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Class to represent the complex primary key for UserRoles
 */
@Embeddable
public class PotluckItemsId
    implements Serializable
{
    /**
     * The id of the potluck
     */
    private long potluck;

    /**
     * The id of the item
     */
    private long item;

    public PotluckItemsId()
    {
    }

    public long getPotluck()
    {
        return potluck;
    }

    public void setPotluck(long potluck)
    {
        this.potluck = potluck;
    }

    public long getItem()
    {
        return item;
    }

    public void setItem(long item)
    {
        this.item = item;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        PotluckItemsId that = (PotluckItemsId) o;
        return item == that.item &&
            potluck == that.potluck;
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
