package com.secure.assignment1.repo;

import com.secure.assignment1.entity.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRepo extends JpaRepository<Input,Integer> {

}
