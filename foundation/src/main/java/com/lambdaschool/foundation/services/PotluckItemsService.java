package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.PotluckItems;

import java.util.List;

public interface PotluckItemsService {
    List<PotluckItems> findAll();

    PotluckItems findPotLuckItemsByID(long id);

    PotluckItems save (PotluckItems potluckItems);

    PotluckItems findByName(String name);

    PotluckItems update(long id, PotluckItems potluckItems);

    void delete(long id);

}
