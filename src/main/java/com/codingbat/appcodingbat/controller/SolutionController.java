package com.codingbat.appcodingbat.controller;

import com.codingbat.appcodingbat.entity.Solution;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.payload.SolutionDto;
import com.codingbat.appcodingbat.service.SolutionService;
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
@RequestMapping("/api/solution")
public class SolutionController {
    @Autowired
    private SolutionService solutionService;


    @GetMapping
    public ResponseEntity<List<Solution>> getSolutions(){
        return ResponseEntity.ok(solutionService.getSolutions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solution> getSolution(@PathVariable Integer id){
        return ResponseEntity.ok(solutionService.getSolution(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addSolution(@RequestBody SolutionDto solutionDto){
        ApiResponse apiResponse = solutionService.addSolution(solutionDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editSolution(@PathVariable Integer id, @RequestBody SolutionDto solutionDto){
        ApiResponse apiResponse = solutionService.editSolution(id,solutionDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSolution(@PathVariable Integer id){
        ApiResponse apiResponse = solutionService.deleteSolution(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
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
