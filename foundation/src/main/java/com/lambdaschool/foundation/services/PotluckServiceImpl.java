package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.repository.PotluckRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "potluckService")
public class PotluckServiceImpl implements PotluckService
{
    @Autowired
    private PotluckRepository potluckrepos;


    @Override
    public List<Potluck> findAll()
    {
        List<Potluck> list = new ArrayList<>();
        potluckrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Potluck findPotluckById(long id)throws
                                           ResourceNotFoundException
    {
        {
            return potluckrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Potluck id " + id + " not found!"));
        }

    }

    @Transactional
    @Override
    public Potluck save(Potluck potluck)
    {
        return null;
    }

    @Transactional
    @Override
    public void delete(long id) throws ResourceNotFoundException
    {
        potluckrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Potluck id " + id + " not found!"));
        potluckrepos.deleteById(id);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll()
    {
        potluckrepos.deleteAll();
    }
}
