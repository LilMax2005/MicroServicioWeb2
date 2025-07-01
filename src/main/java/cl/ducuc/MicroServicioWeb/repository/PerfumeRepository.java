package cl.ducuc.MicroServicioWeb.repository;

import cl.ducuc.MicroServicioWeb.model.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Perfume p SET p.stock = p.stock - :cantidad WHERE p.id = :perfumeId AND p.stock >= :cantidad")
    int comprarPerfume(@Param("perfumeId") Long perfumeId, @Param("cantidad") int cantidad);
}
