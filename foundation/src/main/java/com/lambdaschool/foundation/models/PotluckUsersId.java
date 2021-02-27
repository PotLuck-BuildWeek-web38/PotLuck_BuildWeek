package com.lambdaschool.foundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Class to represent the complex primary key for UserRoles
 */
@Embeddable
public class PotluckUsersId
    implements Serializable
{
    /**
     * The id of the user
     */
    private long guest;

    /**
     * The id of the role
     */
    private long potluck;

    /**
     * The default constructor required by JPA
     */
    public PotluckUsersId()
    {
    }

    public long getGuest()
    {
        return guest;
    }

    public void setGuest(long guest)
    {
        this.guest = guest;
    }

    public long getPotluck()
    {
        return potluck;
    }

    public void setPotluck(long potluck)
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
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        PotluckUsersId that = (PotluckUsersId) o;
        return guest == that.guest &&
            potluck == that.potluck;
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
