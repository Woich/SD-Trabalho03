package com.sd.bolsaApi.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmpresaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private int quantidadeTotalAcoes;
	
	private UUID idCliente;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQuantidadeTotalAcoes() {
		return quantidadeTotalAcoes;
	}
	public void setQuantidadeTotalAcoes(int quantidadeTotalAcoes) {
		this.quantidadeTotalAcoes = quantidadeTotalAcoes;
	}
	
	public UUID getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(UUID idCliente) {
		this.idCliente = idCliente;
	}
	
}
