package com.wilterson.entity;

import java.io.Serializable;

public record Customer(Long id, String firstName, String lastName) implements Serializable {

}