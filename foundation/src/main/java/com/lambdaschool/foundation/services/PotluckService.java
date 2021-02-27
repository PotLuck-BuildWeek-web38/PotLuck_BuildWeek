package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;

import java.util.List;

public interface PotluckService
{
    List<Potluck> findAll();

    Potluck findPotluckById(long id);

    Potluck save(Potluck potluck);

    void delete(long id);

    public void deleteAll();
}
