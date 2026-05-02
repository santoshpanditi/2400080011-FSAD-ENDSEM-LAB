# fsadendexam

A Maven Hibernate project for HQL operations on the `Payment` entity.

## Package

- `com.klef.fsad.exam`

## Files

- `Payment.java` - Hibernate entity with properties: `id`, `name`, `date`, `status`, `amount`, `description`
- `ClientDemo.java` - inserts payment records and deletes a record by ID using HQL named parameters
- `hibernate.cfg.xml` - Hibernate configuration for MySQL database `fsadendexam`
- `pom.xml` - Maven build file with Hibernate and MySQL dependencies

## Setup

1. Create the database:

```sql
CREATE DATABASE fsadendexam;
```

2. Update credentials in `src/main/resources/hibernate.cfg.xml` if needed.

## Build

```powershell
cd C:\Users\Santosh\fsadendexam
mvn compile
```

## Run

```powershell
mvn exec:java -Dexec.mainClass=com.klef.fsad.exam.ClientDemo
```

> Note: Maven dependency resolution in this environment could not be verified due to repository access issues. If you have network access to Maven Central, the project files and structure are ready to compile.
