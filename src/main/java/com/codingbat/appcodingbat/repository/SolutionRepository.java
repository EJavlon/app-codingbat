package com.codingbat.appcodingbat.repository;

import com.codingbat.appcodingbat.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution,Integer> {

    boolean existsByProblemId(Integer problem_id);

    boolean existsByBodyAndProblemIdAndIdNot(String body, Integer problem_id, Integer id);
}
