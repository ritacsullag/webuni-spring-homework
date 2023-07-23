package com.csullagrita.school.service;

import com.csullagrita.school.model.LoginUser;
import com.csullagrita.school.model.Student;
import com.csullagrita.school.repository.UserRepository;
import com.csullagrita.school.security.LoginUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacebookLoginService {

    private static final String GRAPH_API_BASE_URL = "https://graph.facebook.com/v13.0";

    private final UserRepository userRepository;

    //Google-os osztallyal vagy massal is ujrahasznalhato az osztaly

    @Getter
    @Setter
    public static class FacebookData{
        private String email;
        private long id;
    }

    @Transactional
    public UserDetails getUserDetailsForToken(String facebookToken) {
        FacebookData facebookData = getEmailOfFacebookUser(facebookToken);
        LoginUser loginUser = findOrCreateUser(facebookData);
        return LoginUserDetailsService.createFacebookUserDetails(loginUser);

    }

    private FacebookData getEmailOfFacebookUser(String facebookToken) {
        //RestApi hivas a fb fele
        return WebClient
                .create(GRAPH_API_BASE_URL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/me")
                        .queryParam("fields", "email,name")
                        .build()
                )
                .headers(headers -> headers.setBearerAuth(facebookToken))
                .retrieve()
                .bodyToMono(FacebookData.class)
                .block();
    }


    private LoginUser findOrCreateUser(FacebookData facebookData) {
        String facebookId = String.valueOf(facebookData.getId());
        Optional<LoginUser> optionalExistingUser = userRepository.findByFacebookId(facebookId);
        if(optionalExistingUser.isEmpty()) {
            Student newUser = Student.builder()
                    .facebookId(facebookId)
                    .username(facebookData.getEmail())
                    .password("dummy")
                    .build();
            return userRepository.save(newUser);
        } else {
            return optionalExistingUser.get();
        }
    }

}
