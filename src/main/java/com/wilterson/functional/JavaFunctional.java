package com.wilterson.functional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.val;

public class JavaFunctional {

    public static void main(String[] args) {

        val javaFunctional = new JavaFunctional();

        val filteredItems = javaFunctional.loadFromSomewhere()
                .stream()
                .filter(item -> Integer.parseInt(item.substring(3)) > 4)
                .limit(5)
                .toList();

        System.out.println(filteredItems);
    }

    private Function<Person, String> loadFirstName() {

    }

    private List<String> loadFromSomewhere() {
        return Stream.of("str1", "str2", "str3", "str4", "str5", "str6", "str7", "str8", "str9", "str10", "str11", "str12", "str13", "str14", "str15").toList();
    }
}
