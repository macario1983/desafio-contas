package br.com.desafio.payableaccount.domain.service;

import br.com.desafio.payableaccount.api.v1.model.input.UserInput;
import br.com.desafio.payableaccount.core.mapstruct.UserMapper;
import br.com.desafio.payableaccount.domain.exception.UsernameAlreadyExistsException;
import br.com.desafio.payableaccount.domain.model.User;
import br.com.desafio.payableaccount.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService implements UserDetailsService {

    private final UserMapper mapper;

    private final UserRepository repository;

    public UserService(UserMapper mapper, UserRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public void save(UserInput userInput) {

        if (this.repository.existsByUsername(userInput.username())) {
            throw new UsernameAlreadyExistsException();
        }

        User user = this.mapper.toModel(userInput);
        this.repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).build();
    }
}
