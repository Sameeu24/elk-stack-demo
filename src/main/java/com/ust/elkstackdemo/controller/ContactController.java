package com.ust.elkstackdemo.controller;

import com.ust.elkstackdemo.model.Contact;
import com.ust.elkstackdemo.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody Contact contact) {
        log.debug("Adding contact with name: {}, email: {}, phoneNumber: {}",
                contact.getName(), contact.getEmail(), contact.getPhoneNumber());
        contactService.addContact(contact.getName(), contact.getEmail(), contact.getPhoneNumber());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    // POST /contacts/remove?num=1234567890
    public ResponseEntity<?> removeContactByPhoneNumber(@RequestParam("num") String phoneNumber) {
        log.debug("Removing contact with phoneNumber: {}", phoneNumber);
        contactService.removeContactByPhoneNumber(phoneNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getContacts() {
        log.debug("Getting all contacts");
        var list = contactService.getContacts();
        if (list.isEmpty()) {
            log.debug("No contacts found");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    // GET /contacts/search?key=1234567890&type=phone
    public ResponseEntity<?> getContactsByPhoneNumber(@RequestParam("type") String type,
                                                      @RequestParam("key") String key) {
        log.debug("Getting contact with {}: {}", type, key);
        var list = switch (type) {
            case "phone" -> contactService.getContactsByPhoneNumber(key);
            case "name" -> contactService.getContactsByName(key);
            default -> throw new IllegalArgumentException("Invalid search type: " + type);
        };
        if (list.isEmpty()) {
            log.debug("No contacts found with {}: {}", type, key);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }
}