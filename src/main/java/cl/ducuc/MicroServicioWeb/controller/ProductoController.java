package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.Producto;
import cl.ducuc.MicroServicioWeb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductos(@RequestParam String query) {
        List<Producto> productos = productoService.buscarProductos(query);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.agregarProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Producto> actualizarProducto(@RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
