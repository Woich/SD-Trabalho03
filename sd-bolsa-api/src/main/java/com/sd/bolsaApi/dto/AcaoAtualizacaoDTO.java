package com.sd.bolsaApi.dto;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AcaoAtualizacaoDTO {
	
	String codAcao; 
	UUID idNovoDono; 
	double valorCompra;
	
	public String getCodAcao() {
		return codAcao;
	}
	public void setCodAcao(String codAcao) {
		this.codAcao = codAcao;
	}
	
	public UUID getIdNovoDono() {
		return idNovoDono;
	}
	public void setIdNovoDono(UUID idNovoDono) {
		this.idNovoDono = idNovoDono;
	}
	
	public double getValorCompra() {
		return valorCompra;
	}
	public void setValorCompra(double valorCompra) {
		this.valorCompra = valorCompra;
	}
	
	
}
