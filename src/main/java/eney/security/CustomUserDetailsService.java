package eney.security;

import eney.domain.UserPrincipal;
import eney.domain.UserVO;
import eney.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource(name="userService")
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        UserVO user = userService.loadUserByUsername(userId);
        if (user == null) {
            new UsernameNotFoundException("User not found with username or email : " + userId);
        }


        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(String userId) {
        UserVO user = userService.loadUserByUsername(userId);
        if (user == null) {
            new UsernameNotFoundException("User not found with username or email : " + userId);
        }

        return UserPrincipal.create(user);
    }
}