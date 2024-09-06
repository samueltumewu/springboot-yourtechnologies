package com.yourtechnologies.yourtechnologies.repository;

import com.yourtechnologies.yourtechnologies.entity.Answer;
import com.yourtechnologies.yourtechnologies.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

}
