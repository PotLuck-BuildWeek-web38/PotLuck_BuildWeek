package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Item;
import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.services.ItemService;
import com.lambdaschool.foundation.services.PotluckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping(value = "/item/{itemid}",
            produces = "application/json")
    public ResponseEntity<?> getItemById(
            @PathVariable
                    Long itemid)
    {
        Item r = itemService.findById(itemid);
        return new ResponseEntity<>(r,
                HttpStatus.OK);
    }


    @PatchMapping(value = "/updateitem/{itemid}",
            consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> updateItem( @Valid
          @RequestBody Item updateItem, @PathVariable  long itemid)
    {
        updateItem.setItemid(itemid);
        itemService.update(itemid,updateItem);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/item/{itemid}")
    public ResponseEntity<?> deleteItemById(
            @PathVariable
                    long itemid)
    {
        itemService.delete(itemid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
