package br.ifs.edu.cads.api.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifs.edu.cads.api.hotel.entity.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

    Optional<Estado> findByUf(String Uf);

    boolean existsByUf(String Uf);
    
}
