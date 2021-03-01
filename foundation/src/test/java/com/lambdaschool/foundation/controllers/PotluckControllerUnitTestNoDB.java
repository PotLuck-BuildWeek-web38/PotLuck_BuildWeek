package com.lambdaschool.foundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.models.*;
import com.lambdaschool.foundation.services.PotluckService;
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
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser(username = "admin",
    roles = {"USER", "ADMIN"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = FoundationApplicationTesting.class,
    properties = {
        "command.line.runner.enabled=false"})
@AutoConfigureMockMvc
public class PotluckControllerUnitTestNoDB
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PotluckService potluckService;

    private List<Potluck> potluckList;
    private List<User> userList;

    @Before
    public void setUp() throws Exception
    {
        userList = new ArrayList<>();
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
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);
        userList.add(u5);

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
    public void listAllPotlucks() throws Exception
    {
        String apiUrl = "/potlucks/potlucks";
        Mockito.when(potluckService.findAll())
            .thenReturn(potluckList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(potluckList);

        System.out.println(er);
        assertEquals(er,
            tr);
    }

    @Test
    public void getPotluckById() throws Exception
    {
        String apiUrl = "/potlucks/potluck/101";
        Mockito.when(potluckService.findPotluckById(101))
            .thenReturn(potluckList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(potluckList.get(0));

        System.out.println(tr);
        assertEquals(er,
            tr);
    }

    @Test
    public void getPotluckByIdNotFound() throws Exception
    {
        String apiUrl = "/potlucks/potluck/999";
        Mockito.when(potluckService.findPotluckById(999))
            .thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        String er = "";

        System.out.println(er);
        assertEquals(er,
            tr);
    }

    @Test
    public void addPotluck() throws Exception
    {
        String apiUrl = "/potlucks/potluck";

        String potluckName = "Test Birthday Party";
        Potluck testPotluck = new Potluck(potluckName,
            "Malibu Jack's",
            "March 14th",
            "2 PM",
            "cinnamon");
        testPotluck.setPotluckid(142);

        ObjectMapper mapper = new ObjectMapper();
        String potluckString = mapper.writeValueAsString(testPotluck);

        Mockito.when(potluckService.save(any(Potluck.class)))
            .thenReturn(testPotluck);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(potluckString);

        mockMvc.perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateFullPotluck() throws Exception
    {
        String apiUrl = "/potlucks/potluck/{potluckid}";
        String potluckName = "Test Baby Shower";
        Potluck babyShower = new Potluck(potluckName,
            "My House",
            "March 28th",
            "6 PM",
            "misskitty");
        babyShower.setPotluckid(222);

        Mockito.when(potluckService.update(babyShower,
            222L))
            .thenReturn(babyShower);
        ObjectMapper mapper = new ObjectMapper();
        String babyShowerString = mapper.writeValueAsString(babyShower);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl,
            222L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(babyShowerString);

        mockMvc.perform(rb)
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deletePotluckById() throws Exception
    {
        String apiUrl = "/potlucks/potluck/{potluckid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl,
            "105")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }
}