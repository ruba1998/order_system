package com.example.order_system.controller;

import com.example.order_system.domain.Restaurant;
import com.example.order_system.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RestaurantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController)
                .build();
    }

    @Test
    public void testGetAllRestaurants() throws Exception {
        Restaurant restaurant1 = new Restaurant("Orgada Burger", "The best burger ever!", "Nablus");
        Restaurant restaurant2 = new Restaurant("Pizza House", "Several Italian dishes, Yummy Taste!", "Ramallah");
        List<Restaurant> restaurantList = List.of(restaurant1, restaurant2);

        when(restaurantService.findAll()).thenReturn(restaurantList);

        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Orgada Burger")))
                .andExpect(jsonPath("$[0].description", Matchers.is("The best burger ever!")))
                .andExpect(jsonPath("$[0].location", Matchers.is("Nablus")))
                .andExpect(jsonPath("$[1].name", Matchers.is("Pizza House")))
                .andExpect(jsonPath("$[1].description", Matchers.is("Several Italian dishes, Yummy Taste!")))
                .andExpect(jsonPath("$[1].location", Matchers.is("Ramallah")));
    }

    @Test
    public void testCreateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Orgadaa");
        restaurant.setDescription("The best burger!");
        restaurant.setLocation("Jerusalem");
        String jsonRequest = om.writeValueAsString(restaurant);
        mockMvc.perform(post("/restaurants/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.is("Orgadaa")))
                .andExpect(jsonPath("$.description", Matchers.is("The best burger!")))
                .andExpect(jsonPath("$.location", Matchers.is("Jerusalem")));

        verify(restaurantService).save(any(Restaurant.class));

    }
}
