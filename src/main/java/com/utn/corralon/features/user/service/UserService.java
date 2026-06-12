package com.utn.corralon.features.user.service;

import com.utn.corralon.exception.BusinessRuleException; // Importar BusinessRuleException
import com.utn.corralon.exception.EmailAlreadyExistsException;
import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.user.dto.UserRequestDTO;
import com.utn.corralon.features.user.dto.UserResponseDTO;
import com.utn.corralon.features.user.entity.UserEntity;
import com.utn.corralon.features.user.mapper.UserMapper;
import com.utn.corralon.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional // Añadir Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está registrado");
        }
        UserEntity entity = userMapper.toEntity(dto);
        entity.setPassword(encodePassword(dto.getPassword()));
        entity.setCreatedAt(LocalDateTime.now());
        // Asegurarse de que el usuario se cree activo por defecto si no se especifica
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
        UserEntity saved = userRepository.save(entity);
        return userMapper.toResponse(saved);
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return userRepository.findAllByActiveTrue().stream() // Usar findAllByActiveTrue
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDTO getByExternalId(UUID externalId) {
        return userRepository.findByExternalIdAndActiveTrue(externalId) // Usar findByExternalIdAndActiveTrue
                .map(userMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: ", externalId));
    }

    @Override
    @Transactional // Añadir Transactional
    public UserResponseDTO update(UUID externalId, UserRequestDTO dto) {
        UserEntity entity = userRepository.findByExternalIdAndActiveTrue(externalId) // Usar findByExternalIdAndActiveTrue
                .orElseThrow(() -> new ResourceNotFoundException("Cannot be updated. User not found with ID: ", externalId));

        // Validar si el email ya existe en otro usuario activo
        if (userRepository.existsByEmail(dto.getEmail()) &&
                !entity.getEmail().equalsIgnoreCase(dto.getEmail())) {
            throw new EmailAlreadyExistsException("El email '" + dto.getEmail() + "' ya está registrado por otro usuario.");
        }

        userMapper.updateEntity(entity, dto);
        // Solo codificar la contraseña si ha cambiado
        if (dto.getPassword() != null && !encodePassword(dto.getPassword()).equals(entity.getPassword())) {
            entity.setPassword(encodePassword(dto.getPassword()));
        }
        // entity.setCreatedAt(entity.getCreatedAt()); // No se debe actualizar createdAt en un update
        UserEntity updated = userRepository.save(entity);
        return userMapper.toResponse(updated);
    }

    @Override
    @Transactional // Añadir Transactional
    public void delete(UUID externalId) {
        UserEntity entity = userRepository.findByExternalIdAndActiveTrue(externalId) // Usar findByExternalIdAndActiveTrue
                .orElseThrow(() -> new ResourceNotFoundException("Cannot be deleted. User not found with ID: ", externalId));
        entity.setActive(false);
        userRepository.save(entity);
    }

    @Transactional
    @Override
    public void activate(UUID externalId) {
        UserEntity entity = userRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: ", externalId));

        if (entity.getActive()) {
            throw new BusinessRuleException("User with ID: " + externalId + " is already active.");
        }
        entity.setActive(true);
        userRepository.save(entity);
    }

    @Override
    public List<UserResponseDTO> getInactive() { // NUEVO MeTODO: Obtener usuarios inactivos
        return userRepository.findAllByActiveFalse().stream()
                .map(userMapper::toResponse)
                .toList();
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