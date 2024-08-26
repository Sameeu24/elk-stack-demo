package com.ust.elkstackdemo.service;

import com.ust.elkstackdemo.model.Contact;
import com.ust.elkstackdemo.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContactServiceTest {

    /**
     * Use Mockito to mock the ContactRepository object
     * <p>
     * Test cases to be generated
     * 1. addContact_WithValidContact_ShouldAddContact
     * 2. addContact_WithNullName_ShouldThrowException
     * 3. addContact_WithEmptyName_ShouldThrowException
     * 4. addContact_WithNullPhoneNumber_ShouldThrowException
     * 5. addContact_WithEmptyPhoneNumber_ShouldThrowException
     * 6. addContact_WithExistingContact_ShouldThrowException
     */

    @Mock
    ContactRepository contactRepository;

    @InjectMocks
    ContactService contactService;

    List<Contact> contacts;
    Contact contact;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contact = new Contact("John Doe", "john.doe", "1234567890");
        contacts = List.of(contact);
    }

    @Test
    @DisplayName("Add contact with valid contact should add contact")
    void addContact_WithValidContact_ShouldAddContact() {
        when(contactRepository.getContacts()).thenReturn(contacts);
        contactService.addContact(contact.getName(), contact.getEmail(), contact.getPhoneNumber());
        verify(contactRepository).addContact(any(Contact.class));
        assertEquals(1, contactRepository.getContacts().size());
    }


}
