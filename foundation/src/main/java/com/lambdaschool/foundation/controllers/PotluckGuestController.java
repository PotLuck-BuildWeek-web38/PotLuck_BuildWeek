package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.services.GuestService;
import com.lambdaschool.foundation.services.PotluckService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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

        List<User> guests = new ArrayList<>();

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

    @DeleteMapping(value = "/{potluckid}/deleteguest/{userid}", consumes = {"application/json"})
    public ResponseEntity<?> deleteGuestFromPotluck( @PathVariable long potluckid, @PathVariable long userid)
    {
        User guestRemoved = guestService.removeGuest(potluckid, userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

