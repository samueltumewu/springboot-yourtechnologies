package com.yourtechnologies.yourtechnologies.repository;

import com.yourtechnologies.yourtechnologies.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    void deleteByIdAndByFormId(Long id, Long formId);
}
