package com.student.project.TurismoDolomiti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.project.TurismoDolomiti.entity.Piatto;
import com.student.project.TurismoDolomiti.enums.CategoriaMenu;

import java.util.*;

@Repository
public interface PiattoDAO extends JpaRepository<Piatto, Long> {
	@Query("SELECT p FROM Piatto p WHERE p.rifugio.id = :id_rifugio AND p.categoria = :categoria")
	public List<Piatto> findByRifugioIdAndCategoria(@Param("id_rifugio") Long idRifugio,@Param("categoria") CategoriaMenu categoria);
	
	@Query("SELECT COUNT(p) FROM Piatto p WHERE p.rifugio.id = :id_rifugio AND p.nome = :nome_piatto")
	public Integer findPiattoByNomeAndRifugioId(@Param("id_rifugio")Long idRifugio, @Param("nome_piatto")String nomePiatto);
	
	@Query("SELECT p FROM Piatto p WHERE p.rifugio.id = :id_rifugio AND p.nome = :nome_piatto")
	public Piatto	getPiattoByNomeAndRifugioId(@Param("id_rifugio")Long idRifugio, @Param("nome_piatto")String nomePiatto);
}
