package com.example.demo.TheGioiDen.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String Email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}