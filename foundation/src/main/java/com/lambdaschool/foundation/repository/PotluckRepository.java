package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PotluckRepository  extends CrudRepository<Potluck, Long>
{

    List<Potluck> findByOrganizer(String organizer);
}
