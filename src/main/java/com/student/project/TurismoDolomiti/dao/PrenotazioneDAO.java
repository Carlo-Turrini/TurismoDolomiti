package com.student.project.TurismoDolomiti.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.project.TurismoDolomiti.entity.Prenotazione;
import com.student.project.TurismoDolomiti.dto.PrenInfoDTO;
import com.student.project.TurismoDolomiti.dto.PrenotazioneClienteCardDto;
import com.student.project.TurismoDolomiti.dto.PrenotazioneRifugioCardDto;

@Repository
public interface PrenotazioneDAO extends JpaRepository<Prenotazione, Long> {
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.PrenotazioneClienteCardDto(p.id, p.numPersone, p.arrivo, p.partenza, p.rifugio.nome, p.rifugio.iconPath) FROM Prenotazione p WHERE p.cliente.id = :id_cliente")
	public List<PrenotazioneClienteCardDto> findAllPrenotazioniCliente(@Param("id_cliente") Long idCliente);
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.PrenotazioneRifugioCardDto(p.id, p.numPersone, p.arrivo, p.partenza, p.cliente.nome, p.cliente.cognome, p.cliente.profilePhotoPath) FROM Prenotazione p WHERE p.rifugio.id = :id_rifugio")
	public List<PrenotazioneRifugioCardDto> findAllPrenotazioniRifugio(@Param("id_rifugio") Long idRifugio);
	
	@Query("SELECT COUNT(p) FROM Prenotazione p WHERE p.id = :id_pren AND p.cliente.id = :id_cliente")
	public Integer verificaPrenotazioneUtente(@Param("id_pren")Long idPren, @Param("id_cliente")Long idCliente);
	
	@Query("SELECT COUNT(p) FROM Prenotazione p WHERE p.id = :id_pren AND p.rifugio.id = :id_rif") 
	public Integer verificaPenotazioneRifugio(@Param("id_pren")Long idPren, @Param("id_rif")Long idRif);
	
	@Query("SELECT new com.student.project.TurismoDolomiti.dto.PrenInfoDTO(p.rifugio.id, p.rifugio.nome, p.rifugio.email, p.rifugio.tel, p.cliente.id, p.cliente.nome, p.cliente.cognome, p.cliente.email, p.rifugio.iconPath, p.cliente.profilePhotoPath) FROM Prenotazione p WHERE p.id = :id_pren")
	public PrenInfoDTO findPrenInfo(@Param("id_pren")Long idPren);
	


}
