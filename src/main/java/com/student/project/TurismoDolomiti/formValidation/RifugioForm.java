package com.student.project.TurismoDolomiti.formValidation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class RifugioForm {
	@NotNull
	@Size(min = 2, max = 128)
	private String nome;
	@Size(min = 0, max = 2048)
	private String descrizione;
	@NotNull
	@Digits(integer = 2, fraction = 15)
	private Double latitude;
	@NotNull
	@Digits(integer = 3, fraction = 15)
	private Double longitude;
	@NotNull
	@Digits(integer = 3, fraction = 0)
	private Integer prezzoPostoLetto;
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^[0-9]{10}$")	
	private String tel;
	@NotNull
	@Email
	@Size(min = 7, max = 128)
	private String email;
	@NotNull
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String dataApertura;
	@NotNull
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String dataChiusura;
	@NotNull
	@Digits(integer = 4, fraction = 0)
	private Integer altitudine;
	@NotNull
	@Size(min = 2, max = 64)
	private String massiccioMontuoso;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getPrezzoPostoLetto() {
		return prezzoPostoLetto;
	}
	public void setPrezzoPostoLetto(Integer prezzoPostoLetto) {
		this.prezzoPostoLetto = prezzoPostoLetto;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDataApertura() {
		return dataApertura;
	}
	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}
	public String getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
	}
	public Integer getAltitudine() {
		return altitudine;
	}
	public void setAltitudine(Integer altitudine) {
		this.altitudine = altitudine;
	}
	public String getMassiccioMontuoso() {
		return massiccioMontuoso;
	}
	public void setMassiccioMontuoso(String massiccioMontuoso) {
		this.massiccioMontuoso = massiccioMontuoso;
	}
	
	
}
