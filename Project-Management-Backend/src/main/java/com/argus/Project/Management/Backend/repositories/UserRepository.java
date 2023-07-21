package com.argus.Project.Management.Backend.repositories;

import com.argus.Project.Management.Backend.DTO.UserDTO;
import com.argus.Project.Management.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmailId(@Param("email") String email);
    List<UserDTO> getAllUser();
    
    Optional<User> findById(Integer integer);

    List<UserDTO>getByUsername(@Param("username") String currentUser);
}
