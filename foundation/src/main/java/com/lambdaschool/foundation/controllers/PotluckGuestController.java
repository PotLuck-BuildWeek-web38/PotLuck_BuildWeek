package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.services.GuestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "returns all guests for a potluck with the parameter id",
        response = User.class,
        responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Potluck Found",
            response = User.class),
        @ApiResponse(code = 404,
            message = "Potluck Not Found",
            response = ResourceNotFoundException.class)})
    @GetMapping(value = "/{potluckid}/guests")
    public ResponseEntity<?> findAllGuestsByPotluckId(
        @ApiParam(value = "potluck id",
            required = true,
            example = "7")
        @PathVariable long potluckid)
    {

        List<User> guests;

        guests = guestService.findAllGuests(potluckid);

        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @ApiOperation(value = "adds a guest with the parameter user id to a potluck with the parameter potluck id",
        response = Void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "User added to Potluck Found",
            response = Void.class),
        @ApiResponse(code = 404,
            message = "Potluck/User Not Found",
            response = ResourceNotFoundException.class)})
    @PostMapping(value = "/{potluckid}/addguest/{userid}")
    public ResponseEntity<?> addGuestToPotluck(
        @ApiParam(value = "potluck id",
            required = true,
            example = "7")
        @PathVariable long potluckid,
        @ApiParam(value = "user id",
            required = true,
            example = "7")
        @PathVariable long userid)
    {
        User guestAdded = guestService.addGuest(potluckid, userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "removes a guest with the parameter user id from a potluck with the parameter potluck id",
        response = Void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "User removed from Potluck",
            response = Void.class),
        @ApiResponse(code = 404,
            message = "Potluck/User Not Found",
            response = ResourceNotFoundException.class)})
    @DeleteMapping(value = "/{potluckid}/removeguest/{userid}")
    public ResponseEntity<?> removeGuestFromPotluck(
        @ApiParam(value = "potluck id",
            required = true,
            example = "7")
        @PathVariable long potluckid,
        @ApiParam(value = "user id",
            required = true,
            example = "7")
        @PathVariable long userid)
    {
        User guestRemoved = guestService.removeGuest(potluckid, userid);
        System.out.println("Guest removed" + guestRemoved.getUserid() + " " + guestRemoved.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

