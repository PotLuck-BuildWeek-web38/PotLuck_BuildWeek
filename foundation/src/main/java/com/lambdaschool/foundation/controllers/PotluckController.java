package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.ErrorDetail;
import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.services.PotluckService;
import com.lambdaschool.foundation.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "potlucks")
public class PotluckController
{
    @Autowired
    private PotluckService potluckService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "returns all Potlucks",
        response = Potluck.class,
        responseContainer = "List")
    @GetMapping(value = "/potlucks", produces ={"application/json"})
    public ResponseEntity<?> listAllPotlucks()
    {
        List<Potluck> allPotlucks = potluckService.findAll();
        return new ResponseEntity<>(allPotlucks, HttpStatus.OK);
    }

    @ApiOperation(value = "returns a potluck from the path parameter potluck id",
        response = Potluck.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
        message = "Potluck Found",
        response = Potluck.class),
        @ApiResponse(code = 404,
        message = "Potluck Not Found",
        response = ResourceNotFoundException.class)})
    @GetMapping(value = "/potluck/{potluckid}", produces = {"application/json"})
    public ResponseEntity<?> getPotluckById(
        @ApiParam(value = "potluck id",
            required = true,
            example = "7")
        @PathVariable long potluckid)
    {
        Potluck potluck = potluckService.findPotluckById(potluckid);
        return new ResponseEntity<>(potluck, HttpStatus.OK);
    }

    @ApiOperation(value = "creates a potluck from data given in the request body",
        response = Void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201,
        message = "Potluck Created",
        response = Void.class),
        @ApiResponse(code = 400,
        message = "Bad Request",
        response = ErrorDetail.class)})
    @PostMapping(value = "/potluck",
            consumes = {"application/json"}, produces ={"application/json"})
    public ResponseEntity<?> addPotluck(
            @ApiParam(value = "a full potluck object",
            required = true)
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

    @ApiOperation(value = "updates a potluck from data given in the request body and the potluck parameter id",
        response = Void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
        message = "Potluck Updated",
        response = Void.class),
        @ApiResponse(code = 404,
        message = "Potluck not Found",
        response = ResourceNotFoundException.class),
        @ApiResponse(code = 400,
        message = "Bad Request",
        response = ErrorDetail.class)})
    @PutMapping (value = "/potluck/{potluckid}",
        consumes = {"application/json"}, produces ={"application/json"})
    public ResponseEntity<?> updateFullPotluck(
        @ApiParam(value = "a full potluck object",
            required = true)
        @Valid
        @RequestBody
            Potluck updatePotluck,
        @ApiParam(value = "Potluck id",
            required = true,
            example = "7")
        @PathVariable
            long potluckid)
    {
        updatePotluck.setPotluckid(potluckid);
        potluckService.update(updatePotluck, potluckid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "deletes a potluck from a potluck id",
        response = Void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Potluck Found"),
        @ApiResponse(code = 404,
            message = "Potluck Not Found",
            response = ResourceNotFoundException.class)})
    @DeleteMapping(value = "/potluck/{potluckid}")
    public ResponseEntity<?> deletePotluckById(
        @ApiParam(value = "Potluck id",
            required = true,
            example = "7")
        @PathVariable
            long potluckid)
    {
        potluckService.delete(potluckid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "returns all potlucks organized by the currently authenticated user",
        response = Potluck.class,
        responseContainer = "List")
    @GetMapping(value = "/getpotluckinfo",
        produces = {"application/json"})
    public ResponseEntity<?> getPotluckInfo(Authentication authentication)
    {
        List<Potluck> userPotlucks = new ArrayList<>();
        String organizer = authentication.getName();
        userPotlucks = potluckService.findPotlucksByOrganizer(organizer);
        return new ResponseEntity<>(userPotlucks,
            HttpStatus.OK);
    }
}
