package com.codingbat.appcodingbat.service;

import com.codingbat.appcodingbat.entity.Modul;
import com.codingbat.appcodingbat.entity.Problem;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.repository.ModulRepository;
import com.codingbat.appcodingbat.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ModulService {
    @Autowired
    private ModulRepository modulRepository;
    @Autowired
    private ProblemRepository problemRepository;

    public List<Modul> getModuls() {
        return modulRepository.findAll();
    }

    public Modul getModul(Integer id) {
        return modulRepository.findById(id).orElse(null);
    }

    public ApiResponse addModul(String modulName) {
        boolean exists = modulRepository.existsByName(modulName);
        if (exists) return new ApiResponse("There is such a modul", false);

        Modul modul = new Modul();
        modul.setName(modulName);
        modul.setProblems(new HashSet<>());
        modulRepository.save(modul);

        return new ApiResponse("Modul seccessfully saved", true);
    }

    public ApiResponse editModul(Integer id, String modulName) {
        Optional<Modul> optionalModul = modulRepository.findById(id);
        if (!optionalModul.isPresent()) return new ApiResponse("Modul not found", false);

        boolean exists = modulRepository.existsByNameAndIdNot(modulName, id);
        if (exists) return new ApiResponse("There is such a modul", false);

        Modul modul = optionalModul.get();
        modul.setName(modulName);
        modulRepository.save(modul);

        return new ApiResponse("Modul seccessfully edited", true);
    }

    public ApiResponse deleteModul(Integer id) {
        Optional<Modul> optionalModul = modulRepository.findById(id);
        if (!optionalModul.isPresent()) return new ApiResponse("Modul not found", false);

        modulRepository.delete(optionalModul.get());
        return new ApiResponse("Modul seccessfully deleted", true);
    }

    public Set<Problem> getModulProblems(Integer id) {
        Optional<Modul> optionalModul = modulRepository.findById(id);
        return optionalModul.map(Modul::getProblems).orElse(null);

    }

    public ApiResponse addProblemToModul(Integer modulId, Integer problemId) {
        Optional<Modul> optionalModul = modulRepository.findById(modulId);
        if (!optionalModul.isPresent()) return new ApiResponse("Modul not found", false);

        Optional<Problem> optionalProblem = problemRepository.findById(problemId);
        if (!optionalProblem.isPresent()) return new ApiResponse("Problem not found", false);

        Modul modul = optionalModul.get();
        Set<Problem> problems = modul.getProblems();
        problems.add(optionalProblem.get());
        modul.setProblems(problems);
        modulRepository.save(modul);

        return new ApiResponse("Problem seccessfully saved", true);
    }

    public ApiResponse editProblemModul(Integer modulId, Integer problemId, Integer newProblemId) {
        Optional<Modul> optionalModul = modulRepository.findById(modulId);
        if (!optionalModul.isPresent()) return new ApiResponse("Modul not found", false);

        Optional<Problem> optionalProblem = problemRepository.findById(problemId);
        if (!optionalProblem.isPresent()) return new ApiResponse("Problem not found", false);

        Optional<Problem> optionalProblem1 = problemRepository.findById(newProblemId);
        if (!optionalProblem1.isPresent()) return new ApiResponse("New problem not found", false);

        Modul modul = optionalModul.get();
        Set<Problem> problems = modul.getProblems();
        problems.remove(optionalProblem.get());
        problems.add(optionalProblem1.get());
        modul.setProblems(problems);
        modulRepository.save(modul);

        return new ApiResponse("Problem seccessfully edited", true);
    }

    public ApiResponse deleteProblemModul(Integer modulId, Integer problemId) {
        Optional<Modul> optionalModul = modulRepository.findById(modulId);
        if (!optionalModul.isPresent()) return new ApiResponse("Modul not found", false);

        Optional<Problem> optionalProblem = problemRepository.findById(problemId);
        if (!optionalProblem.isPresent()) return new ApiResponse("Problem not found", false);

        Modul modul = optionalModul.get();
        Set<Problem> problems = modul.getProblems();
        problems.remove(optionalProblem.get());
        modul.setProblems(problems);
        modulRepository.save(modul);

        return new ApiResponse("Problem seccessfully deleted", true);
    }
}
