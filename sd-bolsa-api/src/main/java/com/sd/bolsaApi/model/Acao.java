package com.sd.bolsaApi.model;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

@XmlRootElement
public class Acao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private double preco;
	private double precoDeCompra;
	private String codigoEmpresa;
	UUID idClienteDono;
	private boolean aVenda;
	
	public Acao () {}
	
	public Acao (String codigo, double preco) {
		this.codigo = codigo;
		this.preco = preco;
		this.precoDeCompra = 0;
		this.idClienteDono = null;
		this.aVenda = false;
	}
	
	public Acao (String codigo, double preco, Empresa empresa) {
		this.codigo = codigo;
		this.preco = preco;
		this.precoDeCompra = 0;
		this.codigoEmpresa = empresa.getCodigo();
		this.idClienteDono = null;
		this.aVenda = false;
	}
	
	public Acao (String codigo, double preco, Empresa empresa, UUID idClienteDono) {
		this.codigo = codigo;
		this.preco = preco;
		this.precoDeCompra = 0;
		this.codigoEmpresa = empresa.getCodigo();
		this.idClienteDono = idClienteDono;
		this.aVenda = false;
	}
	
	public Acao(JSONObject json, UUID idClienteDono) {
		this.codigo = json.getString("codigo");
		this.preco = json.getDouble("preco");
		this.precoDeCompra = json.getDouble("precoDeCompra");
		this.codigoEmpresa = json.getString("codigoEmpresa");
		this.idClienteDono = idClienteDono;
		this.aVenda = false;
	}
	
	public Acao(JSONObject json) {}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public double getPreco() {
		return this.preco;
	}
	
	public void setPrecoDeCompra(double precoDeCompra) {
		this.precoDeCompra = precoDeCompra;
	}
	
	public double getPrecoDeCompra() {
		return this.precoDeCompra;
	}

	public UUID getIdClienteDono() {
		return idClienteDono;
	}

	public void setIdClienteDono(UUID idClienteDono) {
		this.idClienteDono = idClienteDono;
	}

	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public boolean isaVenda() {
		return aVenda;
	}

	public void setaVenda(boolean aVenda) {
		this.aVenda = aVenda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idClienteDono == null) ? 0 : idClienteDono.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((codigoEmpresa == null) ? 0 : codigoEmpresa.hashCode());
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
		Acao other = (Acao) obj;
		if (idClienteDono == null) {
			if (other.idClienteDono != null)
				return false;
		} else if (!idClienteDono.equals(other.idClienteDono))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (codigoEmpresa == null) {
			if (other.codigoEmpresa != null)
				return false;
		} else if (!codigoEmpresa.equals(other.codigoEmpresa))
			return false;
		return true;
	}
	
}
