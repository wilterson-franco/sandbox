package com.wilterson;

import java.util.Collections;
import java.util.List;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PetsController {

    @QueryMapping
    List<Pet> pets() {

        Person ownerDaniel = new Person("Daniel", "647-401-9404");
        Person ownerGabriel = new Person("Gabriel", "647-706-9404");
        Person ownerSuellen = new Person("Suellen", "647-706-9404");

        Pet dogSkol = new Pet("Skoll", "white", PetKind.DOG, null, List.of(ownerDaniel, ownerGabriel));
        Pet dogLuna = new Pet("Luna", "cappuccino", PetKind.DOG, dogSkol, List.of(ownerDaniel, ownerGabriel));
        Pet catSkipper = new Pet("Skipper", "black", PetKind.CAT, null, Collections.singletonList(ownerSuellen));

        return List.of(
                dogLuna,
                dogSkol,
                catSkipper);
    }
}
