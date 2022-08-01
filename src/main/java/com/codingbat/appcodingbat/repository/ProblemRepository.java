package com.codingbat.appcodingbat.repository;

import com.codingbat.appcodingbat.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem,Integer> {

    boolean existsByNameAndBody(String name, String body);

    boolean existsByNameAndBodyAndIdNot(String name, String body, Integer id);
}
