package com.ust.elkstackdemo.repository;

import com.ust.elkstackdemo.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactRepositoryTest {

    private ContactRepository contactRepository;

    @BeforeEach
    void setUp() {
        contactRepository = new ContactRepository();
    }

    @Test
    @DisplayName("Add contact with valid contact should add contact")
    void addContact_WithValidContact_ShouldAddContact() {
        Contact contact = new Contact("John Doe", "1234567890", "john.doe@example.com");
        contactRepository.addContact(contact);
        List<Contact> contacts = contactRepository.getContacts();
        assertEquals(1, contacts.size());
        assertEquals(contact, contacts.get(0));
    }

    @Test
    @DisplayName("Add contact with null name should throw exception")
    void addContact_WithNullName_ShouldThrowException() {
        Contact contact = new Contact(null, "1234567890", "john.doe@example.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.addContact(contact));
        assertEquals("Contact name is empty", exception.getMessage());
    }

    @Test
    @DisplayName("Add contact with empty name should throw exception")
    void addContact_WithEmptyName_ShouldThrowException() {
        Contact contact = new Contact("", "1234567890", "john.doe@example.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.addContact(contact));
        assertEquals("Contact name is empty", exception.getMessage());
    }

    @Test
    @DisplayName("Add contact with null phone number should throw exception")
    void addContact_WithNullPhoneNumber_ShouldThrowException() {
        Contact contact = new Contact("John Doe", "john.doe@example.com", null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.addContact(contact));
        assertEquals("Contact phone number is empty", exception.getMessage());
    }

    @Test
    @DisplayName("Add contact with empty phone number should throw exception")
    void addContact_WithEmptyPhoneNumber_ShouldThrowException() {
        Contact contact = new Contact("John Doe", "john.doe@example.com", "");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.addContact(contact));
        assertEquals("Contact phone number is empty", exception.getMessage());
    }

    @Test
    @DisplayName("Add contact with existing contact should throw exception")
    void addContact_WithExistingContact_ShouldThrowException() {
        Contact contact = new Contact("John Doe", "john.doe@example.com", "1234567890");
        contactRepository.addContact(contact);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.addContact(contact));
        assertEquals("Contact already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Get contacts with no contacts should return empty list")
    void getContacts_WithNoContacts_ShouldReturnEmptyList() {
        List<Contact> contacts = contactRepository.getContacts();
        assertTrue(contacts.isEmpty());
    }

    @Test
    @DisplayName("Get contacts with contacts should return all contacts")
    void getContacts_WithContacts_ShouldReturnAllContacts() {
        Contact contact1 = new Contact("John Doe", "john.doe@example.com", "1234567890");
        Contact contact2 = new Contact("Jane Doe", "jane.doe@example.com", "0987654321");
        contactRepository.addContact(contact1);
        contactRepository.addContact(contact2);
        List<Contact> contacts = contactRepository.getContacts();
        assertEquals(2, contacts.size());
        assertTrue(contacts.contains(contact1));
        assertTrue(contacts.contains(contact2));
    }

    @Test
    @DisplayName("Get contacts by phone number with existing phone number should return contact")
    void getContactsByPhoneNumber_WithExistingPhoneNumber_ShouldReturnContact() {
        Contact contact = new Contact("John Doe", "john.doe@example.com", "1234567890");
        contactRepository.addContact(contact);
        List<Contact> contacts = contactRepository.getContactsByPhoneNumber("1234567890");
        assertEquals(1, contacts.size());
        assertEquals(contact, contacts.get(0));
    }

    @Test
    @DisplayName("Get contacts by phone number with non-existing phone number should return empty list")
    void getContactsByPhoneNumber_WithNonExistingPhoneNumber_ShouldReturnEmptyList() {
        List<Contact> contacts = contactRepository.getContactsByPhoneNumber("0000000000");
        assertTrue(contacts.isEmpty());
    }

    @Test
    @DisplayName("Get contacts by name with existing name should return contact")
    void getContactsByName_WithExistingName_ShouldReturnContact() {
        Contact contact = new Contact("John Doe", "1234567890", "john.doe@example.com");
        contactRepository.addContact(contact);
        List<Contact> contacts = contactRepository.getContactsByName("John Doe");
        assertEquals(1, contacts.size());
        assertEquals(contact, contacts.get(0));
    }

    @Test
    @DisplayName("Get contacts by name with non-existing name should return empty list")
    void getContactsByName_WithNonExistingName_ShouldReturnEmptyList() {
        List<Contact> contacts = contactRepository.getContactsByName("Non Existing");
        assertTrue(contacts.isEmpty());
    }

    @Test
    @DisplayName("Remove contact by phone number with existing phone number should remove contact")
    void removeContactByPhoneNumber_WithExistingPhoneNumber_ShouldRemoveContact() {
        Contact contact = new Contact("John Doe", "john.doe@example.com", "1234567890");
        contactRepository.addContact(contact);
        contactRepository.removeContactByPhoneNumber("1234567890");
        List<Contact> contacts = contactRepository.getContacts();
        assertTrue(contacts.isEmpty());
    }


    @Test
    @DisplayName("Remove contact by phone number with non-existing phone number should throw exception")
    void removeContactByPhoneNumber_WithNonExistingPhoneNumber_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.removeContactByPhoneNumber("0000000000"));
        assertEquals("Invalid phone number", exception.getMessage());
    }

    @Test
    @DisplayName("Remove contact by phone number with longer phone number should throw exception")
    void removeContactByPhoneNumber_WithLongerPhoneNumber_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.removeContactByPhoneNumber("12345678901"));
        assertEquals("Invalid phone number", exception.getMessage());
    }

    @Test
    @DisplayName("Remove contact by phone number with null phone number should throw exception")
    void removeContactByPhoneNumber_WithNullPhoneNumber_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.removeContactByPhoneNumber(null));
        assertEquals("Invalid phone number", exception.getMessage());
    }

    @Test
    @DisplayName("Remove contact by phone number with non-numeric phone number should throw exception")
    void removeContactByPhoneNumber_WithNonNumericPhoneNumber_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.removeContactByPhoneNumber("abc123"));
        assertEquals("Phone number is not a number", exception.getMessage());
    }

    @Test
    @DisplayName("Get contacts by phone number with non-numeric phone number should throw exception")
    void getContactsByPhoneNumber_WithNonNumericPhoneNumber_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.getContactsByPhoneNumber("abc123"));
        assertEquals("Phone number is not a number", exception.getMessage());
    }


    @Test
    @DisplayName("Get contacts by name with null name should throw exception")
    void getContactsByName_WithNullName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.getContactsByName(null));
        assertEquals("Name is null or empty.", exception.getMessage());
    }


    @Test
    @DisplayName("Get contacts by name with empty name should throw exception")
    void getContactsByName_WithEmptyName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> contactRepository.getContactsByName(""));
        assertEquals("Name is null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Additional:Get contacts by phone number greater than 10 digits")
    void getContactByPhoneNumber_WithGreaterThan10Digits() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            contactRepository.getContactsByPhoneNumber("12345678911");
        });
        assertEquals("Invalid phone number",exception.getMessage());
    }
}


/**
 * Test cases to be generated
 *
 * 1. addContact_WithValidContact_ShouldAddContact done
 * 2. addContact_WithNullName_ShouldThrowException
 * 3. addContact_WithEmptyName_ShouldThrowException
 * 4. addContact_WithNullPhoneNumber_ShouldThrowException
 * 5. addContact_WithEmptyPhoneNumber_ShouldThrowException
 * 6. addContact_WithExistingContact_ShouldThrowException
 * 7. getContacts_WithNoContacts_ShouldReturnEmptyList
 * 8. getContacts_WithContacts_ShouldReturnAllContacts
 * 9. getContactsByPhoneNumber_WithExistingPhoneNumber_ShouldReturnContact
 * 10. getContactsByPhoneNumber_WithNonExistingPhoneNumber_ShouldReturnEmptyList
 * 11. getContactsByName_WithExistingName_ShouldReturnContact
 * 12. getContactsByName_WithNonExistingName_ShouldReturnEmptyList
 * 13. removeContactByPhoneNumber_WithExistingPhoneNumber_ShouldRemoveContact
 * 14. removeContactByPhoneNumber_WithNonExistingPhoneNumber_ShouldThrowException
 * 15. removeContactByPhoneNumber_WithLongerPhoneNumber_ShouldThrowException
 * 16. removeContactByPhoneNumber_WithNullPhoneNumber_ShouldThrowException
 * 17. removeContactByPhoneNumber_WithNonNumericPhoneNumber_ShouldThrowException
 * 18. getContactsByPhoneNumber_WithNonNumericPhoneNumber_ShouldThrowException
 * 19. getContactsByName_WithNullName_ShouldThrowException
 * 20. getContactsByName_WithEmptyName_ShouldThrowException
 *
 * Instructions:
 * 1. While checking for exceptions, ensure the correct exception object is throw
 * and the exception message is correct.
 *
 * 2. Use the DisplayName annotation to give a meaningful name to the test case.
 *
 * 3. Use the @BeforeEach annotation to initialize the ContactRepository object.
 *
 * 4. Ensure code coverage of the ContactRepository class is 100%.
 */
