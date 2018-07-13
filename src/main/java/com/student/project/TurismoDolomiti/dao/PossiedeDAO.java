package com.student.project.TurismoDolomiti.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.project.TurismoDolomiti.entity.Possiede;
import com.student.project.TurismoDolomiti.entity.Rifugio;
import com.student.project.TurismoDolomiti.entity.Utente;


@Repository
public interface PossiedeDAO extends JpaRepository<Possiede, Long> {
	@Query("SELECT COUNT(p) FROM Possiede p WHERE p.rifugio.id = :id_rifugio AND p.proprietario.id = :id_utente")
	public Integer verificaProprieta(@Param("id_rifugio") Long idRifugio, @Param("id_utente") Long idUtente);
	
	@Query("SELECT p.proprietario.id FROM Possiede p WHERE p.rifugio.id = :id_rifugio")
	public List<Long> gestoriRifugio(@Param("id_rifugio") Long idRifugio);
	
	@Query("SELECT COUNT(p) FROM Possiede p WHERE p.rifugio.id = :id_rifugio")
	public Integer verificaEsistenzaAlmenoUnGestoreRifugio(@Param("id_rifugio")Long idRifugio);
	
	@Query("SELECT COUNT(p) FROM Possiede p WHERE p.proprietario.id = :id_utente")
	public Integer verificaUtenteGestore(@Param("id_utente")Long idUtente);
	
	public void deleteByProprietarioAndRifugio(Utente proprietario, Rifugio rifugio);

}
