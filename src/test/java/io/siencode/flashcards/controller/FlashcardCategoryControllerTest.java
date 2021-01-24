package io.siencode.flashcards.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.siencode.flashcards.model.UserModel;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.service.FlashcardCategoryServiceImpl;
import io.siencode.flashcards.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlashcardCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlashcardCategoryServiceImpl flashcardCategoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private FlashcardCategoryRepository flashcardCategoryRepository;

    @BeforeAll
    public void init() {
        UserModel firstUserModel = new UserModel();
        firstUserModel.setUsername("username1");
        firstUserModel.setPassword("password1");
        UserModel secUserModel = new UserModel();
        secUserModel.setUsername("username2");
        secUserModel.setPassword("password2");

        userService.saveUser(firstUserModel);
        flashcardCategoryService.saveDefaultFlashcardCategories(firstUserModel.getUsername());

        userService.saveUser(secUserModel);
        flashcardCategoryService.saveDefaultFlashcardCategories(secUserModel.getUsername());

    }

    @Test
    void testPermissionsForNotLoggedUser() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(delete("/api/category/1"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/category"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(put("/api/category/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "username1")
    void testGetUserFlashcardCategory() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value("Default"));
    }

    @Test
    @WithMockUser(username = "username1")
    void testDeleteDefaultCategory() throws Exception {
        mockMvc.perform(delete("/api/category/1"))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "username1")
    void testDeleteNotExistCategory() throws Exception {
        mockMvc.perform(delete("/api/category/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "username1")
    void testDuplicateDefaultCategory() throws Exception {
        mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString("Default")))
                .andExpect(status().isConflict());
    }

    @Test
    @BeforeTestMethod
    @WithMockUser(username = "username1")
    public void testAddNewCategoryAndEdit() throws Exception {
        mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString("First category")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].categoryName").value("First category"));

        mockMvc.perform(put("/api/category/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString("Sec category")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].categoryName").value("Sec category"));
    }

    @Test
    @WithMockUser(username = "username1")
    void testDeleteAddedCategory() throws Exception {
        int repoSize = flashcardCategoryRepository.findAll().size();
        mockMvc.perform(delete("/api/category/3"))
                .andExpect(status().isOk());

        assertEquals(repoSize - 1, flashcardCategoryRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "username1")
    void testAddEmptyNameOfCategory() throws Exception{
        MvcResult mvcResult= this.mockMvc.perform(post("/api/category")
                .content(new ObjectMapper().writeValueAsString(""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue((mvcResult.getResponse().getContentAsString().matches(".*Incorrect category name length.*")));
    }

    @Test
    @WithMockUser(username = "username1")
    void testEditDefaultCategory() throws Exception {
        mockMvc.perform(put("/api/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString("Default")))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "username1")
    void testEditNoExistCategory() throws Exception {
        mockMvc.perform(put("/api/category/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString("Fourth Category")))
                .andExpect(status().isNotFound());
    }
}