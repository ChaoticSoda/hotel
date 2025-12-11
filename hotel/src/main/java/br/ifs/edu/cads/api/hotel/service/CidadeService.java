package br.ifs.edu.cads.api.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import br.ifs.edu.cads.api.hotel.dto.CidadeDTO;
import br.ifs.edu.cads.api.hotel.entity.Cidade;
import br.ifs.edu.cads.api.hotel.entity.Estado;
import br.ifs.edu.cads.api.hotel.exception.RecursoNaoEncontradoException;
import br.ifs.edu.cads.api.hotel.exception.RegraDeNegocioException;
import br.ifs.edu.cads.api.hotel.repository.CidadeRepository;
import br.ifs.edu.cads.api.hotel.repository.EstadoRepository;
import jakarta.transaction.Transactional;

@Service
public class CidadeService {
    
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public CidadeService(CidadeRepository cidadeRepository, EstadoRepository estadoRepository){
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    private CidadeDTO toDTO(Cidade cidade){
        return new CidadeDTO(
            cidade.getId(),
            cidade.getNome(),
            cidade.getEstado().getIdEstado()
        );
    }

    public CidadeDTO buscarPorId(Long id){
        Optional<Cidade> cidade = cidadeRepository.findById(id);

        CidadeDTO cidadeDTO = null;
        if (cidade.get() != null)
            cidadeDTO = toDTO(cidade.get());

        return cidadeDTO;
    }

    public CidadeDTO buscarPorNome(String nome){
        Optional<Cidade> cidade = cidadeRepository.findByNome(nome);
        
        CidadeDTO cidadeDTO = null;
        if (cidade.get() != null)
            cidadeDTO = toDTO(cidade.get());

        return cidadeDTO;
    }

    public List<CidadeDTO> listarTodos() {
        return cidadeRepository.findAll()
        .stream()
        .map(this::toDTO)
        .toList();
    }



    public List<Cidade> listarTodosNome(String nome){
        return cidadeRepository.findAllByNome(nome);
    }

    public List<Cidade> listarTodosEstado(Estado estado){
        return cidadeRepository.findAllByEstado(estado);
    }

    private Cidade fromDTO(CidadeDTO cidadeDTO){
        Cidade cidade = new Cidade();
        cidade.setId(cidadeDTO.id());
        cidade.setNome(cidadeDTO.nome());

        Estado estado = estadoRepository.findById(cidadeDTO.estadoID())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Estado não encontrado com ID: " + cidadeDTO.estadoID()));
        cidade.setEstado(estado);

        return cidade;
    }

    @Transactional
    public CidadeDTO salvar(CidadeDTO cidadeDTO) {
    Estado estado = estadoRepository.findById(cidadeDTO.estadoID())
            .orElseThrow(() -> new RuntimeException("Estado não encontrado com o ID: " + cidadeDTO.estadoID()));

    Cidade cidade = fromDTO(cidadeDTO);
    cidade.setNome(cidadeDTO.nome());
    cidade.setEstado(estado);
    Cidade cidadeSalva = cidadeRepository.save(cidade);

    return toDTO(cidadeSalva);
}

    @Transactional
    public Cidade atualizar(Long id, Cidade cidade){
        Cidade cidadeAtual = cidadeRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cidade não encontrada com ID: " + id));

        cidadeAtual.setNome(cidade.getNome());

        if (!cidadeAtual.getEstado().getIdEstado().equals(cidade.getEstado().getIdEstado())){
            Estado estado = estadoRepository.findById(cidade.getEstado().getIdEstado())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estado não encontrado com ID: " + cidade.getEstado().getIdEstado()));
            cidadeAtual.setEstado(estado);
        }

        return cidadeRepository.save(cidadeAtual);
    }

}
