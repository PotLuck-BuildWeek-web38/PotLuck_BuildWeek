package com.lambdaschool.foundation.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.repository.ItemRepository;
import com.lambdaschool.foundation.repository.PotluckRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoundationApplicationTesting.class,
    properties = {
        "command.line.runner.enabled=false"})
public class PotluckServiceImplUnitTestNoDB
{
    @Autowired
    private PotluckService potluckService;

    @MockBean
    private PotluckRepository potluckrepos;

    @MockBean
    private UserRepository userrepos;

    @MockBean
    private ItemRepository itemrepos;

    @MockBean
    private UserService userService;

    private List<User> guestList;
    private List<Potluck> potluckList;

    @Before
    public void setUp() throws Exception
    {
        // set up mock data
        guestList = new ArrayList<>();
        potluckList = new ArrayList<>();

        Role r1 = new Role("role_user");
        r1.setRoleid(1);

        // Create users
        User u1 = new User("admin",
            "password",
            "admin@lambdaschool.local");
        u1.setUserid(10);
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));

        User u2 = new User("cinnamon",
            "1234567",
            "cinnamon@lambdaschool.local");
        u1.setUserid(20);
        u2.getRoles()
            .add(new UserRoles(u1,
                r1));

        User u3 = new User("barnbarn",
            "ILuvM4th!",
            "barnbarn@lambdaschool.local");
        u3.setUserid(30);
        u3.getRoles()
            .add(new UserRoles(u1,
                r1));

        User u4 = new User("puttat",
            "password",
            "puttat@school.lambda");
        u4.setUserid(40);
        u4.getRoles()
            .add(new UserRoles(u1,
                r1));

        User u5 = new User("misskitty",
            "password",
            "misskitty@school.lambda");
        u5.setUserid(50);
        u5.getRoles()
            .add(new UserRoles(u1,
                r1));

        // Create potlucks
        Potluck p1 = new Potluck("Saturday Game Night",
            "My house", "March 20", "7 PM", u1.getUsername());
        p1.setPotluckid(101);
        p1.getUsers().add(new UserPotlucks(p1, u1));
        Potluck p2 = new Potluck("Sunday Picnic",
            "Shillito Park", "March 6", "1 PM", u2.getUsername());
        p2.setPotluckid(102);
        Potluck p3 = new Potluck("Coffee Club",
            "Brevede", "March 13", "9 AM", u3.getUsername());
        p3.setPotluckid(103);
        Potluck p4 = new Potluck("Work Celebration",
            "Lambda Office", "March 5th", "4 PM", u4.getUsername());
        p4.setPotluckid(104);
        Potluck p5 = new Potluck("Brunch",
            "Community Center", "March 27", "11 AM", u5.getUsername());
        p5.setPotluckid(105);

        // Create and add items to potlucks
        Item item1 = new Item("pizza","someone",false,p1);
        item1.setItemid(11);
        Item item2 = new Item("chips", "random guys",false, p2);
        item2.setItemid(22);
        Item item3 = new Item("sage", "random guys",false, p3);
        item3.setItemid(33);
        Item item4 = new Item("scones","someone",false,p4);
        item4.setItemid(44);
        Item item5 = new Item("blueberry muffins", "random guys",false, p5);
        item5.setItemid(55);
        p1.getItems().add(item1);
        p2.getItems().add(item2);
        p3.getItems().add(item3);
        p4.getItems().add(item4);
        p5.getItems().add(item5);

        potluckList.add(p1);
        potluckList.add(p2);
        potluckList.add(p3);
        potluckList.add(p4);
        potluckList.add(p5);

        // Add potlucks to users
        u1.getPotlucks()
            .add(new UserPotlucks(p1,u1));
        u2.getPotlucks()
            .add(new UserPotlucks(p2,u2));
        u3.getPotlucks()
            .add(new UserPotlucks(p3,u3));
        u4.getPotlucks()
            .add(new UserPotlucks(p4,u4));
        u5.getPotlucks()
            .add(new UserPotlucks(p5,u5));

        // save users to array list
        guestList.add(u1);
        guestList.add(u2);
        guestList.add(u3);
        guestList.add(u4);
        guestList.add(u5);

        MockitoAnnotations.initMocks(this);

    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findAll()
    {
        Mockito.when(potluckrepos.findAll())
            .thenReturn(potluckList);
       assertEquals(5, potluckService.findAll().size());
    }

    @Test
    public void findPotluckById()
    {
        Mockito.when(potluckrepos.findById(any(Long.class)))
            .thenReturn(Optional.of(potluckList.get(0)));

        assertEquals("Saturday Game Night", potluckService.findPotluckById(101).getName());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void findPotluckByIdNotFound()
    {
        Mockito.when(potluckrepos.findById(any(Long.class)))
            .thenReturn(Optional.empty());

        assertEquals("Saturday Game Night", potluckService.findPotluckById(101).getName());
    }
    @Test
    public void save()
    {
        String potluckName = "Birthday Party";
        Potluck newPotluck = new Potluck();
        newPotluck.setName(potluckName);
        newPotluck.setDate("March 14th");
        newPotluck.setTime("2 PM");
        newPotluck.setLocation("Malibu Jacks");
        newPotluck.setOrganizer("barnbarn");
        newPotluck.getItems().add(new Item("bananas", "barnbarn",false,newPotluck));
        newPotluck.getUsers().add(new UserPotlucks( newPotluck, guestList.get(0)));

        Mockito.when(potluckrepos.findById(any(Long.class)))
            .thenReturn(Optional.of(potluckList.get(0)));

        Mockito.when(userService.findUserById(any(Long.class)))
            .thenReturn(guestList.get(0));

        Mockito.when(potluckrepos.save(any(Potluck.class)))
            .thenReturn(newPotluck);

        Potluck addedPotluck = potluckService.save(newPotluck);
        assertEquals(newPotluck.getName(), addedPotluck.getName());

    }

    @Test (expected = ResourceNotFoundException.class)
    public void saveNotFound()
    {
        String potluckName = "Birthday Party";
        Potluck newPotluck = new Potluck();
        newPotluck.setName(potluckName);
        newPotluck.setDate("March 14th");
        newPotluck.setTime("2 PM");
        newPotluck.setLocation("Malibu Jacks");
        newPotluck.setOrganizer("barnbarn");
        newPotluck.getItems().add(new Item("bananas", "barnbarn",false,newPotluck));
        newPotluck.getUsers().add(new UserPotlucks( newPotluck, guestList.get(0)));
        newPotluck.setPotluckid(999L);

        Mockito.when(potluckrepos.findById(999L))
            .thenReturn(Optional.empty());

        Mockito.when(userService.findUserById(any(Long.class)))
            .thenReturn(guestList.get(0));

        Mockito.when(potluckrepos.save(any(Potluck.class)))
            .thenReturn(newPotluck);

        Potluck addedPotluck = potluckService.save(newPotluck);
        assertEquals(newPotluck.getName(), addedPotluck.getName());

    }

    @Test
    public void savePutFound()
    {
        String potluckName = "Birthday Party";
        Potluck newPotluck = new Potluck();
        newPotluck.setName(potluckName);
        newPotluck.setDate("March 14th");
        newPotluck.setTime("2 PM");
        newPotluck.setLocation("Malibu Jacks");
        newPotluck.setOrganizer("barnbarn");
        newPotluck.getItems().add(new Item("bananas", "barnbarn",false,newPotluck));
        newPotluck.getUsers().add(new UserPotlucks( newPotluck, guestList.get(0)));
        newPotluck.setPotluckid(999L);

        Mockito.when(potluckrepos.findById(999L))
            .thenReturn(Optional.of(potluckList.get(0)));

        Mockito.when(userService.findUserById(any(Long.class)))
            .thenReturn(guestList.get(0));

        Mockito.when(potluckrepos.save(any(Potluck.class)))
            .thenReturn(newPotluck);

        Potluck addedPotluck = potluckService.save(newPotluck);
        assertEquals(newPotluck.getName(), addedPotluck.getName());

    }

    @Test
    public void update() throws Exception
    {
        String potluckName = "Birthday Party";
        Potluck newPotluck = new Potluck();
        newPotluck.setName(potluckName);
        newPotluck.setDate("March 14th");
        newPotluck.setTime("2 PM");
        newPotluck.setLocation("Malibu Jacks");
        newPotluck.setOrganizer("barnbarn");
        newPotluck.getItems().add(new Item("bananas", "barnbarn",false,newPotluck));
        newPotluck.getUsers().add(new UserPotlucks( newPotluck, guestList.get(0)));


        // I need a copy of newPotluck to send to update so the original newPotluck is not changed.
        // I am using Jackson to make a clone of the object
        ObjectMapper objectMapper = new ObjectMapper();
        Potluck copyPotluck = objectMapper
            .readValue(objectMapper.writeValueAsString(newPotluck), Potluck.class);

        Mockito.when(potluckrepos.findById(10L))
            .thenReturn(Optional.of(copyPotluck));

        Mockito.when(potluckrepos.save(any(Potluck.class)))
            .thenReturn(newPotluck);

        Potluck addPotluck = potluckService.update(newPotluck,
            10);

        assertNotNull(addPotluck);
        assertEquals(newPotluck.getName(),
            addPotluck.getName());

    }

    @Test (expected = ResourceNotFoundException.class)
    public void updateNotFound() throws Exception
    {
        String potluckName = "Birthday Party";
        Potluck newPotluck = new Potluck();
        newPotluck.setName(potluckName);
        newPotluck.setDate("March 14th");
        newPotluck.setTime("2 PM");
        newPotluck.setLocation("Malibu Jacks");
        newPotluck.setOrganizer("barnbarn");

        // I need a copy of r2 to send to update so the original r2 is not changed.
        // I am using Jackson to make a clone of the object
        ObjectMapper objectMapper = new ObjectMapper();
        Potluck copyPotluck = objectMapper
            .readValue(objectMapper.writeValueAsString(newPotluck), Potluck.class);

        Mockito.when(potluckrepos.findById(10L))
            .thenReturn(Optional.empty());

        Mockito.when(potluckrepos.save(any(Potluck.class)))
            .thenReturn(newPotluck);

        Potluck addPotluck = potluckService.update(newPotluck,
            10);

        assertNotNull(addPotluck);
        assertEquals(newPotluck.getName(),
            addPotluck.getName());

    }

    @Test
    public void delete()
    {
        Mockito.when(potluckrepos.findById(any(Long.class)))
            .thenReturn(Optional.of(potluckList.get(0)));
        Mockito.doNothing()
            .when(potluckrepos).deleteById(any(Long.class));

        potluckService.delete(2);
        assertEquals(5, potluckList.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteNotFound()
    {
        Mockito.when(potluckrepos.findById(any(Long.class)))
            .thenReturn(Optional.empty());
        Mockito.doNothing()
            .when(potluckrepos).deleteById(any(Long.class));

        potluckService.delete(2);
        assertEquals(5, potluckList.size());
    }

    @Test
    public void deleteAll()
    {
        Mockito.doNothing()
            .when(potluckrepos).deleteAll();
        potluckService.deleteAll();
        assertEquals(5, potluckList.size());
    }

    @Test
    public void findPotlucksByOrganizer()
    {
        Mockito.when(potluckrepos.findByOrganizer(any(String.class)))
            .thenReturn(potluckList);
        assertEquals(5, potluckService.findPotlucksByOrganizer("cinnamon").size());
    }
}