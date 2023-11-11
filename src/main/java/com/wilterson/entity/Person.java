package com.wilterson.entity;

import java.io.Serializable;

public record Person(Long id, String firstName, String lastName, String status) implements Serializable {

}