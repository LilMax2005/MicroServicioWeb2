package cl.ducuc.MicroServicioWeb.repository;

import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UserLoginRequest, Long> {
    UserLoginRequest findByEmail(String email);

    UserLoginRequest findByEmailAndContraseña(String email, String contraseña);
}
