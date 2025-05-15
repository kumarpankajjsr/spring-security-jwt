package com.example.springsecurity.Spring_Security_JWT.repository;

import com.example.springsecurity.Spring_Security_JWT.entity.UserAuthDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthDetailsRepository extends JpaRepository<UserAuthDetails,Long> {

  public UserAuthDetails findByUsername(String username);
}
