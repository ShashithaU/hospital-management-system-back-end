package edu.icet.repository;


import edu.icet.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findFirstByEmailAndActiveTrueAndVerifiedTrue(String email);

    UserEntity findByEmail(String email);

    List<UserEntity> findUserByFirstName(String name);

    Page<UserEntity> findAllByRole(String role, Pageable pageable);

    UserEntity findUserByIdAndActiveTrue(Long id);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM USERS WHERE ROLE IS NULL")
    int findUserCount();

    @Query(nativeQuery = true, value = "SELECT role, COUNT(*) FROM USERS GROUP BY role")
    List<Object[]> countUsersByRole();

}
