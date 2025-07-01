package cl.ducuc.MicroServicioWeb.service;


import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;
import cl.ducuc.MicroServicioWeb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioAuth {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserLoginRequest registrarUsuario(String email, String contraseña, String nombre, String apellido) {
        UserLoginRequest usuario = new UserLoginRequest();
        usuario.setEmail(email);
        usuario.setContraseña(passwordEncoder.encode(contraseña));
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        return usuarioRepository.save(usuario);

    }


    public UserLoginRequest iniciar(String email, String password) {
        UserLoginRequest usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && passwordEncoder.matches(password, usuario.getContraseña())) {
            return usuario;
        } else {
            return null;
        }
    }

}
