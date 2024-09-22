package com.example.task_crud_api.repository;

import com.example.task_crud_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = """
            SELECT u from User u
            LEFT JOIN FETCH u.tasks t
            WHERE u.id = :userId
            AND t.id IN (
                SELECT tr.tid
                FROM (
                    SELECT ts.id as tid, ROW_NUMBER() OVER(ORDER BY ts.createdAt DESC) as ranking
                    FROM Task ts
                    WHERE ts.user.id = :userId
                ) tr
                WHERE tr.ranking
                BETWEEN (:maxCount * (:pageNumber - 1)) + 1
                AND (:maxCount * (:pageNumber - 1)) + :maxCount
            )
       """
    )
    User findUserWithTasksByUserId(
            @Param("userId") int userId,
            @Param("pageNumber") int pageNumber,
            @Param("maxCount") int maxCount
    );

    Optional<User> findByUsername(String username);
}


