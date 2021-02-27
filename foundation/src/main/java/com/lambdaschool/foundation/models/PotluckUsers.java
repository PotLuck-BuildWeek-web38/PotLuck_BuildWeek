package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "potluckusers")
@IdClass(PotluckUsersId.class)
public class PotluckUsers
    extends Auditable
    implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "potlucks",
        allowSetters = true)
    private User guest;


    @Id
    @ManyToOne
    @JoinColumn(name = "potluckid")
    @JsonIgnoreProperties(value = "guests",
        allowSetters = true)
    private Potluck potluck;

    /**
     * Default constructor used primarily by the JPA.
     */
    public PotluckUsers()
    {
    }


    public PotluckUsers(
        User guest,
        Potluck potluck)
    {
        this.guest = guest;
        this.potluck = potluck;
    }

    public User getGuest()
    {
        return guest;
    }

    public void setGuest(User guest)
    {
        this.guest = guest;
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
        if (!(o instanceof PotluckUsers))
        {
            return false;
        }
        PotluckUsers that = (PotluckUsers) o;
        return ((guest == null) ? 0 : guest.getUserid()) == ((that.guest == null) ? 0 : that.guest.getUserid()) &&
            ((potluck == null) ? 0 : potluck.getPotluckid()) == ((that.potluck == null) ? 0 : that.potluck.getPotluckid());
    }

    @Override
    public int hashCode()
    {
        // return Objects.hash(guest.getUserid(), potluck.getPotluckid());
        return 37;
    }
}
