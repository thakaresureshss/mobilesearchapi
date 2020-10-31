package com.mobile.search;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import com.mobile.search.controller.SearchController;

@SpringBootTest
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
class ApplicationTests {

  @Autowired
  private SearchController controller;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
  }

}
