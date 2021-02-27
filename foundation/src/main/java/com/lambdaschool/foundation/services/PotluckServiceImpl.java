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

//            User newOrganizer = userrepos.findById(potluck.getOrganizer().getUsername())
//                    .orElseThrow(() -> new ResourceNotFoundException(("Organizer id " + potluck.getOrganizer()
//                            .getUsername() + " not found!")));
            newPotluck.setOrganizer(potluck.getOrganizer());

//            newPotluck.getGuests()
//                    .clear();
//            for (PotluckUsers pg : potluck.getGuests()) {
//                if (potluck.getGuests().size() > 0) {
//                    User newGuest = userrepos.findById(pg.getGuest()
//                            .getUserid())
//                            .orElseThrow(() -> new ResourceNotFoundException("User/Guest id " + pg.getGuest()
//                                    .getUserid() + " not found!"));
//
//                    newPotluck.getGuests()
//                            .add(new PotluckUsers(newGuest,
//                                    newPotluck));
//                }
//            }
//
//            newPotluck.getItems()
//                    .clear();
//            for (PotluckItems pi : potluck.getItems()) {
//                if (potluck.getItems().size() > 0) {
//                    Item newItem = itemrepos.findById(pi.getItem()
//                            .getItemid())
//                            .orElseThrow(() -> new ResourceNotFoundException("Item id " + pi.getItem()
//                                    .getItemid() + " not found!"));
//                    newPotluck.getItems()
//                            .add(new PotluckItems(newItem,
//                                    newPotluck));
//                }
//            }

            return potluckrepos.save(newPotluck);

        }
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void deleteAll() {

    }
}
