package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author anupb
 *
 */
@Repository
public interface TodoRepository extends JpaRepository<Task, Long>{


}