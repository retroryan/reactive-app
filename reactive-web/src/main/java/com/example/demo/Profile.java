package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // <2>
@AllArgsConstructor
@NoArgsConstructor
class Profile {
    private String id;
    private String email;
}
