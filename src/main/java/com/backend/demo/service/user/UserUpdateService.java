package com.backend.demo.service.user;

import com.backend.demo.model.User;
import com.backend.demo.register.PasswordEncoded;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.util.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserUpdateService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserValidation userValidation;

    public void update(User request) {
       try {
           User user = this.authService.signInWithToken(request.getToken());

           User userUpdated = this.userValidation.update(request, user);

           this.userRepository.save(userUpdated);
       }catch (IllegalStateException e) {
           throw new IllegalStateException(e.getMessage());
       }
    }
}
