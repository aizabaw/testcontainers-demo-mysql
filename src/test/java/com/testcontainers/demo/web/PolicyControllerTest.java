package com.testcontainers.demo.web;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class PolicyControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @LocalServerPort
    private Integer port;

    static final String MYSQL_IMAGE = "mysql:8.0.35";
    static final String POLICY_API_URL = "/api/policy";

    private MockMvc mockMvc;

    @ClassRule
    @Container
    @ServiceConnection
    static MySQLContainer mysql = new MySQLContainer<>(MYSQL_IMAGE)
            .withDatabaseName("test-policy-db")
            .withUsername("sa")
            .withPassword("password");

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @BeforeAll
    static void beforeAll() { mysql.start(); }

//    @AfterAll
//    static void afterAll() {
//        mysql.stop();
//    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    @Sql(scripts = "/data/insert_policy.sql", executionPhase =
            Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void givenExistingPolicy_whenRetrieveOne_thenReturnDetails() throws Exception {
        mockMvc.perform(get(POLICY_API_URL+ "/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyId").value("2"))
                .andExpect(jsonPath("$.policyNumber").value("POL24352345"))
                .andExpect(jsonPath("$.ownerFirstName").value("Jane"))
                .andExpect(jsonPath("$.ownerLastName").value("Doe"))
                .andExpect(jsonPath("$.effectivityDate").value("2022-01-03"));
    }

}
