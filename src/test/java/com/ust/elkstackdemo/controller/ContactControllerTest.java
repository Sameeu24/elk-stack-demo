package com.ust.elkstackdemo.controller;

import com.ust.elkstackdemo.model.Contact;
import com.ust.elkstackdemo.repository.ContactRepository;
import com.ust.elkstackdemo.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    // MockMvc is used to simulate HTTP requests to the controller.
    @Autowired
    MockMvc mockMvc;

    // MockBean is used to mock the ContactRepository and ContactService beans. This creates a
    // mock object for each of these beans, which can be used to simulate the behavior of the
    // actual beans.
    @MockBean
    ContactRepository contactRepository;

    @MockBean
    ContactService contactService;

    Contact contact;

    @BeforeEach
    void setUp() {
        contact = new Contact("John Doe", "john.doe", "1234567890");
    }

    @Test
    @DisplayName("Test getALlContacts GET request")
    void getAllContacts_WithSatusOK() throws Exception {
        when(contactService.getContacts()).thenReturn(List.of(contact));

        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe"))
                .andExpect(jsonPath("$[0].phoneNumber").value("1234567890"))
                .andDo(print());
    }

    @Test
    @DisplayName("Test getALlContacts GET request")
    void getAllContacts_WithSatusNoContent() throws Exception {
        when(contactService.getContacts()).thenReturn(List.of());

        mockMvc.perform(get("/contacts"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("Test addContact POST request")
    void addContact_WithSatusCreated() throws Exception {
        doNothing()
                .when(contactService)
                .addContact("John Doe", "john.doe", "1234567890");
        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe\",\"phoneNumber\":\"1234567890\"}"))
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.name").value("John Doe"))
//                .andExpect(jsonPath("$.email").value("john.doe"))
//                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andDo(print());
    }

}