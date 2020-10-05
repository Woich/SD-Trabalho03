package com.sd.bolsaApi.dto;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrdemDTO {
	
	private UUID idCliente;
	private String codigoEmpresa;
	private int tipoOrdem;
	private double valorOrdem;
	private int quantidadeAcoesVendida;
	private int prazoMin;
	
	public UUID getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(UUID idCliente) {
		this.idCliente = idCliente;
	}
	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}
	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}
	public int getTipoOrdem() {
		return tipoOrdem;
	}
	public void setTipoOrdem(int tipoOrdem) {
		this.tipoOrdem = tipoOrdem;
	}
	public double getValorOrdem() {
		return valorOrdem;
	}
	public void setValorOrdem(double valorOrdem) {
		this.valorOrdem = valorOrdem;
	}
	public int getQuantidadeAcoesVendida() {
		return quantidadeAcoesVendida;
	}
	public void setQuantidadeAcoesVendida(int quantidadeAcoesVendida) {
		this.quantidadeAcoesVendida = quantidadeAcoesVendida;
	}
	public int getPrazoMin() {
		return prazoMin;
	}
	public void setPrazoMin(int prazoMin) {
		this.prazoMin = prazoMin;
	}
	
}
