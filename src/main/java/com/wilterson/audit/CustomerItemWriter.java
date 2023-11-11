package com.wilterson.audit;

import com.wilterson.entity.Customer;
import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class CustomerItemWriter extends JdbcBatchItemWriter<Customer> {

    public CustomerItemWriter(DataSource dataSource) {
        setSql("insert into customer (id, first_name, last_name) values (:id, :firstName, :lastName)");
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        setJdbcTemplate(new NamedParameterJdbcTemplate(dataSource));
    }
}
