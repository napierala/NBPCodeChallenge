package pl.napierala.nbpcodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.napierala.nbpcodechallenge.builder.UserEntityBuilder;
import pl.napierala.nbpcodechallenge.builder.UserRegisterResponseBuilder;
import pl.napierala.nbpcodechallenge.entity.UserEntity;
import pl.napierala.nbpcodechallenge.exception.UserAlreadyExistsException;
import pl.napierala.nbpcodechallenge.extmodel.UserRegisterRequest;
import pl.napierala.nbpcodechallenge.extmodel.UserRegisterResponse;
import pl.napierala.nbpcodechallenge.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserEntity> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public UserEntity findByUserNameOrThrowException(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public UserRegisterResponse register(UserRegisterRequest request) {

        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity userEntity = UserEntityBuilder.buildRegularUserWith(request.getUserName(), encodedPassword);

        UserEntity saved = userRepository.save(userEntity);

        return UserRegisterResponseBuilder.buildWith(saved);
    }
}