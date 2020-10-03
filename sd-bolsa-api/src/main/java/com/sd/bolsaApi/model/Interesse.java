package com.sd.bolsaApi.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Interesse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ClienteControle cliente;
	private Empresa empresa;
	private double valGanho;
	private double valPerda;
	
	public Interesse(ClienteControle cliente, Empresa empresa) {
		
		this.cliente = cliente;
		this.empresa = empresa;
		
	}
	
	public Interesse(ClienteControle cliente, Empresa empresa, double valGanho, double valPerda) {
		
		this.cliente = cliente;
		this.empresa = empresa;
		
		this.valGanho = valGanho;
		this.valPerda = valPerda;
	}
	
	/*GETS E SETS*/
	public ClienteControle getCliente() {
		return cliente;
	}
	public Empresa getEmpresa() {
		return empresa;
	}

	public double getValGanho() {
		return valGanho;
	}

	public double getValPerda() {
		return valPerda;
	}
	
	
}
