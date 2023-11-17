package com.wilterson.entity;

import java.io.Serializable;

public record PersonSkip(Long id, String firstName, String lastName) implements Serializable {

}