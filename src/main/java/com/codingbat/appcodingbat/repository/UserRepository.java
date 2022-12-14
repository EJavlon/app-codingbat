package com.codingbat.appcodingbat.repository;

import com.codingbat.appcodingbat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);

}
