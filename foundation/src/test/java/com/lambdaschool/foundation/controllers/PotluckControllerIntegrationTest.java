package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.services.PotluckService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
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
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = FoundationApplicationTesting.class)
@AutoConfigureMockMvc
@WithUserDetails(value = "cinnamon")
public class PotluckControllerIntegrationTest
{

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    PotluckService potluckService;

    @Before
    public void setUp() throws Exception
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        List<Potluck> myPotlucks = potluckService.findAll();
        for(Potluck p : myPotlucks)
        {
            System.out.println(p.getPotluckid() + " " + p.getName());
        }

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllPotlucks()
    {
        given().when()
            .get("/potlucks/potlucks")
            .then()
            .statusCode(200)
            .and()
            .body(containsString("Coffee Club"));
    }

    @Test
    public void getPotluckById()
    {
        long aPotluckId = 14;

        given().when()
            .get("/potlucks/potluck/" + aPotluckId)
            .then()
            .statusCode(200)
            .and()
            .body(containsString("Brunch"));
    }

    @Test
    public void getPotluckByIdNotFound()
    {
        long notAPotluckId = 7777;

        given().when()
            .get("/potlucks/potluck/" + notAPotluckId)
            .then()
            .statusCode(404)
            .and()
            .body(containsString("Resource"));
    }

    @Test
    public void addPotluck() throws Exception
    {
        String jsonInput = "{\n" +
            "    \"name\": \"Birthday Party\",\n" +
            "    \"location\": \"Malibu Jack's\",\n" +
            "    \"date\": \"March 28th\",\n" +
            "    \"time\": \"2 PM\",\n" +
            "    \"organizer\": \"barnbarn\" \n" +
            "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/potlucks/potluck")
            .content(jsonInput)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.header()
                .exists("location"));
    }

    @Test
    public void updateFullPotluck() throws Exception
    {
        String jsonInput = "{\n" +
            "    \"name\": \"Football Tailgate\",\n" +
            "    \"location\": \"Kroger Field\",\n" +
            "    \"date\": \"September 28th\",\n" +
            "    \"time\": \"12 Noon\",\n" +
            "    \"organizer\": \"cinnamon\" \n" +
            "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/potlucks/potluck/{potluckid}",
            2)
            .content(jsonInput)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void deletePotluckById()
    {
        long aPotluckId = 2;
        given().when()
            .delete("/potlucks/potluck/" + aPotluckId)
            .then()
            .statusCode(200);
    }
    @Test
    public void deletePotluckByIdNotFound()
    {
        long notAPotluckId = 7777;
        given().when()
            .delete("/potlucks/potluck/" + notAPotluckId)
            .then()
            .statusCode(404);
    }
}