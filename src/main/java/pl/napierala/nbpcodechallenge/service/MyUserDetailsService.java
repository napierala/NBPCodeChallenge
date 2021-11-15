package pl.napierala.nbpcodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.napierala.nbpcodechallenge.entity.UserEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<UserEntity> user = userService.findByUserName(userName);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("");
        }

        return buildFrom(user.get());
    }

    private UserDetails buildFrom(UserEntity entity) {
        return new User(
                entity.getUserName(),
                entity.getPassword(),
                buildGrantedAuthoritiesFrom(entity.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> buildGrantedAuthoritiesFrom(Set<String> roles) {
        if (roles == null) {
            return null;
        }

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}