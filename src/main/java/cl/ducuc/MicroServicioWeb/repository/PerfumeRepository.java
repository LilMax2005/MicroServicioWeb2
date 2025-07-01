package cl.ducuc.MicroServicioWeb.repository;

import cl.ducuc.MicroServicioWeb.model.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    List<Perfume> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByNombre(String nombre);
}
