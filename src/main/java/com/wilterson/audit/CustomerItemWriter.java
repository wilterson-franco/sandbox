package com.wilterson.audit;

import com.wilterson.entity.Customer;
import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;

public class CustomerItemWriter extends JdbcBatchItemWriter<Customer> {

    public JdbcBatchItemWriter<Customer> getItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .sql("insert into customer (id, first_name, last_name) values (:id, :firstName, :lastName)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }
}
