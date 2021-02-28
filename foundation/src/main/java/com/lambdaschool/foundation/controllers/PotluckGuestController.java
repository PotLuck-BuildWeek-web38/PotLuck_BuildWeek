package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("potlucks/potluck")
public class PotluckGuestController
{
    @Autowired
    private GuestService guestService;

    // return all guests at coming to a potluck
    @GetMapping(value = "/{potluckid}/guests")
    public ResponseEntity<?> findAllGuestsByPotluckId(@PathVariable long potluckid)
    {

        List<User> guests;

        guests = guestService.findAllGuests(potluckid);

        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @PostMapping(value = "/{potluckid}/addguest/{userid}", consumes = {"application/json"})
    public ResponseEntity<?> addGuestToPotluck(
        @PathVariable long potluckid, @PathVariable long userid)
    {
        User guestAdded = guestService.addGuest(potluckid, userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{potluckid}/removeguest/{userid}", consumes = {"application/json"})
    public ResponseEntity<?> removeGuestFromPotluck( @PathVariable long potluckid, @PathVariable long userid)
    {
        User guestRemoved = guestService.removeGuest(potluckid, userid);
        System.out.println("Guest removed" + guestRemoved.getUserid() + " " + guestRemoved.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

