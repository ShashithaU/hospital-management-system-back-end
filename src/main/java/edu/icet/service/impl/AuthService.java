package edu.icet.service.impl;


import edu.icet.config.JwtAuthenticationController;
import edu.icet.dto.ResponseDto;
import edu.icet.dto.UserDto;
import edu.icet.entity.UserEntity;
import edu.icet.repository.AuthRepository;
import edu.icet.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationController authenticationController;

    public UserEntity findValidUserByEmail(String email) {
        return authRepository.findFirstByEmailAndActiveTrueAndVerifiedTrue(email);
    }

    public UserEntity findUserByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    @Transactional(rollbackFor = {Exception.class})
    public ResponseDto signUp(UserDto userDto) throws Exception{
        UserEntity user = findUserByEmail(userDto.getEmail());
        if (user != null) {
            log.info("{}. tried to sign up again",user.getEmail());
            return new ResponseDto("500","That email is taken. Try another one!");
        } else {
            String verifyCode = new Utilities().generateRandomNumber();

            user = new ModelMapper().map(userDto, UserEntity.class);
            user.setActive(true);
            user.setVerified(false);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setVerifyCode(verifyCode);
            user.setCreateDateTime(new Date());
            user = authRepository.saveAndFlush(user);
            return new ResponseDto("success","200",null);
        }
    }

}
