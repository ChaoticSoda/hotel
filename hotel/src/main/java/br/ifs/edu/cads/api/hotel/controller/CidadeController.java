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

import br.ifs.edu.cads.api.hotel.dto.CidadeDTO;
import br.ifs.edu.cads.api.hotel.entity.Cidade;
import br.ifs.edu.cads.api.hotel.service.CidadeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {
    
    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService){
        this.cidadeService = cidadeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTO> buscarPorId(@PathVariable Long id){
        CidadeDTO cidadeDTO = cidadeService.buscarPorId(id);
        return ResponseEntity.ok(cidadeDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<Cidade>> listarTodos(){
        List<Cidade> cidades = cidadeService.listarTodos();
        return ResponseEntity.ok(cidades);
    }

    @PostMapping
    public ResponseEntity<CidadeDTO> criar(@Valid @RequestBody CidadeDTO cidadeDTO){
        CidadeDTO cidadeCriada = cidadeService.salvar(cidadeDTO);
        URI location = URI.create("/api/cidades/" + cidadeCriada.id());
        return ResponseEntity.created(location).body(cidadeCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Long id, @RequestBody Cidade cidade){
        Cidade cidadeAtualizado = cidadeService.atualizar(id, cidade);
        return ResponseEntity.ok(cidadeAtualizado);
    }
        
}
