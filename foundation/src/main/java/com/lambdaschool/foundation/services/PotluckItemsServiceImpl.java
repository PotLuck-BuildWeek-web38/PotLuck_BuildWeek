package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.PotluckItems;
import com.lambdaschool.foundation.repository.PotluckItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value= "potLuckItemsService")
public class PotluckItemsServiceImpl implements PotluckItemsService {

    @Autowired
    PotluckItemsRepository potluckItemsRepository;

    @Autowired

    @Override
    public List<PotluckItems> findAll() {
        List<PotluckItems> list = new ArrayList<>();
        potluckItemsRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public PotluckItems findPotLuckItemsByID(long id) {
        return  potluckItemsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
    }

    @Transactional
    @Override
    public PotluckItems save(PotluckItems potluckItems) {
        if (potluckItems.getItem() == null){
            throw new ResourceNotFoundException("Items are not updated trough  potluckItems");
        }
        return potluckItemsRepository.save(potluckItems);
    }

    @Override
    public PotluckItems findByName(String name) {
        return null;
    }


    @Override
    public PotluckItems update(long id, PotluckItems potluckItems) {
        return null;
    }

    @Override
    public void delete(long id) {
        if (potluckItemsRepository.findById(id).isPresent()) {
            potluckItemsRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Id not find : " + id);
        }
    }
}
