package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Item;
import com.lambdaschool.foundation.repository.ItemRepository;
import com.lambdaschool.foundation.repository.PotluckRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("itemService")
public class ItemServiceImpl implements ItemService{

    @Autowired
    ItemRepository itemrepos;

    @Autowired
    PotluckRepository potluckrepos;

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Item> findAll(){
        List<Item> list = new ArrayList<>();

        itemrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Item findById(long id){
        return itemrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item id " + id + " not found!"));
    }


    @Override
    public void delete(long id) {
        if (itemrepos.findById(id).isPresent()) {
            itemrepos.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Item id " + id + " not found!");
        }
    }
    @Transactional
    @Override
    public Item update(long itemid, Item item) {
       Item currentItem = itemrepos.findById(itemid)
               .orElseThrow(() -> new ResourceNotFoundException("Item " + itemid + " not found"));
        if (item.getName() != null)
        {
            currentItem.setName(item.getName());
        }

        if (item.getGuest() != null)
        {
            currentItem.setGuest(item.getGuest());
        }

        if (item.getPicked() != null)
        {
            currentItem.setPicked(item.getPicked());
        }
       return itemrepos.save(currentItem);
    }


}