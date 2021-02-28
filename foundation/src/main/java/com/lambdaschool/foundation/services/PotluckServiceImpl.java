package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.repository.ItemRepository;
import com.lambdaschool.foundation.repository.PotluckRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "potluckService")
public class PotluckServiceImpl implements PotluckService
{
    @Autowired
    private PotluckRepository potluckrepos;

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private ItemRepository itemrepos;

    @Autowired
    private UserService userService;

    @Override
    public List<Potluck> findAll()
    {
        List<Potluck> list = new ArrayList<>();
        potluckrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Potluck findPotluckById(long id)throws
                                           ResourceNotFoundException
    {
        {
            return potluckrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Potluck id " + id + " not found!"));
        }

    }

    @Override
    public Potluck save(Potluck potluck) {
        {

            Potluck newPotluck = new Potluck();

            if (potluck.getPotluckid() != 0) {
                Potluck currentPotluck = potluckrepos.findById(potluck.getPotluckid())
                        .orElseThrow(() -> new ResourceNotFoundException("Potluck id " + potluck.getPotluckid() + " not found!"));

                newPotluck.setPotluckid(potluck.getPotluckid());
            }

            newPotluck.setName(potluck.getName());
            newPotluck.setDate(potluck.getDate());
            newPotluck.setTime(potluck.getTime());
            newPotluck.setLocation(potluck.getLocation());
            newPotluck.setOrganizer(potluck.getOrganizer());

            newPotluck.getUsers()
                .clear();
            for (UserPotlucks up : potluck.getUsers())
            {
                User addUser = userService.findUserById(up.getUser()
                    .getUserid());
                newPotluck.getUsers()
                    .add(new UserPotlucks(newPotluck,
                        addUser));
            }
           newPotluck.getItems()
                    .clear();
            for (Item pi : potluck.getItems()) {
                if (potluck.getItems().size() > 0) {
                    newPotluck.getItems().add(new Item(pi.getName(), pi.getGuest(), pi.getPicked(), newPotluck));
                }
            }

            return potluckrepos.save(newPotluck);

        }
    }
    @Transactional
    @Override
    public Potluck update(
            Potluck potluck,
            long id)
    {
        Potluck currentPotluck = potluckrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant " + id + " not found"));

        if (potluck.getName() != null)
        {
            currentPotluck.setName(potluck.getName());
        }

        if (potluck.getLocation() != null)
        {
            currentPotluck.setLocation(potluck.getLocation());
        }

        if (potluck.getDate() != null)
        {
            currentPotluck.setDate(potluck.getDate());
        }

        if (potluck.getTime() != null)
        {
            currentPotluck.setTime(potluck.getTime());
        }

        if (potluck.getOrganizer() != null)
        {
            currentPotluck.setOrganizer(potluck.getOrganizer());
        }

        if (potluck.getUsers()
                .size() > 0) {
            for (UserPotlucks up : potluck.getUsers()) {
                User addUser = userService.findUserById(up.getUser()
                        .getUserid());
                currentPotluck.getUsers()
                        .add(new UserPotlucks(currentPotluck,
                                addUser));
            }
        }

        System.out.println("*** " + potluck.getItems()
                .size());
        System.out.println(potluck.getItems());

        if (potluck.getItems()
                .size() > 0)
        {

            for (Item i : potluck.getItems())
            {
                currentPotluck.getItems()
                        .add(new Item(i.getName(),i.getGuest(),false,currentPotluck));
            }
        }

        return potluckrepos.save(currentPotluck);
    }
    @Override
    public void delete(long id) {

    }

    @Override
    public void deleteAll() {

    }
}
