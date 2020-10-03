package com.sd.bolsaApi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ordem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private UUID idCliente;
	private String codigoAcao;
	private String codigoEmpresa;
	private double precoMaximoCompra;
	private double precoMinimoVenda;
	private LocalDateTime dataLimite;
	
	public Ordem() {}
	
	public Ordem(UUID idCliente, String codigoAcao, double precoMaximo , double precoMinimo, int prazo) {
		this.idCliente = idCliente;
		this.codigoAcao = codigoAcao;
		this.precoMaximoCompra = precoMaximo;
		this.precoMinimoVenda = precoMinimo;
		this.dataLimite = LocalDateTime.now().plusMinutes(prazo);
	}
	
	public Ordem(UUID idCliente, String codigoEmpresa, double precoMaximo, int prazo) {
		this.idCliente = idCliente;
		this.codigoEmpresa = codigoEmpresa;
		this.precoMaximoCompra = precoMaximo;
		this.precoMinimoVenda = 0;
		this.dataLimite = LocalDateTime.now().plusMinutes(prazo);
	}
	
	/*GETS E SETS*/
	public void setIdCliente(UUID idCliente) {
		this.idCliente = idCliente;
	}
	
	public void setCodigoAcao(String codigoAcao) {
		this.codigoAcao = codigoAcao;
	}
	
	public UUID getIdCliente() {
		return this.idCliente;
	}
	
	public String getCodigoAcao() {
		return this.codigoAcao;
	}
	
	public double getPrecoMaximoCompra() {
		return this.precoMaximoCompra;
	}
	
	public double getPrecoMinimoVenda() {
		return this.precoMinimoVenda;
	}
	
	public LocalDateTime getDataLimite() {
		return this.dataLimite;
	}

	public void setPrecoMaximoCompra(double precoMaximoCompra) {
		this.precoMaximoCompra = precoMaximoCompra;
	}


	public void setPrecoMinimoVenda(double precoMinimoVenda) {
		this.precoMinimoVenda = precoMinimoVenda;
	}
	
	public boolean isUltrapassado() {
		return this.dataLimite.isBefore(LocalDateTime.now());
	}
	
	public boolean isVenda() {
		return this.getPrecoMinimoVenda() > 0;
	}

	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoAcao == null) ? 0 : codigoAcao.hashCode());
		result = prime * result + ((codigoEmpresa == null) ? 0 : codigoEmpresa.hashCode());
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ordem other = (Ordem) obj;
		if (codigoAcao == null) {
			if (other.codigoAcao != null)
				return false;
		} else if (!codigoAcao.equals(other.codigoAcao))
			return false;
		if (codigoEmpresa == null) {
			if (other.codigoEmpresa != null)
				return false;
		} else if (!codigoEmpresa.equals(other.codigoEmpresa))
			return false;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		return true;
	}
	
}
