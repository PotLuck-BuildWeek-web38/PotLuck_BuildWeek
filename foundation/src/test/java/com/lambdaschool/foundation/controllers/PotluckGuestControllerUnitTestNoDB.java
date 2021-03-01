package com.lambdaschool.foundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.services.GuestService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WithMockUser(username = "cinnamon",
    roles = { "USER"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = FoundationApplicationTesting.class,
    properties = {
        "command.line.runner.enabled=false"
    })
@AutoConfigureMockMvc
public class PotluckGuestControllerUnitTestNoDB
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private GuestService guestService;

    private List<User> guestList;
    private List<Potluck> potluckList;

    @Before
    public void setUp() throws Exception
    {
        // create sample data from SeedData, need to set ids
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

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findAllGuestsByPotluckId() throws Exception
    {
        String apiUrl = "/potlucks/potluck/101/guests";
        Mockito.when(guestService.findAllGuests(101L))
            .thenReturn(guestList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(guestList);

        System.out.println(er);
        assertEquals(er,
            tr);
    }

    @Test
    public void addGuestToPotluck() throws Exception
    {

        String apiUrl = "/potlucks/potluck/101/addguest/10";
        Mockito.when(guestService.addGuest(101L, 10L))
            .thenReturn(guestList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void removeGuestFromPotluck() throws Exception
    {

        String apiUrl = "/potlucks/potluck/101/removeguest/10";

        Mockito.when(guestService.removeGuest(101L, 10L))
            .thenReturn(guestList.get(0));
        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());


    }
}