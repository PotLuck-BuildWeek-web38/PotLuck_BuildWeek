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

//    @Override
//    public User addGuest(
//            long potluckid,
//            long userid)
//    {
//        Potluck currentPotluck = potluckrepos.findById(potluckid)
//                .orElseThrow(() -> new ResourceNotFoundException(("Potluck id " + potluckid + " Not Found")));
//        User guestToAdd = userrepos.findById(userid)
//                .orElseThrow(() -> new ResourceNotFoundException(("User id " + userid + " Not Found")));
//        //currentPotluck.getGuests().add(guestToAdd);
//        potluckrepos.save(currentPotluck);
//        return guestToAdd;
//    }


    @Override
    public void delete(long id) {

    }
    @Transactional
    @Override
    public Item save(Item item) {
//
//        Potluck curPotLuck = potluckrepos.findById(potluckid).orElseThrow(() -> new ResourceNotFoundException(("Potluck id: " + potluckid + " Not Found")));
//
//        Item newItem = itemrepos.findById(itemid)
//                .orElseThrow(() -> new ResourceNotFoundException(("Item id : " + itemid + " Not Found")));
//        curPotLuck.getItems().add(newItem);
//        potluckrepos.save(curPotLuck);
//        return newItem;

        Item newItem = new Item();

        if(item.getItemid() != 0){
            itemrepos.findById(item.getItemid())
                    .orElseThrow(() -> new ResourceNotFoundException("Item id " + item.getItemid() + " not found!"));
            newItem.setItemid(item.getItemid());
        }
        newItem.setName(item.getName());
        newItem.setGuest(item.getGuest());
        newItem.setPicked(item.getPicked());

        return itemrepos.save(newItem);
    }

    @Override
    public Item update(long itemid, Item item) {
        return null;
    }

//    @Override
//    public Item update(long itemid, Item item) {
//
//        if (item.getName() == null)
//        {
//            throw new ResourceNotFoundException("No item name found to update!");
//        }
//        Item newItem = findById(itemid); // see if id exists
//
//        itemrepos.updateItemName(userAuditing.getCurrentAuditor()
//                        .get(),
//                itemid,
//                item.getName());
//        return findById(itemid);
//    }
}