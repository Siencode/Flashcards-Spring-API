package io.siencode.flashcards.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlashcardCategoryRepository flashcardCategoryRepository;

    @Test
    void testUserinfoPermissionsForNotLoggedUser() throws Exception {
        mockMvc.perform(get("/api/userinfo"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "FirstUsername")
    void testUserinfoPermissionsForLoggedUser() throws Exception {
        mockMvc.perform(get("/api/userinfo"))
                .andExpect(content().string("FirstUsername"))
                .andExpect(status().isOk()) ;
    }

    @Test
    void testAddNewUserToRepositoryAndCreateDefaultCategory() throws Exception {
        String userJsonDataSet = createRegistrationJsonDataSet("username", "password");
        this.mockMvc.perform(post("/api/register")
                .content(userJsonDataSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(1, userRepository.findAll().size());
        assertEquals(1, flashcardCategoryRepository.findAll().size());

    }

    @Test
    void testPermissionsTestForRegistrationWithoutContent() throws Exception {
        mockMvc.perform(post("/api/register"))
                .andExpect(status().isBadRequest()) ;
    }

    @Test
    void testRegistrationsWithBadUsername() throws Exception{
        String userJsonDataJsonSet = createRegistrationJsonDataSet("username2321341432212", "password");
        MvcResult mvcResult = this.mockMvc.perform(post("/api/register")
                .content(userJsonDataJsonSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue((mvcResult.getResponse().getContentAsString().matches(".*Incorrect username length.*")));
    }

    @Test
    void testRegistrationsWithNullUsername() throws Exception{
        String userJsonDataSet = createRegistrationJsonDataSet(null, "password");
        MvcResult mvcResult = this.mockMvc.perform(post("/api/register")
                .content(userJsonDataSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue((mvcResult.getResponse().getContentAsString().matches(".*Username cannot be null.*")));
    }

    @Test
    void testRegistrationsWithBadPassword() throws Exception{
        String userJsonDataSet = createRegistrationJsonDataSet("username", "password1234567890121");
        MvcResult mvcResult = this.mockMvc.perform(post("/api/register")
                .content(userJsonDataSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue((mvcResult.getResponse().getContentAsString().matches(".*Incorrect password length.*")));
    }

    @Test
    void testRegistrationsWithNullPassword() throws Exception{
        String userJsonDataSet = createRegistrationJsonDataSet("username", null);
        MvcResult mvcResult= this.mockMvc.perform(post("/api/register")
                .content(userJsonDataSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue((mvcResult.getResponse().getContentAsString().matches(".*Password cannot be null.*")));
    }

    @Test
    void testRegistrationsWithBadUsernameAndPassword() throws Exception{
        String userJsonDataSet = createRegistrationJsonDataSet("username2321341432212", "password1234567890121");
        this.mockMvc.perform(post("/api/register")
                .content(userJsonDataSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegistrationsWithNullUsernameAndPassword() throws Exception{
        String userJsonDataSet = createRegistrationJsonDataSet(null, null);
        this.mockMvc.perform(post("/api/register")
                .content(userJsonDataSet)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    String createRegistrationJsonDataSet(String username, String password) {
        Map<String,Object> map = new HashMap<>();
        if (username != null)
        map.put("username", username);
        if (password != null)
            map.put("password", password);
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            System.out.println("ERROR: " + e.getMessage());
            return "";
        }
    }

}
