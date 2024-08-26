package com.ust.elkstackdemo.service;

import com.ust.elkstackdemo.model.Contact;
import com.ust.elkstackdemo.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    /**
     * Use Mockito to mock the ContactRepository object
     * <p>
     * Test cases to be generated
     * 1. addContact_WithValidContact_ShouldAddContact
     * 2. addContact_WithNullName_ShouldThrowException
     * 3. addContact_WithEmptyName_ShouldThrowException
     * 4. addContact_WithNullPhoneNumber_ShouldThrowException
     *
     *
     * 5. addContact_WithEmptyPhoneNumber_ShouldThrowException
     * 6. addContact_WithExistingContact_ShouldThrowException
     */

    @Mock
    ContactRepository contactRepository;

    @InjectMocks
    ContactService contactService;

    List<Contact> contacts;
    Contact contact;
    Contact nullname;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contact = new Contact("John Doe", "john.doe", "1234567890");
        nullname = new Contact(null, "john.doe", "1234567890");
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

    @Test
    @DisplayName("Add contact with null name should throw exception")
    void addContact_WithNullName_ShouldThrowException() {
        doThrow(new IllegalArgumentException("Contact name is empty"))
                .when(contactRepository)
                .addContact(any(Contact.class));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactService.addContact(null, "john.doe@example.com", "1234567890")
        );
        assertEquals("Contact name is empty", exception.getMessage());
    }
    @Test
    @DisplayName("Add contact with empty name should throw exception")
    void addContact_WithEmptyName_ShouldThrowException() {
        doThrow(new IllegalArgumentException("Contact name is empty"))
                .when(contactRepository)
                .addContact(any(Contact.class));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactService.addContact("", "john.doe@example.com", "1234567890")
        );
        assertEquals("Contact name is empty", exception.getMessage());
    }
    @Test
    @DisplayName("Add contact with null number should throw exception")
    void addContact_WithNullPhoneNumber_ShouldThrowException() {
        doThrow(new IllegalArgumentException("Contact number is empty"))
                .when(contactRepository)
                .addContact(any(Contact.class));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactService.addContact("Jane Doe", "john.doe@example.com", null)
        );
        assertEquals("Contact number is empty", exception.getMessage());
    }
    @Test
    @DisplayName("Add contact with empty number should throw exception")
    void addContact_WithEmptyPhoneNumber_ShouldThrowException() {
        doThrow(new IllegalArgumentException("Contact number is empty"))
                .when(contactRepository)
                .addContact(any(Contact.class));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contactService.addContact("Jane Doe", "john.doe@example.com", "1234567890")
        );
        assertEquals("Contact number is empty", exception.getMessage());
    }
    @Test
    @DisplayName("Add contact with existing contact should throw exception")
    void addContact_WithExistingContact_ShouldThrowException() {
        // Arrange
        // Mock the behavior of contactRepository to throw an exception when addContact is called
        doThrow(new IllegalArgumentException("Contact already exists"))
                .when(contactRepository)
                .addContact(any(Contact.class));

        // Use assertThrows to check if the correct exception is thrown
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                {
                    contactService.addContact(contact.getName(), contact.getEmail(), contact.getPhoneNumber());
                });
        assertEquals("Contact already exists", exception.getMessage());
    }



}
