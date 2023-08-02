package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public Main(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("create table sometable(id int primary key)");
        jdbcTemplate.execute("insert into sometable (id) values (1)");
        Integer result = jdbcTemplate.query("select id from sometable", rs -> rs.next() ? rs.getInt(1) : null);
        System.out.println(result);
    }

    @Configuration
    static class Config {
        @Bean
        JdbcConnectionDetails jdbcConnectionDetails() {
            return new JdbcConnectionDetails() {
                @Override
                public String getUsername() {
                    return "sa";
                }

                @Override
                public String getPassword() {
                    return "";
                }

                @Override
                public String getJdbcUrl() {
                    return "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=4";
                }
            };
        }
    }
}