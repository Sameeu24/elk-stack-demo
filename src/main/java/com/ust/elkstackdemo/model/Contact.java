package com.ust.elkstackdemo.model;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    private String name;
    private String email;
    private String phoneNumber;


}
