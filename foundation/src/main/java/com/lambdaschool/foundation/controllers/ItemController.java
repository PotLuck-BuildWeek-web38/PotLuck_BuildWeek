package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Item;
import com.lambdaschool.foundation.services.ItemService;
import com.lambdaschool.foundation.services.PotluckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/potlucks")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    PotluckService potluckService;

    @GetMapping(value = "/item",
            produces = "application/json")
    public ResponseEntity<?> listItem()
    {
        List<Item> allRoles = itemService.findAll();
        return new ResponseEntity<>(allRoles,
                HttpStatus.OK);
    }

    @GetMapping(value = "/item/{itemId}",
            produces = "application/json")
    public ResponseEntity<?> getItemById(
            @PathVariable
                    Long itemId)
    {
        Item r = itemService.findById(itemId);
        return new ResponseEntity<>(r,
                HttpStatus.OK);
    }

//    @GetMapping(value = "/item/name/{itemName}",
//            produces = "application/json")
//    public ResponseEntity<?> getItemByName(
//            @PathVariable
//                    String itemName)
//    {
//        Item r = itemService.findByName(itemName);
//        return new ResponseEntity<>(r,
//                HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/{potluckid}/additem",
//            consumes = "application/json")
//    public ResponseEntity<?> addNewItem(@PathVariable long potluckid)throws
//            URISyntaxException
//    {
//        Item i = potluckService.save(potluckid,);
//
//    }

}
