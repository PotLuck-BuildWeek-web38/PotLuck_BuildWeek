package com.lambdaschool.foundation.models;


import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserPotlucksId implements Serializable
{
    /**
     * The id of the user
     */
    private long user;

    /**
     * The id of the potluck
     */
    private long potluck;



    /**
     * The default constructor
     */
    public UserPotlucksId()
    {
    }


    public long getUser()
    {
        return user;
    }

    public void setUser(long user)
    {
        this.user = user;
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
        UserPotlucksId that = (UserPotlucksId) o;
        return user == that.user &&
            potluck == that.potluck;
    }

    @Override
    public int hashCode()
    {
        return 37;
    }

}
