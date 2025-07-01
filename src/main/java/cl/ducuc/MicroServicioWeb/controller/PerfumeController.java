package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.Perfume;
import cl.ducuc.MicroServicioWeb.service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/perfumes")
public class PerfumeController {

    @Autowired
    private PerfumeService perfumeService;

    @GetMapping
    public ResponseEntity<List<Perfume>> obtenerTodos() {
        try {
            List<Perfume> perfumes = perfumeService.obtenerTodos();
            return ok(perfumes);
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfume> obtenerPorId(@PathVariable Long id) {
        try {
            return perfumeService.obtenerPorId(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> notFound().build());
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @PostMapping
    public ResponseEntity<Perfume> agregarPerfume(@Valid @RequestBody Perfume perfume) {
        try {
            Perfume nuevoPerfume = perfumeService.agregarPerfume(perfume);
            return status(HttpStatus.CREATED).body(nuevoPerfume);
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfume(@PathVariable Long id) {
        try {
            boolean eliminado = perfumeService.eliminarPerfume(id);
            if (eliminado) {
                return noContent().build();
            } else {
                return notFound().build();
            }
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/comprar")
    public ResponseEntity<String> comprarPerfume(@PathVariable Long id, @RequestParam int cantidad) {
        try {
            if (cantidad <= 0) {
                return badRequest().body("La cantidad debe ser mayor a cero.");
            }

            boolean comprado = perfumeService.comprarPerfume(id, cantidad);
            if (comprado) {
                return ok("Compra realizada exitosamente.");
            } else {
                return badRequest().body("No hay suficiente stock o perfume no existente.");
            }
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

