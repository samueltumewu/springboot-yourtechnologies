package com.yourtechnologies.yourtechnologies.repository;

import com.yourtechnologies.yourtechnologies.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    void deleteByIdAndFormId(Long id, Long formId);
    List<Question> findByFormId(Long formId);
    List<Question> findByFormIdAndId(Long formId, Long id);
}
