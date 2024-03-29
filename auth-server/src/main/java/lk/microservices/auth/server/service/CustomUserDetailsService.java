package lk.microservices.auth.server.service;

import lk.microservices.auth.server.domain.User;
import lk.microservices.auth.server.model.CustomUser;
import lk.microservices.auth.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(s);
//
//        if(user == null) {
//            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
//        }
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        });
//        UserDetails userDetails = new org.springframework.security.core.userdetails.
//                User(user.getUsername(), user.getPassword(), authorities);
//        return userDetails;
//    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        CustomUser customUser = new CustomUser(user.getUsername(), user.getPassword(), authorities);
        customUser.setContext(user.getContext());
        customUser.setEmail(user.getEmail());
        customUser.setCreateDate(user.getCreateDate());
        customUser.setFirstName(user.getFirstName());
        customUser.setLastName(user.getLastName());
        customUser.setLastUpdate(user.getLastUpdate());
        customUser.setNumberOfLoginAttempts(user.getNumberOfLoginAttempts());
        customUser.setStatus(user.getStatus());
        return customUser;
    }
}
