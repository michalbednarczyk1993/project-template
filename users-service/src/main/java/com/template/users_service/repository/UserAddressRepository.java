package com.template.users_service.repository;

import com.template.users_service.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {
}