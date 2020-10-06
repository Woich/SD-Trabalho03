package com.sd.bolsaApi.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Interesse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ClienteControle cliente;
	private String codigoEmpresa;
	private double valGanho;
	private double valPerda;
	
	public Interesse(ClienteControle cliente, String codigoEmpresa) {
		
		this.cliente = cliente;
		this.codigoEmpresa = codigoEmpresa;
		
	}
	
	public Interesse(ClienteControle cliente, String codigoEmpresa, double valGanho, double valPerda) {
		
		this.cliente = cliente;
		this.codigoEmpresa = codigoEmpresa;
		
		this.valGanho = valGanho;
		this.valPerda = valPerda;
	}
	
	/*GETS E SETS*/
	public ClienteControle getCliente() {
		return cliente;
	}
	
	

	public double getValGanho() {
		return valGanho;
	}

	public double getValPerda() {
		return valPerda;
	}
	
	
}
