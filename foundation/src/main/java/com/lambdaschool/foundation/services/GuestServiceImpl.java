package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.repository.PotluckRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
@Service(value = "guestService")
public class GuestServiceImpl implements GuestService
{

    @Autowired
    private PotluckRepository potluckrepos;

    @Autowired
    private UserRepository userrepos;

    @Override
    public List<User> findAllGuests(long id)
    {
        Potluck currentPotluck = potluckrepos.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(("Potluck id " + id + " Not Found")));
        //List<User> guests = currentPotluck.getGuests();
        return null;
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
        //currentPotluck.getGuests().add(guestToAdd);
        potluckrepos.save(currentPotluck);
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
        //List<User> guests = currentPotluck.getGuests();
//        List<User> updatedGuests = new ArrayList<>();
//        for (User g : guests)
//        {
//           if (g.getUsername() != guestToRemove.getUsername())
//           {
//               updatedGuests.add(g);
//           }
//
//        }
//        currentPotluck.setGuests(updatedGuests);
        potluckrepos.save(currentPotluck);

        return guestToRemove;
    }
}
