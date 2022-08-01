package com.codingbat.appcodingbat.service;

import com.codingbat.appcodingbat.entity.Problem;
import com.codingbat.appcodingbat.entity.Solution;
import com.codingbat.appcodingbat.entity.User;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.payload.SolutionDto;
import com.codingbat.appcodingbat.payload.UserDto;
import com.codingbat.appcodingbat.repository.ProblemRepository;
import com.codingbat.appcodingbat.repository.SolutionRepository;
import com.codingbat.appcodingbat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private ProblemRepository problemRepository;


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public ApiResponse addUser(UserDto userDto) {
        boolean exists = userRepository.existsByEmail(userDto.getEmail());
        if (exists) return new ApiResponse("There is such a user", false);

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setSolutions(new HashSet<>());
        userRepository.save(user);

        return new ApiResponse("User seccessfully saved", true);
    }

    public ApiResponse editUser(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) return new ApiResponse("User not found", false);

        User user = optionalUser.get();
        user.setPassword(userDto.getPassword());
        userRepository.save(user);

        return new ApiResponse("User seccessfully edited", true);
    }

    public ApiResponse deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) return new ApiResponse("User not found", false);

        userRepository.delete(optionalUser.get());
        return new ApiResponse("User seccessfully deleted", true);
    }


    public Set<Solution> getUserSolutions(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(User::getSolutions).orElse(null);
    }

    public Solution getUserSolution(Integer userId, Integer solutionId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) return null;

        User user = optionalUser.get();
        Set<Solution> solutions = user.getSolutions();
        Solution solution = null;

        for (Solution solution1 : solutions) {
            if ((solution1.getId().equals(solutionId))) {
                solution = solution1;
                break;
            }
        }

        return solution;
    }

    public ApiResponse addSolutinToUser(Integer userId, SolutionDto solutionDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) return new ApiResponse("User not found", false);

        Optional<Problem> optionalProblem = problemRepository.findById(solutionDto.getProblemId());
        if (!optionalProblem.isPresent()) return new ApiResponse("Problem not found", false);

        User user = optionalUser.get();
        Set<Solution> solutions = user.getSolutions();

        Solution newSolution = new Solution();
        newSolution.setBody(solutionDto.getBody());
        newSolution.setProblem(optionalProblem.get());
        solutions.add(newSolution);

        user.setSolutions(solutions);
        return new ApiResponse("User solution seccessfully saved", true);
    }

    public ApiResponse editUserSolution(Integer userId, Integer solutionId, String body) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) return new ApiResponse("User not found", false);

        User user = optionalUser.get();
        Set<Solution> solutions = user.getSolutions();
        Solution solution = null;

        for (Solution sol : solutions) {
            if (sol.getId().equals(solutionId)) {
                solution = sol;
                solutions.remove(sol);
                break;
            }
        }

        if (Objects.isNull(solution))
            return new ApiResponse("User solution not found", false);

        solution.setBody(body);
        solutions.add(solution);
        user.setSolutions(solutions);
        userRepository.save(user);

        return new ApiResponse("User solution seccessfully edited", true);
    }

    public ApiResponse deleteUserSolution(Integer userId, Integer solutionId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) return new ApiResponse("User not found", false);

        User user = optionalUser.get();
        Set<Solution> solutions = user.getSolutions();
        Solution solution = null;

        for (Solution sol : solutions) {
            if (sol.getId().equals(solutionId)) {
                solution = sol;
                break;
            }
        }

        try {
            solutions.remove(solution);
        } catch (Exception e) {
            return new ApiResponse("User solution not found", false);
        }

        user.setSolutions(solutions);
        userRepository.save(user);

        return new ApiResponse("User solution seccessfully deleted", true);
    }
}
