package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item findById(long id);

    void delete(long id);


    Item update ( long itemid, Item item);

}
