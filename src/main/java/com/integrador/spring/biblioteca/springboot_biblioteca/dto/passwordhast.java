package com.integrador.spring.biblioteca.springboot_biblioteca.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passwordhast {
      public static void main(String[] args) {
    BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
    System.out.println(enc.encode("1234"));
  }
}
