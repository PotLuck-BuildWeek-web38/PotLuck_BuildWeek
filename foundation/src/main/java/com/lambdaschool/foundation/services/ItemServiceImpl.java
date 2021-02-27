package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Item;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("itemService")
public class ItemServiceImpl implements ItemService{
    @Override
    public List<Item> findAll(){return null;}

    @Override
    public Item findItemById(long id){return null;}

    @Override
    public void delete(long id) {

    }

    @Override
    public Item save(Item item) {
        return null;
    }

    @Override
    public Item update(long itemid, Item item) {
        return null;
    }
}
