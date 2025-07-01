package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;
import cl.ducuc.MicroServicioWeb.service.UsuarioAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/autenticacion")
public class UsuarioLoginController {
    @Autowired
    private UsuarioAuth usuarioService;

    @PostMapping("/registrar")

    public ResponseEntity<UserLoginRequest> registrar(@RequestBody UserLoginRequest usuario) {
        try {
            UserLoginRequest usuarioRegistrado = usuarioService.registrarUsuario(usuario.getEmail(), usuario.getContraseña(), usuario.getNombre(), usuario.getApellido());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @PostMapping("/login")

    public ResponseEntity<UserLoginRequest> iniciar(@RequestBody UserLoginRequest usuario) {
        try {
            UserLoginRequest usuarioiniciado = usuarioService.iniciar(usuario.getEmail(), usuario.getContraseña());
            if (usuarioiniciado != null) {
                return ResponseEntity.ok(usuarioiniciado);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
