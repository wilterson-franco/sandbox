package com.wilterson.shared;

import com.wilterson.entity.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper<Person> {

    public static final String ID_COLUMN = "id";
    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";

    public static final String STATUS_COLUMN = "status";

    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(rs.getLong(ID_COLUMN), rs.getString(FIRST_NAME_COLUMN), rs.getString(LAST_NAME_COLUMN), rs.getString(STATUS_COLUMN));
    }
}

