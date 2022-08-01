package com.codingbat.appcodingbat.service;

import com.codingbat.appcodingbat.entity.Problem;
import com.codingbat.appcodingbat.entity.Solution;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.payload.SolutionDto;
import com.codingbat.appcodingbat.repository.ProblemRepository;
import com.codingbat.appcodingbat.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolutionService {
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private ProblemRepository problemRepository;

    public List<Solution> getSolutions() {
        return solutionRepository.findAll();
    }

    public Solution getSolution(Integer id) {
        return solutionRepository.findById(id).orElse(null);
    }

    public ApiResponse addSolution(SolutionDto solutionDto){
        Optional<Problem> optionalProblem = problemRepository.findById(solutionDto.getProblemId());
        if (!optionalProblem.isPresent()) return new ApiResponse("Problem not found",false);

        boolean exists = solutionRepository.existsByProblemId(solutionDto.getProblemId());
        if (exists) return new ApiResponse("There is a solution to this problem",false);

        Solution solution = new Solution();
        solution.setBody(solutionDto.getBody());
        solution.setProblem(optionalProblem.get());
        solutionRepository.save(solution);

        return new ApiResponse("Solution seccessfully saved",true);
    }

    public ApiResponse editSolution(Integer id, SolutionDto solutionDto){
        Optional<Solution> optionalSolution = solutionRepository.findById(id);
        if (!optionalSolution.isPresent()) return new ApiResponse("Solution not found",false);

        Optional<Problem> optionalProblem = problemRepository.findById(solutionDto.getProblemId());
        if (!optionalProblem.isPresent()) return new ApiResponse("Problem not found",false);

        boolean exists = solutionRepository.existsByProblemId(solutionDto.getProblemId());
        if (!exists) return new ApiResponse("Problem not found",false);

        boolean exists1 = solutionRepository.existsByBodyAndProblemIdAndIdNot(solutionDto.getBody(), solutionDto.getProblemId(), id);
        if (exists1) return new ApiResponse("There is such a solution",false);

        Solution solution = optionalSolution.get();
        solution.setBody(solutionDto.getBody());
        solution.setProblem(optionalProblem.get());
        solutionRepository.save(solution);

        return new ApiResponse("Solution seccessfully edited",true);
    }

    public ApiResponse deleteSolution(Integer id){
        Optional<Solution> optionalSolution = solutionRepository.findById(id);
        if (!optionalSolution.isPresent()) return new ApiResponse("Solution not found",false);

        solutionRepository.delete(optionalSolution.get());
        return new ApiResponse("Solution seccessfully deleted",true);
    }




}
