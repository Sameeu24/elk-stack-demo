package com.ust.elkstackdemo.service;

import com.ust.elkstackdemo.model.Contact;
import com.ust.elkstackdemo.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
        LOGGER.debug("ContactService instantiated with contactRepository: {}", contactRepository);
    }

    public void addContact(String name, String email, String phoneNumber) {
        LOGGER.debug("Adding contact with name: {}, email: {}, phoneNumber: {}", name, email, phoneNumber);
        contactRepository.addContact(new Contact(name, email, phoneNumber));
    }

    public void removeContactByPhoneNumber(String phoneNumber) {
        LOGGER.debug("Removing contact with phoneNumber: {}", phoneNumber);
        contactRepository.removeContactByPhoneNumber(phoneNumber);
    }

    public List<Contact> getContacts() {
        LOGGER.debug("Getting all contacts");
        var cx = contactRepository.getContacts();
        if (cx.isEmpty()) {
            LOGGER.debug("No contacts found");
        }
        return cx;
    }

    public List<Contact> getContactsByPhoneNumber(String phoneNumber) {
        LOGGER.debug("Getting contact with phoneNumber: {}", phoneNumber);
        var cx = contactRepository.getContactsByPhoneNumber(phoneNumber);
        if (cx.isEmpty()) {
            LOGGER.debug("No contacts found with phoneNumber: {}", phoneNumber);
        }
        return cx;
    }

    public List<Contact> getContactsByName(String name) {
        LOGGER.debug("Getting contact with name: {}", name);
        var cx = contactRepository.getContactsByName(name);
        if (cx.isEmpty()) {
            LOGGER.debug("No contacts found with name: {}", name);
        }
        return cx;
    }
}