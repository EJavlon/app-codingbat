package com.codingbat.appcodingbat.repository;

import com.codingbat.appcodingbat.entity.Modul;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModulRepository extends JpaRepository<Modul,Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
