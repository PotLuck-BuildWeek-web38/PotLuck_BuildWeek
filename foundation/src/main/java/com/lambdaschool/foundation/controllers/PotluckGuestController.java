package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.services.GuestService;
import com.lambdaschool.foundation.services.PotluckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("potlucks/potluck")
public class PotluckGuestController
{
    @Autowired
    private GuestService guestService;

    @Autowired
    private PotluckService potluckService;

    // return all guests at coming to a potluck
    @GetMapping(value = "/{potluckid}/guests")
    public ResponseEntity<?> findAllGuestsByPotluckId(@PathVariable long potluckid)
    {
        Potluck currentPotluck = potluckService.findPotluckById(potluckid);

        List<User> guests = new ArrayList<>();

        guests = currentPotluck.getGuests();
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }


}

