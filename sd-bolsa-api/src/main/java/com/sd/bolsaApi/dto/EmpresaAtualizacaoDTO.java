package com.sd.bolsaApi.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

@XmlRootElement
public class EmpresaAtualizacaoDTO {
	
	private String codEmpresa;
	private double valorNovo;
	private boolean atualizado;
	
	public EmpresaAtualizacaoDTO() {}
	
	public EmpresaAtualizacaoDTO(String codEmpresa, double valorNovo) {
		
		this.codEmpresa = codEmpresa;
		this.valorNovo = valorNovo;
		
	}
	
	public String getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	
	public double getValorNovo() {
		return valorNovo;
	}
	public void setValorNovo(double valorNovo) {
		this.valorNovo = valorNovo;
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
