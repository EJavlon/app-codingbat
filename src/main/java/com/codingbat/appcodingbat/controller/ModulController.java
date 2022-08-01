package com.codingbat.appcodingbat.controller;

import com.codingbat.appcodingbat.entity.Modul;
import com.codingbat.appcodingbat.entity.Problem;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.service.ModulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/modul")
public class ModulController {
    @Autowired
    private ModulService modulService;


    @GetMapping
    public ResponseEntity<List<Modul>> getModuls(){
        return ResponseEntity.ok(modulService.getModuls());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modul> getModul(@PathVariable Integer id){
        return ResponseEntity.ok(modulService.getModul(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addModul(@NotNull(message = "name cannot be empty") @RequestBody String modulName){
        ApiResponse apiResponse = modulService.addModul(modulName);
        return ResponseEntity.status(apiResponse.isSuccess() ?
                HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editModul(@PathVariable Integer id,
                                                 @NotNull(message = "name cannot be empty") @RequestBody String modulName){
        ApiResponse apiResponse = modulService.editModul(id, modulName);
        return ResponseEntity.status(apiResponse.isSuccess() ?
                HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteModul(@PathVariable Integer id){
        ApiResponse apiResponse = modulService.deleteModul(id);
        return ResponseEntity.status(apiResponse.isSuccess() ?
                HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/{id}/problem")
    public ResponseEntity<Set<Problem>> getModulProblems(@PathVariable Integer id){
        return ResponseEntity.ok(modulService.getModulProblems(id));
    }

    @PostMapping("/{modulId}/problem")
    public ResponseEntity<ApiResponse> addProblemToModul(@PathVariable Integer modulId,
                                                         @RequestBody Integer problemId){
        ApiResponse apiResponse = modulService.addProblemToModul(modulId,problemId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{modulId}/problem/{problemId}")
    public ResponseEntity<ApiResponse> addProblemToModul(@PathVariable Integer modulId,
                                                         @PathVariable Integer problemId, @RequestBody Integer newProblemId){
        ApiResponse apiResponse = modulService.editProblemModul(modulId,problemId,newProblemId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{modulId}/problem/{problemId}")
    public ResponseEntity<ApiResponse> deleteProblemModel(@PathVariable Integer modulId,
                                                         @PathVariable Integer problemId){
        ApiResponse apiResponse = modulService.deleteProblemModul(modulId,problemId);
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
