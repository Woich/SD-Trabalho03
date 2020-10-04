package com.sd.bolsaApi.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmpresaAtualizacaoDTO {
	
	private String codEmpresa;
	private double valorNovo;
	
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
	
	
	
}
