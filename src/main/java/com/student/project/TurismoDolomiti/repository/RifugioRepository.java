package com.student.project.TurismoDolomiti.repository;

import com.student.project.TurismoDolomiti.dto.RifugioCartinaEscursioneCardDto;
import com.student.project.TurismoDolomiti.dto.RifugioNomeIdDTO;
import com.student.project.TurismoDolomiti.dto.RifugioCardDto;
import com.student.project.TurismoDolomiti.entity.Rifugio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface RifugioRepository extends JpaRepository<Rifugio, Long>, JpaSpecificationExecutor<Rifugio>{
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.RifugioCardDto(r.id, r.nome, r.massiccioMontuoso, r.altitudine, r.dataApertura, r.dataChiusura, r.iconPath) FROM Rifugio r JOIN Possiede p ON r.id = p.rifugio.id WHERE p.proprietario.id = :id_utente")
	List<RifugioCardDto> elencoRifugiPosseduti(@Param("id_utente") Long idUtente);
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.RifugioCartinaEscursioneCardDto(r.id, r.nome, r.latitude, r.longitude, r.iconPath) FROM Rifugio r JOIN Elemento e ON r.id = e.id JOIN PassaPer pp ON r.id = pp.rifugio.id WHERE pp.escursione.id = :id_escursione")
	List<RifugioCartinaEscursioneCardDto> findRifugiEscursione(@Param("id_escursione") Long idEscursione);
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.nome = :nome_rifugio")
	Integer verificaEsistenzaRifugioByNome(@Param("nome_rifugio")String nomeRifugio);
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.tel = :tel_rifugio")
	Integer verificaEsistenzaRifugioByTel(@Param("tel_rifugio")Integer telRifugio);
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.email = :email_rifugio")
	Integer verificaEsistenzaRifugioByEmail(@Param("email_rifugio")String emailRifugio);
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.id = :id_rifugio")
	Integer verificaEsistenzaRifugio(@Param("id_rifugio")Long idRif);
	@Query("SELECT r.nome FROM Rifugio r WHERE r.id = :id_rifugio")
	String findNomeRifugio(@Param("id_rifugio")Long idRif);
	Rifugio findByEmail(String email);
	Rifugio findByTel(Integer tel);
	//List<RifugioCardDto> findAllDtoedBy();
	Rifugio findByNome(String nome);
	@Query("SELECT r.nome FROM Rifugio r")
	List<String> findRifugiNames();
	@Query("SELECT COUNT(r) FROM Rifugio r WHERE r.id = :id_rifugio AND r.dataApertura <= :check_in AND r.dataChiusura >= :check_out")
	Integer verificaRifugioApertoInPeriodo(@Param("id_rifugio")Long idRifugio, @Param("check_in")Date checkIn, @Param("check_out")Date checkOut);
	@Query("SELECT r.iconPath FROM Rifugio r WHERE r.id = :id_rif")
	String findRifugioIconPath(@Param("id_rif")Long idRif);
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.RifugioNomeIdDTO(r.id, r.nome) FROM Rifugio r")
	List<RifugioNomeIdDTO> findRifugioNomeAndId();
	
	
}
