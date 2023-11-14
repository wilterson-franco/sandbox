# Overview
This application uses Spring Batch to move data from one DB table to another. The only processing it does, before moving the data, is changing the data to upper case. The DB access is implemented with JDBC template and reads 10 items at a time (configurable, of course). Items not processed are picked up on the next Job run.

This application has the web layer just to give me a chance to log into the H2 console to query the data. It is not a web application.

## Notes
- The `@EnableBatchProcessing` annotation activates the Spring Boot auto-configuration which in turn sets up all the Spring Batch infrastructure.
- Spring Boot will run the Job for us. It will look for any Job in the Spring application context when the Java application is stated and will just run them. You can explicitly kick them off, perhaps in response to an event, but the default behaviour with Spring Boot auto-configuration is to run the Job on application startup.

## Spring Initializr
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.5&packaging=jar&jvmVersion=17&groupId=com.wilterson&artifactId=first-spring-batch&name=first-spring-batch&description=Spring%20Batch%20first%20project&packageName=com.wilterson&dependencies=batch,h2,web,lombok,data-jpa

## TODO
- The data must be in a Postgres DB
- The batch job must be triggered by an event, say new data added to the source table


## References
- [Spring Batch Guid: Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)
- [Josh Long YouTube Video](https://www.youtube.com/watch?v=x4nBNLoizOc)
- [Spring Batch Page](https://spring.io/batch)
- [Spring Batch Documentation](https://spring.io/projects/spring-batch#learn)
- [Spring Batch 5.0 Migration Guide](https://github.com/spring-projects/spring-batch/wiki/Spring-Batch-5.0-Migration-Guide)
- [Spring Batch Reference Documentation](https://docs.spring.io/spring-batch/docs/current/reference/html/)
- [Cursor-based ItemReader Implementations](https://docs.spring.io/spring-batch/docs/current/reference/html/readersAndWriters.html#cursorBasedItemReaders)
- [Spring Batch GitHub with lots of info and exemples](https://github.com/spring-projects/spring-batch/tree/main)

# Merchant Service
Switching the Audit logic to a Batch Job turns it into a asynchronous flow, decoupled from the main flow of onboarding a sub-merchant or changing a merchant's status to LIVE. Here, I'm going to try to describe how this batch job should be designed:

## The New DB tables

### AUDIT_BATCH_STAGE

| NAME             | TYPE        | NULLABLE  | DEFAULT VALUE |
|------------------|-------------|-----------|---------------|
| id               | bigint      | no        |               |
| created_datetime | time        | no        |               |
| left_entity      | JSON        | no        |               |
| right_entity     | JSON        | no        |               |
| status           | varchar(10) | no        | NEW           |

This is the table where CMS will store the old and the new version of the whole merchant graph. This data will be the input of the batch job. The status column can have the following values:

- NEW: rows inserted by CMS must be automatically inserted as NEW. This status indicates that the row hasn't been processed yet;
- PROCESSING: the row has already been picked up by the batch jon. The processing is in progress, it hasn't finished yet;

### AUDIT_BATCH_SKIP

| NAME             | TYPE        | NULLABLE  | DEFAULT VALUE |
|------------------|-------------|-----------|---------------|
| id               | bigint      | no        |               |
| created_datetime | time        | no        |               |
| left_entity      | JSON        | no        |               |
| right_entity     | JSON        | no        |               |

This table stores the records that failed to be processed. The items on this table must be fixed before they are moved back to the AUDIT_BATCH_STAGE for reprocessing.

## The CMS Responsibility

- CMS will onboard a sub-merchant and before finishing the operation, it will add a new row with status `NEW` to the `AUDIT_BATCH_STAGE` table;

## The New Batch Job

- Must run every 15 minutes

- Must take as a job parameter as datetime, the `olderThan`. Only rows that has status equal to `NEW` and are older than the `olderThan` job parameter value must be picked up by the reader;

- Must process the rows in chunks as a `ChunkOrientedTasklet` Step with an ItemReader, one ItemProcessor, and an ItemWriter;

### Step 1: The Reader

- Must read the `AUDIT_BATCH_STAGE` rows and return an `AuditableMerchant` for each row. This object must be the entity that represents this table rows. It must have fields that maps to the database table columns;

#### The `AuditableMerchant` Model Class

This class will have the following fields:

- `Merchant left`
- `Merchant right`
- `AuditCommit auditCommit`
- `List<AuditMetadata> auditMetadataItems`
- `List<AuditItem> auditItems`

Each Audit processor is supposed to enhance this object by building their respective fields.

### Step 1: The Status Change Processor (#1)

- Must change the status of each `AuditableMerchant` to `PROCESSING`. This will prevent concurrent jobs from picking up the same rows;

### Step 1: The Audit Commit Processor (#2)

- Must enrich the `AuditableMerchant` object with the `AuditCommit` object

- Must take the `AuditableMerchant.leftEntity` and compare it with the `AuditableMerchant.rightEntity` using Javers diff. The processor's goal is to return an `AuditCommit` object with the `AuditMetadata` and the `AuditItem` objects;

### Step 1: The Writer

- Must commit the `AuditCommit`, as well as the children objects, to the DB;

- The batch job must have an additional step, the `DeleteFinishedAudit`. It must delete all the `AUDIT_BATCH_STAGE` rows that have status `PROCESSED` from the DB