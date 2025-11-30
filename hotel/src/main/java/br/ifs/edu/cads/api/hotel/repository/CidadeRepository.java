package br.ifs.edu.cads.api.hotel.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import br.ifs.edu.cads.api.hotel.entity.Cidade;
import br.ifs.edu.cads.api.hotel.entity.Estado;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNome(String nome);

    boolean existsByNome(String nome);

    List<Cidade> findAllbyNome(String nome);

    List<Cidade> findAllbyEstado(Estado estado);
}
