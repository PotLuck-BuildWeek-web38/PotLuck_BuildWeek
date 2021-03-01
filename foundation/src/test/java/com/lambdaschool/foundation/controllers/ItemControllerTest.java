package com.lambdaschool.foundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.models.Item;
import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.repository.ItemRepository;
import com.lambdaschool.foundation.repository.PotluckRepository;
import com.lambdaschool.foundation.services.HelperFunctions;
import com.lambdaschool.foundation.services.ItemService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
public class ItemControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ItemService itemService;

    @MockBean
    private HelperFunctions helperFunctions;

    private MockMvc mockMvc;

    @MockBean
    private ItemRepository itemrepos;
    @MockBean
    private PotluckRepository potluckrepos;

    private List<Potluck> potluckList;
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

        Item i1 =new Item("pizza","someone",false,p1);
        Item i2 = new Item("chips", "random guys",false, p1);
        Item i3 = new Item("strawberries","someone",false,p1);
        Item i4 = new Item("sage", "random guys",false,p1 );
        i1.setItemid(102);
        i2.setItemid(101);
        itemList.add(i1);
        itemList.add(new Item("scones","someone",false,p1));
        itemList.add(new Item("blueberry muffins", "random guys",false, p1));
        itemList.add(new Item("cookies","someone",false,p1));
        itemList.add(new Item("brownies", "random guys",false, p1));
        itemList.add(new Item("juice","someone",false,p1));
        itemList.add(new Item("coffee", "random guys",false, p1));
        itemList.add(i2);
        itemList.add(i3);
        itemList.add(i4);
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listItem() throws Exception{
        String apiUrl = "/potlucks/item";

        Mockito.when(itemService.findAll())
                .thenReturn(itemList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb)
                .andReturn(); // this could throw an exception
        String tr = r.getResponse()
                .getContentAsString();
        System.out.println(itemList);
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(itemList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
                er,
                tr);
    }

    @Test
    public void getItemById() throws Exception{
        String apiUrl = "/potlucks/item/102";
        Mockito.when(itemService.findById(102L))
                .thenReturn(itemList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get("/potlucks/item/102")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn(); // this could throw an exception
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(itemList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
                er,
                tr);
    }

    @Test
    public void updateItem() throws Exception {
        String apiUrl = "/potlucks/updateitem/1";
        Item i = new Item();
        i.setGuest("guys");

        String patch = "{\"name\": \"tigerUpdated\", \"guest\": \"EATEATEAT\"}";

        System.out.println(patch);

        Mockito.when(itemService.update(102L,
                i))
                .thenReturn(i);

        RequestBuilder rb = MockMvcRequestBuilders.patch(apiUrl, 102L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(patch);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteItemById() throws Exception{
        String apiUrl = "/potlucks/item/102";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "102")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}