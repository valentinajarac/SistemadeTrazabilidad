package com.fruitsales.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
}