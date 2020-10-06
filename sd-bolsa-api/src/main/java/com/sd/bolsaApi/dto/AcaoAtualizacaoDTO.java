package com.sd.bolsaApi.dto;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

@XmlRootElement
public class AcaoAtualizacaoDTO {
	
	private String codEmpresa;
	private String codAcao; 
	private UUID idNovoDono; 
	private double valorCompra;
	private boolean vendendo;
	private boolean atualizado;
	
	public AcaoAtualizacaoDTO() {}
	
	public AcaoAtualizacaoDTO(String codEmpresa, String codAcao, UUID idNovoDono, double valorCompra, boolean vendendo) {
		
		this.codEmpresa = codEmpresa;
		this.codAcao = codAcao;
		this.idNovoDono = idNovoDono;
		this.valorCompra = valorCompra;
		this.vendendo = vendendo;
		this.atualizado = false;
		
	}
	
	public AcaoAtualizacaoDTO(JSONObject objetoAcao, UUID idCliente) {
		
		this.codEmpresa = objetoAcao.getString("codigoEmpresa");
		this.codAcao = objetoAcao.getString("codigo");
		this.idNovoDono = idCliente;
		this.valorCompra = objetoAcao.getDouble("precoDeCompra");
		this.vendendo = objetoAcao.getBoolean("vendendo");
		this.atualizado = false;
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
	
	public boolean isVendendo() {
		return vendendo;
	}

	public void setVendendo(boolean vendendo) {
		this.vendendo = vendendo;
	}

	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public boolean isAtualizado() {
		return atualizado;
	}

	public void setAtualizado(boolean atualizado) {
		this.atualizado = atualizado;
	}
	
	@Override
	public String toString() {
		
		JSONObject object = new JSONObject(this);
		
		return object.toString();
	}
}
