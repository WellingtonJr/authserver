package com.wellington.authserver.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Object> createUsuario(@RequestBody UsuarioDto usuarioDto) {
        if (usuarioService.verificaPorEmail(usuarioDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ja existe um usuario com este email.");
        }
        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Object> buscarUm(@PathVariable("usuarioId") UUID usuarioId) {
        var usuario = usuarioService.findOne(usuarioId);
        if (!usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Object> deletarUm(@PathVariable("usuarioId") UUID usuarioId) {
        var usuario = usuarioService.findOne(usuarioId);
        if (!usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado!");
        }
        usuarioService.deleteOne(usuario.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso!");
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Object> updateOne(@PathVariable("usuarioId") UUID usuarioId,
            @RequestBody UsuarioDto usuarioDto) {
        var usuario = usuarioService.findOne(usuarioId);
        if (!usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado!");
        }
        if (usuarioService.existsByEmail(usuarioDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuario com este email!");
        }

        var usuarioModel = usuario.get();

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateUsuario(usuarioModel, usuarioDto));
    }

}
