package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.repository.ItemRepository;
import com.lambdaschool.foundation.repository.PotluckRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoundationApplicationTesting.class,
        properties = {
                "command.line.runner.enabled=false"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemServiceImplTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemrepos;
    @MockBean
    private PotluckRepository potluckrepos;

    private List<Potluck>  potluckList;
    private List<Item> itemList;
    @Before
    public void setUp() throws Exception {
        potluckList = new ArrayList<>();
        itemList = new ArrayList<>();



        // Create users
        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local");


        User u2 = new User("cinnamon",
                "1234567",
                "cinnamon@lambdaschool.local");


        User u3 = new User("barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");


        User u4 = new User("puttat",
                "password",
                "puttat@school.lambda");


        User u5 = new User("misskitty",
                "password",
                "misskitty@school.lambda");

//
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
        potluckList.add(p1);
        potluckList.add(p2);
        potluckList.add(p3);
        potluckList.add(p4);
        potluckList.add(p5);

        itemList.add(new Item("pizza","someone",false,p1));
        itemList.add(new Item("chips", "random guys",false, p1));
        itemList.add(new Item("strawberries","someone",false,p1));
        itemList.add(new Item("sage", "random guys",false,p1 ));
        itemList.add(new Item("scones","someone",false,p1));
        itemList.add(new Item("blueberry muffins", "random guys",false, p1));
        itemList.add(new Item("cookies","someone",false,p1));
        itemList.add(new Item("brownies", "random guys",false, p1));
        itemList.add(new Item("juice","someone",false,p1));
        itemList.add(new Item("coffee", "random guys",false, p1));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findAll() {
        Mockito.when(itemrepos.findAll()).thenReturn(itemList);


        System.out.println(itemService.findAll());
        assertEquals(10,
                itemService.findAll()
                        .size());
    }

    @Test
    public void findById() {
        Mockito.when(itemrepos.findById(4L))
                .thenReturn(Optional.of(itemList.get(0)));

        assertEquals("pizza",
                itemService.findById(4)
                        .getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void notFindById() {
        Mockito.when(itemrepos.findById(4000L))
                .thenThrow(ResourceNotFoundException.class);

        assertEquals("pizza",
                itemService.findById(4000)
                        .getName());
    }
    @Test
    public void delete() {
        Mockito.when(itemrepos.findById(0L))
                .thenReturn(Optional.of(itemList.get(0)));

        Mockito.doNothing()
                .when(itemrepos)
                .deleteById(0L);

        itemService.delete(0);
        assertEquals(10,
                itemList.size());
    }

    @Test
    public void update() {
    }
}