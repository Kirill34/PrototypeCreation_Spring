package com.example.demo;

import com.example.demo.controller.UserController;
import model.Answer;
import model.Problem;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import repo.AnswerRepository;
import repo.ProblemRepository;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@SpringBootApplication(scanBasePackageClasses = {AnswerRepository.class})
@EntityScan(basePackageClasses = {Answer.class, Problem.class})
@ComponentScan(basePackageClasses = {UserController.class})
@EnableJpaRepositories(basePackageClasses = {AnswerRepository.class})
@Configuration
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @PostMapping("/postbody")
    public String postBody(@RequestBody String fullName) {
        return "Hello " + fullName;
    }

    @GetMapping("/getbody")
    public String getBody(@RequestBody String fullName)
    {
        return "Hello "+fullName;
    }

}
