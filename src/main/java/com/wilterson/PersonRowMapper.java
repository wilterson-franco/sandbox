package com.wilterson;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper<Person> {

    public static final String ID_COLUMN = "id";
    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";

    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(rs.getLong(ID_COLUMN), rs.getString(FIRST_NAME_COLUMN), rs.getString(LAST_NAME_COLUMN));
    }
}

