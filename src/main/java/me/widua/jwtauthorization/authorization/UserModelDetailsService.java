package me.widua.jwtauthorization.authorization;

import me.widua.jwtauthorization.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserModelDetailsService implements UserDetailsService {


    private final UserRepository repository;

    @Autowired
    public UserModelDetailsService ( UserRepository repository ){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       return repository.findById(username)
               .orElseThrow( () -> new UsernameNotFoundException(String.format("Username %s does not exist",username)) ) ;
    }
}
