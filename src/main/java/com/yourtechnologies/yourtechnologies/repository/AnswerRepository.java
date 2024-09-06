package com.yourtechnologies.yourtechnologies.repository;

import com.yourtechnologies.yourtechnologies.entity.Answer;
import com.yourtechnologies.yourtechnologies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
