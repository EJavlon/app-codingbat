package com.codingbat.appcodingbat.controller;

import com.codingbat.appcodingbat.entity.Modul;
import com.codingbat.appcodingbat.entity.ProgrammingLanguage;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.payload.LanguageDto;
import com.codingbat.appcodingbat.service.ProgrammingLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/programming-language")
public class ProgrammingLanguageController {
    @Autowired
    private ProgrammingLanguageService programmingLanguageService;

    @GetMapping
    public ResponseEntity<List<ProgrammingLanguage>> getProgrammingLanguages() {
        return ResponseEntity.ok(programmingLanguageService.getProgrammingLanguages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgrammingLanguage> getProgrammingLanguage(@PathVariable Integer id){
        return ResponseEntity.ok(programmingLanguageService.getProgrammingLanguage(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProgrammingLanguage(@RequestBody LanguageDto languageDto){
        ApiResponse apiResponse = programmingLanguageService.addProgrammingLanguage(languageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editProgrammingLanguage(@PathVariable Integer id, @RequestBody LanguageDto languageDto){
        ApiResponse apiResponse = programmingLanguageService.editProgrammingLanguage(id, languageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProgrammingLanguage(@PathVariable Integer id){
        ApiResponse apiResponse = programmingLanguageService.deleteProgrammingLanguage(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @GetMapping("/{languageId}/modul")
    public ResponseEntity<Set<Modul>> getProgrammingLanguageModuls(@PathVariable Integer languageId) {
        return ResponseEntity.ok(programmingLanguageService.getProgrammingLanguageModuls(languageId));
    }

    @GetMapping("/{languageId}/modul/{modulId}")
    public ResponseEntity<Modul> getProgrammingLanguageModul(@PathVariable Integer languageId,
                                                             @PathVariable Integer modulId) {
        return ResponseEntity.ok(programmingLanguageService.getProgrammingLanguageModul(languageId,modulId));
    }

    @PostMapping("/{languageId}/modul")
    public ResponseEntity<ApiResponse> addModulToProgrammingLanguage(@PathVariable Integer languageId,@RequestBody Integer modulId){
        ApiResponse apiResponse = programmingLanguageService.addModulToLanguage(languageId,modulId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{languageId}/modul/{modulId}")
    public ResponseEntity<ApiResponse> editProgrammingLanguageModul(@PathVariable Integer languageId,
                                                                    @PathVariable Integer modulId, @RequestBody Integer newModulId){
        ApiResponse apiResponse = programmingLanguageService.editLanguageModul(languageId,modulId,newModulId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{languageId}/modul/{modulId}")
    public ResponseEntity<ApiResponse> deleteProgrammingLanguageModul(@PathVariable Integer languageId,
                                                                    @PathVariable Integer modulId){
        ApiResponse apiResponse = programmingLanguageService.deleteLanguageModul(languageId,modulId);
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
