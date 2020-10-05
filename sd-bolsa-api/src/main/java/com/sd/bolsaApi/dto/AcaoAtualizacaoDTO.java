package com.sd.bolsaApi.dto;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

@XmlRootElement
public class AcaoAtualizacaoDTO {
	
	String codEmpresa;
	String codAcao; 
	UUID idNovoDono; 
	double valorCompra;
	boolean aVenda;
	
	
	public AcaoAtualizacaoDTO(String codEmpresa, String codAcao, UUID idNovoDono, double valorCompra, boolean aVenda) {
		
		this.codEmpresa = codEmpresa;
		this.codAcao = codAcao;
		this.idNovoDono = idNovoDono;
		this.valorCompra = valorCompra;
		this.aVenda = aVenda;
		
	}
	
	public AcaoAtualizacaoDTO(JSONObject objetoAcao, UUID idCliente) {
		
		this.codEmpresa = objetoAcao.getString("codigoEmpresa");
		this.codAcao = objetoAcao.getString("codigo");
		this.idNovoDono = idCliente;
		this.valorCompra = objetoAcao.getDouble("precoDeCompra");
		this.aVenda = objetoAcao.getBoolean("aVenda");
		
	}
	
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
	public boolean isaVenda() {
		return aVenda;
	}
	public void setaVenda(boolean aVenda) {
		this.aVenda = aVenda;
	}
	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	
	
}
