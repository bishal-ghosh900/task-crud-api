package com.example.task_crud_api.repository;

import com.example.task_crud_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("FROM Task t WHERE t.id = :taskId AND t.user.id = :userId")
    Optional<Task> findByUserIdAndTaskId(
            @Param("taskId") int taskId,
            @Param("userId") int userId
    );
}
