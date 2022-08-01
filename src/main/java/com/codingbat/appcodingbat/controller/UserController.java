package com.codingbat.appcodingbat.controller;

import com.codingbat.appcodingbat.entity.Solution;
import com.codingbat.appcodingbat.entity.User;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.payload.SolutionDto;
import com.codingbat.appcodingbat.payload.UserDto;
import com.codingbat.appcodingbat.service.UserService;
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
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUser(id));
    }


    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    /*
        Update user password
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.editUser(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @GetMapping("/{userId}/solution")
    public ResponseEntity<Set<Solution>> getUserSolutions(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserSolutions(userId));
    }

    @GetMapping("/{userId}/solution/{solutionId}")
    public ResponseEntity<Solution> getUserSolution(@PathVariable Integer userId,
                                                    @PathVariable Integer solutionId) {
        return ResponseEntity.ok(userService.getUserSolution(userId, solutionId));
    }

    @PostMapping("/{userId}/solition")
    public ResponseEntity<ApiResponse> addSolutionToUser(@PathVariable Integer userId,
                                                         @RequestBody SolutionDto solutionDto) {
        ApiResponse apiResponse = userService.addSolutinToUser(userId, solutionDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED :
                HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{userId}/solution/{solutionId}")
    public ResponseEntity<ApiResponse> editUserSolution(@PathVariable Integer userId,
                                                        @PathVariable Integer solutionId, @RequestBody String body) {
        ApiResponse apiResponse = userService.editUserSolution(userId, solutionId,body);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED :
                HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{userId}/solution/{solutionId}")
    public ResponseEntity<ApiResponse> deleteUserSolution(@PathVariable Integer userId, @PathVariable Integer solutionId){
        ApiResponse apiResponse = userService.deleteUserSolution(userId,solutionId);
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
