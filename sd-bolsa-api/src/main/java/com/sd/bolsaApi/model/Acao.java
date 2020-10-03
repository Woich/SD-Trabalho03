package com.sd.bolsaApi.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Acao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private double preco;
	private double precoDeCompra;
	Empresa empresa;
	ClienteControle clienteDono;
	private boolean aVenda;
	
	public Acao (String codigo, double preco) {
		this.codigo = codigo;
		this.preco = preco;
		this.precoDeCompra = 0;
		this.clienteDono = null;
		aVenda = false;
	}
	
	public Acao (String codigo, double preco, Empresa empresa) {
		this.codigo = codigo;
		this.preco = preco;
		this.precoDeCompra = 0;
		this.empresa = empresa;
		this.clienteDono = null;
		aVenda = false;
	}
	
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

	public ClienteControle getClienteDono() {
		return clienteDono;
	}

	public void setClienteDono(ClienteControle clienteDono) {
		this.clienteDono = clienteDono;
	}

	public Empresa getEmpresa() {
		return empresa;
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
		result = prime * result + ((clienteDono == null) ? 0 : clienteDono.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
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
		if (clienteDono == null) {
			if (other.clienteDono != null)
				return false;
		} else if (!clienteDono.equals(other.clienteDono))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		return true;
	}
	
}
