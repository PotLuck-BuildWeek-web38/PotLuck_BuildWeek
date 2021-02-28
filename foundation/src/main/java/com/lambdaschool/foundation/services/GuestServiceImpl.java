package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.models.UserPotlucks;
import com.lambdaschool.foundation.repository.PotluckRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service(value = "guestService")
public class GuestServiceImpl implements GuestService
{
    @Autowired
    private PotluckService potluckService;

    @Autowired
    private UserService userService;

    @Autowired
    private PotluckRepository potluckrepos;

    @Autowired
    private UserRepository userrepos;

    @Override
    public List<User> findAllGuests(long id)
    {
        Potluck currentPotluck = potluckrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(("Potluck id " + id + " Not Found")));
        List<User> guests = new ArrayList<>();
        for (UserPotlucks up : currentPotluck.getUsers())
        {
            User guest = userService.findUserById(up.getUser()
                .getUserid());
            guests.add(guest);
        }
        return guests;
    }

    @Override
    public User addGuest(
        long potluckid,
        long userid)
    {
        Potluck currentPotluck = potluckrepos.findById(potluckid)
            .orElseThrow(() -> new ResourceNotFoundException(("Potluck id " + potluckid + " Not Found")));
        User guestToAdd = userrepos.findById(userid)
            .orElseThrow(() -> new ResourceNotFoundException(("User id " + userid + " Not Found")));
        currentPotluck.getUsers().add(new UserPotlucks( currentPotluck, guestToAdd));
        potluckService.save(currentPotluck);
        return guestToAdd;
    }

    @Override
    public User removeGuest(
        long potluckid,
        long userid)
    {
        Potluck currentPotluck = potluckrepos.findById(potluckid)
            .orElseThrow(() -> new ResourceNotFoundException(("Potluck id " + potluckid + " Not Found")));
        User guestToRemove = userrepos.findById(userid)
            .orElseThrow(() -> new ResourceNotFoundException(("User id " + userid + " Not Found")));


        for (UserPotlucks up : currentPotluck.getUsers())
        {
            User checkUser = userService.findUserById(up.getUser()
                .getUserid());

            if (checkUser.getUserid() == guestToRemove.getUserid())
            {
                System.out.println(checkUser.getUserid() +" " + guestToRemove.getUserid());
                currentPotluck.getUsers().remove(up);
            }

        }

        potluckService.save(currentPotluck);

        return guestToRemove;
    }
}