package com.wilterson;

import java.util.List;

public record Pet(String name, String color, PetKind petKind, Pet friend, List<Person> owners) {

}
