package com.student.project.TurismoDolomiti.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.project.TurismoDolomiti.entity.Elemento;

@Repository
public interface ElementoDAO extends JpaRepository<Elemento, Long>{
	@Query("SELECT e FROM Elemento e WHERE TYPE(e) = :nomeClass AND e.nome = :nome")
	<E extends Elemento> E findByNome(@Param("nomeClass") final Class<E> nomeClass, @Param("nome")String nome);
	@Query("SELECT COUNT(e) FROM Elemento e WHERE e.id = :id_el")
	Integer verificaEsistenzaEl(@Param("id_el")Long idEl);
	@Query("SELECT e.nome FROM Elemento e WHERE e.id = :id_el")
	String findNomeEl(@Param("id_el")Long idEl);
}
