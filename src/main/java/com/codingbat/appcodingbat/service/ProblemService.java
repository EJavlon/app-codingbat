package com.codingbat.appcodingbat.service;

import com.codingbat.appcodingbat.entity.Problem;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.payload.ProblemDto;
import com.codingbat.appcodingbat.repository.ModulRepository;
import com.codingbat.appcodingbat.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ModulRepository modulRepository;


    public List<Problem> getProblems() {
        return problemRepository.findAll();
    }

    public Problem getProblem(Integer id) {
        return problemRepository.findById(id).orElse(null);
    }

    public ApiResponse addProblem(ProblemDto problemDto) {
        boolean exists = problemRepository.existsByNameAndBody(problemDto.getName(), problemDto.getBody());
        if (exists) return new ApiResponse("There is such a problem",false);

        Problem problem = new Problem();
        problem.setName(problemDto.getName());
        problem.setBody(problemDto.getBody());
        problemRepository.save(problem);

        return new ApiResponse("Problem seccessfully saved",true);
    }

    public ApiResponse editProblem(Integer id, ProblemDto problemDto) {
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        if (!optionalProblem.isPresent()) return new ApiResponse("Problem not found",false);

        boolean exists = problemRepository.existsByNameAndBodyAndIdNot(problemDto.getName(), problemDto.getBody(), id);
        if (exists) return new ApiResponse("There is such a problem",false);

        Problem problem = optionalProblem.get();
        problem.setName(problemDto.getName());
        problem.setBody(problemDto.getBody());
        problemRepository.save(problem);

        return new ApiResponse("Problem seccessfully edited",true);
    }

    public ApiResponse deleteProblem(Integer id){
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        if (!optionalProblem.isPresent()) return new ApiResponse("Problem not found",false);

        problemRepository.delete(optionalProblem.get());
        return new ApiResponse("Problem seccessfully deleted",true);
    }
}
