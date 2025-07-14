package com.template.users_service.controller;

import com.template.users_service.dto.UserAddressDto;
import com.template.users_service.service.UserAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-addresses")
public class UserAddressController {
    private final UserAddressService userAddressService;

    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @GetMapping
    public List<UserAddressDto> getAllAddresses() {
        return userAddressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAddressDto> getAddressById(@PathVariable UUID id) {
        return userAddressService.getAddressById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserAddressDto createAddress(@Valid @RequestBody UserAddressDto addressDto) {
        return userAddressService.saveAddress(addressDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAddressDto> updateAddress(@PathVariable UUID id,
            @Valid @RequestBody UserAddressDto addressDto) {
        if (userAddressService.getAddressById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        addressDto.setId(id);
        return ResponseEntity.ok(userAddressService.saveAddress(addressDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        if (userAddressService.getAddressById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userAddressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}