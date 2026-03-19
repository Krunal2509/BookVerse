package com.service.bookverse.feature.auth.repository;

import com.service.bookverse.feature.auth.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> getUserProfileByUsername(String username);
}
