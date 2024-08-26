package com.ust.elkstackdemo.repository;

import com.ust.elkstackdemo.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class ContactRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactRepository.class);
    private final List<Contact> contacts;

    public ContactRepository() {
        contacts = new LinkedList<>();
        LOGGER.debug("ContactRepository initialized");
    }

    /**
     * Add a new contact to the repository. The contact must have a name and a phone number.
     * Check if the contact already exists in the repository, if it does, do not add it but
     * log a warning and throw an exception.
     * @param contact The contact to add.
     */
    public void addContact(Contact contact) {
        if (contact.getName() == null || contact.getName().isEmpty()) {
            LOGGER.warn("Contact name is empty");
            throw new IllegalArgumentException("Contact name is empty");
        }
        if (contact.getPhoneNumber() == null || contact.getPhoneNumber().isEmpty()) {
            LOGGER.warn("Contact phone number is empty");
            throw new IllegalArgumentException("Contact phone number is empty");
        }
        contacts.stream()
                .filter(c ->    c.getName().equals(contact.getName()) &&
                                c.getPhoneNumber().equals(contact.getPhoneNumber()) &&
                                c.getEmail().equals(contact.getEmail())
                )
                .findAny()
                .ifPresent(c -> {
                    LOGGER.warn("Contact already exists: {}", contact);
                    throw new IllegalArgumentException("Contact already exists");
                });
        contacts.add(contact);
        LOGGER.debug("Contact added: {}", contact);
    }

    /**
     * Get all contacts in the repository. The list is a copy of the internal list.
     * If the repository is empty, an empty list is returned.
     */
    public List<Contact> getContacts() {
        if (contacts.isEmpty()) {
            LOGGER.warn("No contacts in repository");
        }
        return List.copyOf(contacts);
    }

    /**
     * Get a copy of list of contacts by name. If the contact does not exist, return an empty list.
     * @param name The name of the contact to get.
     */
    public List<Contact> getContactsByName(String name) {
        if(name == null || name.isEmpty()) {
            LOGGER.warn("Name is null or empty");
            throw new IllegalArgumentException("Name is null or empty.");
        }
        List<Contact> contactsByName = new LinkedList<>();
        contacts.stream()
                .filter(c -> c.getName().equals(name))
                .forEach(contactsByName::add);
        if (contactsByName.isEmpty()) {
            LOGGER.warn("No contacts with name: {}", name);
        }
        return List.copyOf(contactsByName);
    }

    /**
     * Get a copy of list of contacts by phone number. If the contact does not exist, return an empty list.
     *
     * @param phoneNumber The phone number of the contact to get.
     *                    Cannot be more the 10 characters long.
     *                    Must be a number.
     *
     */
    public List<Contact> getContactsByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() > 10) {
            LOGGER.warn("Invalid phone number: {}", phoneNumber);
            throw new IllegalArgumentException("Invalid phone number");
        }
        if (!phoneNumber.matches("[0-9]+")) {
            LOGGER.warn("Phone number is not a number: {}", phoneNumber);
            throw new IllegalArgumentException("Phone number is not a number");
        }
        List<Contact> contactsByPhoneNumber = new LinkedList<>();
        contacts.stream()
                .filter(c -> c.getPhoneNumber().contains(phoneNumber))
                .forEach(contactsByPhoneNumber::add);
        if (contactsByPhoneNumber.isEmpty()) {
            LOGGER.warn("No contacts with phone number: {}", phoneNumber);
        }
        return List.copyOf(contactsByPhoneNumber);
    }

    /**
     * Remove a contact based on the phone number. If the contact does not exist, log a warning.
     * @param phoneNumber The phone number of the contact to remove.
     */
    public void removeContactByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() > 10) {
            LOGGER.warn("Invalid phone number: {}", phoneNumber);
            throw new IllegalArgumentException("Invalid phone number");
        }
        if (!phoneNumber.matches("[0-9]+")) {
            LOGGER.warn("Phone number is not a number: {}", phoneNumber);
            throw new IllegalArgumentException("Phone number is not a number");
        }
        var removed = contacts.removeIf(c -> c.getPhoneNumber().equals(phoneNumber));
        if (!removed) {
            LOGGER.warn("No contact with phone number: {}", phoneNumber);
             throw new IllegalArgumentException("Invalid phone number");

        }
        LOGGER.debug("Contact removed by phone number: {}", phoneNumber);
    }

}
