package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The entity allowing interaction with the studcourses table.
 * The join table between course and students
 */
@Entity
@Table(name = "userpotlucks")
@IdClass(UserPotlucksId.class)
public class UserPotlucks
    extends Auditable
    implements Serializable
{
    /**
     * Foreign key into the potluck table
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "potluckid")
    @JsonIgnoreProperties("users")
    private Potluck potluck;

    /**
     * Foreign key into the user table
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("potlucks")
    private User user;

    public UserPotlucks()
    {
    }

    /**
     * Given the params, create a new course student combination
     *
     * @param potluck  The potluck object of the potluck user combination
     * @param user The user object of the potluck user combination
     */
    public UserPotlucks(
        Potluck potluck,
        User user)
    {
        this.potluck = potluck;
        this.user = user;
    }

    /**
     * The getter for course
     *
     * @return the complete course object of this course student combination
     */
    public Potluck getPotluck()
    {
        return potluck;
    }

    /**
     * The setter for potluck
     *
     * @param potluck change the potluck object associated with this course student combination to this one.
     */
    public void setCourse(Potluck potluck)
    {
        this.potluck = potluck;
    }

    /**
     * The getter for student
     *
     * @return the complete student object of this course student combination
     */
    public User getUser()
    {
        return user;
    }

    /**
     * The setter for User
     *
     * @param user change the student object associated with this course student combination to this one.
     */
    public void setUser(User user)
    {
        this.user = user;
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
        UserPotlucks that = (UserPotlucks) o;
        return ((this.user == null) ? 0 : this.user.getUserid()) == ((that.user == null) ? 0 : that.user.getUserid()) &&
            ((this.potluck == null) ? 0 : this.potluck.getPotluckid()) == ((that.potluck == null) ? 0 : that.potluck.getPotluckid());
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
