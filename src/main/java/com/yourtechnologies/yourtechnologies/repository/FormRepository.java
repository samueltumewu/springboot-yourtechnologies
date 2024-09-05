package com.yourtechnologies.yourtechnologies.repository;

import com.yourtechnologies.yourtechnologies.entity.Form;
import com.yourtechnologies.yourtechnologies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    Boolean existsBySlug(String slug);
    List<Form> findByCreatorId(Long userId);
}
