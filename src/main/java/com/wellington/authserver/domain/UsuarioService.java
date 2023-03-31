package com.wellington.authserver.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean verificaPorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario save(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findOne(UUID usuarioId) {
        return usuarioRepository.findById(usuarioId);
    }

    public void deleteOne(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario updateUsuario(Usuario usuario, UsuarioDto usuarioDto) {
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNome(usuarioDto.getNome());
        usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        return usuarioRepository.save(usuario);
    }

}
