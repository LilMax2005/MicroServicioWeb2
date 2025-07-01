package cl.ducuc.MicroServicioWeb.service;

import cl.ducuc.MicroServicioWeb.model.Perfume;
import cl.ducuc.MicroServicioWeb.repository.PerfumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;


    public List<Perfume> obtenerTodos() {
        return perfumeRepository.findAll();
    }


    public Optional<Perfume> obtenerPorId(Long id) {
        return perfumeRepository.findById(id);
    }



    public Perfume agregarPerfume(Perfume perfume) {
        if (perfume.getNombre() == null || perfume.getPrecio() <= 0 || perfume.getStock() < 0) {
            throw new IllegalArgumentException("Datos del perfume inválidos");
        }
        return perfumeRepository.save(perfume);
    }


    public boolean eliminarPerfume(Long id) {
        if (perfumeRepository.existsById(id)) {
            perfumeRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public boolean comprarPerfume(Long id, int cantidad) {
        Optional<Perfume> opt = perfumeRepository.findById(id);
        if (opt.isPresent()) {
            Perfume p = opt.get();
            if (p.getStock() >= cantidad) {
                p.setStock(p.getStock() - cantidad);
                perfumeRepository.save(p);
                return true;
            } else {
                throw new RuntimeException("Stock insuficiente para realizar la compra.");
            }
        }
        throw new RuntimeException("Perfume no encontrado con id: " + id);
    }
}
