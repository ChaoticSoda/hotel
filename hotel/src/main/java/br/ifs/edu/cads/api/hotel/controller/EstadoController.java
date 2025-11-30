package br.ifs.edu.cads.api.hotel.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifs.edu.cads.api.hotel.dto.EstadoDTO;
import br.ifs.edu.cads.api.hotel.entity.Estado;
import br.ifs.edu.cads.api.hotel.service.EstadoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService){
        this.estadoService = estadoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> buscarPorId(@PathVariable Long id){
        EstadoDTO estadoDTO = estadoService.buscarPorId(id);
        return ResponseEntity.ok(estadoDTO);
    }

    @GetMapping("/uf/{uf}")
        public ResponseEntity<EstadoDTO> buscarPorUf(@PathVariable String uf){
            return ResponseEntity.ok(estadoService.buscarPorUf(uf));
        }

    @GetMapping("/")
    public ResponseEntity<List<Estado>> listarTodos(){
        List<Estado> estados = estadoService.listarTodos();
        return ResponseEntity.ok(estados);
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> criar (@Valid @RequestBody EstadoDTO estadoDTO){
        EstadoDTO estadoCriado = estadoService.salvar(estadoDTO);
        URI location = URI.create("/api/estado/" + estadoCriado.id());
        return ResponseEntity.created(location).body(estadoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado){
        Estado estadoAtualizado = estadoService.atualizar(id, estado);
        return ResponseEntity.ok(estadoAtualizado);
    }
    
}