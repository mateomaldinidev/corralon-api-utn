package com.utn.corralon.features.user.service;

import com.utn.corralon.exception.EmailAlreadyExistsException;
import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.notifications.service.IEmailService;
import com.utn.corralon.features.user.dto.UserRequestDTO;
import com.utn.corralon.features.user.dto.UserResponseDTO;
import com.utn.corralon.features.user.entity.UserEntity;
import com.utn.corralon.features.user.mapper.UserMapper;
import com.utn.corralon.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IEmailService emailService;

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está registrado");
        }
        UserEntity entity = userMapper.toEntity(dto);
        entity.setPassword(encodePassword(dto.getPassword()));
        entity.setCreatedAt(LocalDateTime.now());
        UserEntity saved = userRepository.save(entity);

        emailService.sendWelcomeEmail(saved.getEmail(), saved.getName());

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
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: ", externalId));
    }

    @Override
    public UserResponseDTO update(UUID externalId, UserRequestDTO dto) {
        UserEntity entity = userRepository.findByExternalId(externalId)
                .filter(UserEntity::getActive)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot be updated. User not found with ID: ", externalId));

        boolean passwordChanged = !entity.getPassword().equals(encodePassword(dto.getPassword()));

        userMapper.updateEntity(entity, dto);
        if (passwordChanged) {
            entity.setPassword(encodePassword(dto.getPassword()));
        }
        entity.setCreatedAt(entity.getCreatedAt());
        UserEntity updated = userRepository.save(entity);

        if (passwordChanged) {
            emailService.sendPasswordChangedEmail(updated.getEmail(), updated.getName());
        }

        return userMapper.toResponse(updated);
    }

    @Override
    public void delete(UUID externalId) {
        UserEntity entity = userRepository.findByExternalId(externalId)
                .filter(UserEntity::getActive)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot be deleted. User not found wit ID: ", externalId));
        entity.setActive(false);
        userRepository.save(entity);
    }

    private String encodePassword(String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(rawPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }
}