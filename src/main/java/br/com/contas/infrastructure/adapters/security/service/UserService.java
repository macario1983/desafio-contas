package br.com.contas.infrastructure.adapters.security.service;

import br.com.contas.infrastructure.adapters.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        this.logger.info("Tentando carregar usuário pelo nome de usuário: {}", username);
        try {
            UserDetails userDetails = this.userRepository.findByUsername(username).orElseThrow(() -> {
                this.logger.error("Usuário não encontrado com o nome de usuário: {}", username);
                return new UsernameNotFoundException("Usuário não encontrado com o nome de usuário: " + username);
            });
            this.logger.info("Usuário carregado com sucesso pelo nome de usuário: {}", username);
            return userDetails;
        } catch (UsernameNotFoundException ex) {
            this.logger.error("Erro ao carregar usuário pelo nome de usuário: {}", username, ex);
            throw ex;
        } catch (Exception ex) {
            this.logger.error("Erro inesperado ao carregar usuário pelo nome de usuário: {}", username, ex);
            throw new RuntimeException("Erro inesperado ocorreu", ex);
        }
    }
}

