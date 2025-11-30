package br.ifs.edu.cads.api.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import br.ifs.edu.cads.api.hotel.dto.EstadoDTO;
import br.ifs.edu.cads.api.hotel.entity.Estado;
import br.ifs.edu.cads.api.hotel.exception.RegraDeNegocioException;
import br.ifs.edu.cads.api.hotel.repository.EstadoRepository;
import jakarta.transaction.Transactional;

@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository){
        this.estadoRepository = estadoRepository;
    }
    
    private EstadoDTO toDTO(Estado estado){
        
        return new EstadoDTO(
            estado.getIdEstado(),
            estado.getUf()
        );
    }

    public EstadoDTO buscarPorId(Long id){
        Optional<Estado> estado = estadoRepository.findById(id);

        EstadoDTO estadoDTO = null;
        if (estado.get() != null)
            estadoDTO = toDTO(estado.get());

        return estadoDTO;
    }

    public EstadoDTO buscarPorUf(String uf){
        Optional<Estado> estado = estadoRepository.findByUf(uf);

        EstadoDTO estadoDTO = null;
        if (estado.get() != null)
            estadoDTO = toDTO(estado.get());

        return estadoDTO;
    }

    public List<Estado> listarTodos(){
        return estadoRepository.findAll();
    }

    private Estado fromDTO (EstadoDTO estadoDTO){
        Estado estado = new Estado();
        estado.setIdEstado(estadoDTO.id());
        estado.setUf(estadoDTO.uf());

        return estado;
    }

    @Transactional
    public EstadoDTO salvar(EstadoDTO estadoDTO){

        String uf = estadoDTO.uf();
        if (estadoRepository.existsByUf(uf))
            throw new RegraDeNegocioException("Estado já existe: " + uf);

        Estado estado = fromDTO(estadoDTO);
        Estado estadoSalvo = estadoRepository.save(estado);
        return toDTO(estadoSalvo);
    }

    @Transactional
    public Estado atualizar(Long id, Estado estado){
        estado.setIdEstado(id);
        return estadoRepository.save(estado);
    }
    
}
