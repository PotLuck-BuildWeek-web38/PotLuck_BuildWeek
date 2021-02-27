package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
