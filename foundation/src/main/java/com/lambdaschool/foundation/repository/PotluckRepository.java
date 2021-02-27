package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Potluck;
import org.springframework.data.repository.CrudRepository;

public interface PotluckRepository  extends CrudRepository<Potluck, Long>
{
}
