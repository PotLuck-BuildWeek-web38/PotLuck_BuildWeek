package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.services.PotluckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "potlucks")
public class PotluckController
{
    @Autowired
    private PotluckService potluckService;

    // Return a list of all potlucks
    @GetMapping(value = "/potlucks", produces ={"application/json"})
    public ResponseEntity<?> listAllPotlucks()
    {
        List<Potluck> allPotlucks = potluckService.findAll();
        return new ResponseEntity<>(allPotlucks, HttpStatus.OK);
    }

    // Return a potluck by id
    @GetMapping(value = "/potluck/{potluckid}", produces = {"application/json"})
    public ResponseEntity<?> getPotluckById(@PathVariable long potluckid)
    {
        Potluck potluck = potluckService.findPotluckById(potluckid);
        return new ResponseEntity<>(potluck, HttpStatus.OK);
    }

    // Create a new potluck
    @PostMapping(value = "/potluck",
        consumes = {"application/json"})
    public ResponseEntity<?> addPotluck(
        @Valid
        @RequestBody
            Potluck newpotluck) throws
                                URISyntaxException
    {
        newpotluck.setPotluckid(0);
        newpotluck = potluckService.save(newpotluck);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPotluckURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{potluckid}")
            .buildAndExpand(newpotluck.getPotluckid())
            .toUri();
        responseHeaders.setLocation(newPotluckURI);

        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }

    // update a complete potluck object
    @PutMapping(value = "/potluck/{potluckid}",
        consumes = {"application/json"})
    public ResponseEntity<?> updateFullPotluck(
        @Valid
        @RequestBody
            Potluck updatePotluck,
        @PathVariable
            long potluckid)
    {
        updatePotluck.setPotluckid(potluckid);
        potluckService.save(updatePotluck);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/potluck/{potluckid}")
    public ResponseEntity<?> deletePotluckById(
        @PathVariable
            long id)
    {
        potluckService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
