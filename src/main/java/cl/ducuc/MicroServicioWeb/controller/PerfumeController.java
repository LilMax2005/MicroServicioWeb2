package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.Perfume;
import cl.ducuc.MicroServicioWeb.service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/perfumes")
public class PerfumeController {

    @Autowired
    private PerfumeService perfumeService;

    @GetMapping
    public ResponseEntity<List<Perfume>> obtenerTodos() {
        try {
            List<Perfume> perfumes = perfumeService.obtenerTodos();
            return ResponseEntity.ok(perfumes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfume> obtenerPorId(@PathVariable Long id) {
        try {
            return perfumeService.obtenerPorId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Perfume> agregarPerfume(@Valid @RequestBody Perfume perfume) {
        try {
            Perfume nuevoPerfume = perfumeService.agregarPerfume(perfume);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPerfume);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfume(@PathVariable Long id) {
        try {
            boolean eliminado = perfumeService.eliminarPerfume(id);
            if (eliminado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/comprar")
    public ResponseEntity<String> comprarPerfume(@PathVariable Long id, @RequestParam int cantidad) {
        try {
            if (cantidad <= 0) {
                return ResponseEntity.badRequest().body("La cantidad debe ser mayor a cero.");
            }

            boolean comprado = perfumeService.comprarPerfume(id, cantidad);
            if (comprado) {
                return ResponseEntity.ok("Compra realizada exitosamente.");
            } else {
                return ResponseEntity.badRequest().body("No hay suficiente stock o perfume no existente.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

