package com.utn.corralon.features.user.service;

import com.utn.corralon.exception.EmailAlreadyExistsException;
import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.user.dto.UserRequestDTO;
import com.utn.corralon.features.user.dto.UserResponseDTO;
import com.utn.corralon.features.user.entity.UserEntity;
import com.utn.corralon.features.user.mapper.UserMapper;
import com.utn.corralon.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está registrado");
        }
        UserEntity entity = userMapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setCreatedAt(LocalDateTime.now());
        UserEntity saved = userRepository.save(entity);
        return userMapper.toResponse(saved);
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream()
                .filter(UserEntity::getActive)
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDTO getByExternalId(UUID externalId) {
        return userRepository.findByExternalId(externalId)
                .filter(UserEntity::getActive)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found ID: " + externalId));
    }

    @Override
    public UserResponseDTO update(UUID externalId, UserRequestDTO dto) {
        UserEntity entity = userRepository.findByExternalId(externalId)
                .filter(UserEntity::getActive)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot be updated. User not found."));

        userMapper.updateEntity(entity, dto);
        if (!entity.getPassword().equals(dto.getPassword())) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        entity.setCreatedAt(entity.getCreatedAt());
        UserEntity updated = userRepository.save(entity);
        return userMapper.toResponse(updated);
    }

    @Override
    public void delete(UUID externalId) {
        UserEntity entity = userRepository.findByExternalId(externalId)
                .filter(UserEntity::getActive)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot be deleted. User not found."));
        entity.setActive(false);
        userRepository.save(entity);
    }
}