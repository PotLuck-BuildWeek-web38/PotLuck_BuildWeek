package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.models.Potluck;
import com.lambdaschool.foundation.services.GuestService;
import com.lambdaschool.foundation.models.Role;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.services.PotluckService;
import com.lambdaschool.foundation.services.RoleService;
import com.lambdaschool.foundation.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = FoundationApplicationTesting.class)
@AutoConfigureMockMvc
@WithUserDetails(value = "admin")
public class PotluckGuestControllerIntegrationTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    GuestService guestService;

    @Autowired
    PotluckService potluckService;

    @Before
    public void setUp() throws Exception
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        List<User> myUsers = userService.findAll();
        for (User u : myUsers )
        {
            System.out.println(u.getUserid() + " " + u.getUsername());
        }
        List<Role> myRoles = roleService.findAll();
        for(Role r : myRoles)
        {
            System.out.println(r.getRoleid() + " " + r.getRoleid());
        }
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
    public void findAllGuestsByPotluckId()
    {
        given().when()
            .get("/potlucks/potluck/11/guests")
            .then()
            .statusCode(200)
            .and()
            .body(containsString("cinnamon"));
    }

    @Test
    public void addGuestToPotluck() throws Exception
    {
       String jsonInput = "";
        mockMvc.perform(MockMvcRequestBuilders.post("/potlucks/potluck/11/addguest/19")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void removeGuestFromPotluck() throws Exception
    {
        given().when()
            .delete("/potlucks/potluck/2/removeguest/20")
            .then()
            .statusCode(200);
    }
}