package com.mascotas_virtuales.mascotas_virtuales.services;

import com.mascotas_virtuales.mascotas_virtuales.models.Usuario;
import com.mascotas_virtuales.mascotas_virtuales.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioService implements UserDetailsService {


    private final UsuarioRepository usuarioRepository;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registerUser(Usuario newUser) {

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Collections.singletonList("ROLE_USER"));
        return usuarioRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().toArray(new String[0]))
                .build();
    }
}
