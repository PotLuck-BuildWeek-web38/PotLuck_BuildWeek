package com.lambdaschool.foundation;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.services.PotluckService;
import com.lambdaschool.foundation.services.RoleService;
import com.lambdaschool.foundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Set;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Component
public class SeedData
    implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;


    @Autowired
    PotluckService potLuckService;
    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws
                                   Exception
    {
        userService.deleteAll();
        roleService.deleteAll();
        potLuckService.deleteAll();

        // Create a single user role
        Role r1 = new Role("role_user");
        r1 = roleService.save(r1);

        // Create users
        User u1 = new User("admin",
            "password",
            "admin@lambdaschool.local");
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));

        User u2 = new User("cinnamon",
            "1234567",
            "cinnamon@lambdaschool.local");
        u2.getRoles()
            .add(new UserRoles(u1,
                r1));

        User u3 = new User("barnbarn",
            "ILuvM4th!",
            "barnbarn@lambdaschool.local");
        u3.getRoles()
            .add(new UserRoles(u1,
                r1));

        User u4 = new User("puttat",
            "password",
            "puttat@school.lambda");
        u4.getRoles()
            .add(new UserRoles(u1,
                r1));

        User u5 = new User("misskitty",
            "password",
            "misskitty@school.lambda");
        u5.getRoles()
            .add(new UserRoles(u1,
                r1));

        // Create potlucks
        Potluck p1 = new Potluck("Saturday Game Night",
            "My house", "March 20", "7 PM", u1.getUsername());
        Potluck p2 = new Potluck("Sunday Picnic",
            "Shillito Park", "March 6", "1 PM", u2.getUsername());
        Potluck p3 = new Potluck("Coffee Club",
            "Brevede", "March 13", "9 AM", u3.getUsername());
        Potluck p4 = new Potluck("Work Celebration",
            "Lambda Office", "March 5th", "4 PM", u4.getUsername());
        Potluck p5 = new Potluck("Brunch",
            "Community Center", "March 27", "11 AM", u5.getUsername());

        // Create and add items to potlucks
        p1.getItems().add(new Item("pizza","someone",false,p1));
        p1.getItems().add(new Item("chips", "random guys",false, p1));
        p2.getItems().add(new Item("strawberries","someone",false,p1));
        p2.getItems().add(new Item("sage", "random guys",false, p1));
        p3.getItems().add(new Item("scones","someone",false,p1));
        p3.getItems().add(new Item("blueberry muffins", "random guys",false, p1));
        p4.getItems().add(new Item("cookies","someone",false,p1));
        p4.getItems().add(new Item("brownies", "random guys",false, p1));
        p5.getItems().add(new Item("juice","someone",false,p1));
        p5.getItems().add(new Item("coffee", "random guys",false, p1));
        Potluck potluck1 = potLuckService.save(p1);
        Potluck potluck2 = potLuckService.save(p2);
        Potluck potluck3 = potLuckService.save(p3);
        Potluck potluck4 = potLuckService.save(p4);
        Potluck potluck5 = potLuckService.save(p5);

        // Add potlucks to users
        u1.getPotlucks()
            .add(new UserPotlucks(potluck5,u1));
        u2.getPotlucks()
            .add(new UserPotlucks(potluck4,u2));
        u3.getPotlucks()
            .add(new UserPotlucks(potluck3,u3));
        u4.getPotlucks()
            .add(new UserPotlucks(potluck2,u4));
        u5.getPotlucks()
            .add(new UserPotlucks(potluck1,u5));

        // save users
        userService.save(u1);

        userService.save(u2);

        userService.save(u3);

        userService.save(u4);

        userService.save(u5);






        if (false)
        {
            // using JavaFaker create a bunch of regular users
            // https://www.baeldung.com/java-faker
            // https://www.baeldung.com/regular-expressions-java

            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                new RandomService());
            Faker nameFaker = new Faker(new Locale("en-US"));

            for (int i = 0; i < 25; i++)
            {
                new User();
                User fakeUser;

                fakeUser = new User(nameFaker.name()
                    .username(),
                    "password",
                    nameFaker.internet()
                        .emailAddress());
                fakeUser.getRoles()
                    .add(new UserRoles(fakeUser,
                        r1));
                fakeUser.getUseremails()
                    .add(new Useremail(fakeUser,
                        fakeValuesService.bothify("????##@gmail.com")));
                userService.save(fakeUser);
            }
        }
    }
}