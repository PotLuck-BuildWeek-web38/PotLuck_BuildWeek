package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.models.Item;
import com.lambdaschool.foundation.services.ItemService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser(username = "admin",
        roles = {"USER", "ADMIN"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = FoundationApplicationTesting.class)
public class ItemControllerIntegartionTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    ItemService itemService;




    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        List<Item> itemList = itemService.findAll();

        for(Item i : itemList){
            System.out.println(i.getItemid()+ " "+ i.getName());
        }

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listItem() {
        given().when()
                .get("/potlucks/item")
                .then()
                .statusCode(200)
                .and()
                .body(containsString("pizza"));
    }

    @Test
    public void getItemById() {
        long i= 10;
        given().when()
                .get("/potlucks/item/"+ i)
                .then()
                .statusCode(200)
                .and()
                .body(containsString("muffins"));
    }

    @Test
    public void updateItem() throws Exception {
        String jsonInput = "{\"name\": \"tigerUpdated\", \"guest\": \"EATEATEAT\"}";

        mockMvc.perform(MockMvcRequestBuilders.patch("/potlucks/updateitem/{id}",12)
        .content(jsonInput)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());

    }

    @Test
    public void deleteItemById() {
        long i = 10;
        given().when()
                .delete("/potlucks/item/"+ i)
                .then()
                .statusCode(200);
    }
}