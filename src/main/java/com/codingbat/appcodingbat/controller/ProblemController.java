package com.codingbat.appcodingbat.controller;

import com.codingbat.appcodingbat.entity.Problem;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.payload.ProblemDto;
import com.codingbat.appcodingbat.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;


    @GetMapping
    public ResponseEntity<List<Problem>> getProblems() {
        return ResponseEntity.ok(problemService.getProblems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblem(@PathVariable Integer id) {
        return ResponseEntity.ok(problemService.getProblem(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProblem(@RequestBody ProblemDto problemDto) {
        ApiResponse apiResponse = problemService.addProblem(problemDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED :
                HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editProblem(@PathVariable Integer id, @RequestBody ProblemDto problemDto) {
        ApiResponse apiResponse = problemService.editProblem(id, problemDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED :
                HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProblem(@PathVariable Integer id){
        ApiResponse apiResponse = problemService.deleteProblem(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED :
                HttpStatus.CONFLICT).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
