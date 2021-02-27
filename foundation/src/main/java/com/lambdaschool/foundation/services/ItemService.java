package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item findItemById(long id);

    void delete(long id);

    Item save (Item item);

    Item update ( long itemid, Item item);

}
