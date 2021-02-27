package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;

import java.util.List;

public interface GuestService
{
    List<User> findAllGuests(long id);

    User addGuest(long potluckid, long userid);

    User removeGuest(long potluckid, long userid);
}
