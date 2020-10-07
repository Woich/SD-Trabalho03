package com.sd.bolsaApi.model;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import com.sd.bolsaApi.dto.InteresseDTO;

@XmlRootElement
public class Interesse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private UUID idCliente;
	private String codigoEmpresa;
	private double valGanho;
	private double valPerda;
	
	public Interesse() {}
	
	public Interesse(ClienteControle cliente, String codigoEmpresa) {
		
		this.idCliente = cliente.getID();
		this.codigoEmpresa = codigoEmpresa;
		
	}
	
	public Interesse(ClienteControle cliente, String codigoEmpresa, double valGanho, double valPerda) {
		
		this.idCliente = cliente.getID();
		this.codigoEmpresa = codigoEmpresa;
		
		this.valGanho = valGanho;
		this.valPerda = valPerda;
	}
	
	public Interesse(InteresseDTO dto) {
		this.idCliente = dto.getIdCliente();
		this.codigoEmpresa = dto.getCodEmpresa();
		
		this.valGanho = dto.getValGanho();
		this.valPerda = dto.getValPerda();
	}
	
	/*GETS E SETS*/
	public UUID getIdCliente() {
		return idCliente;
	}

	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public double getValGanho() {
		return valGanho;
	}

	public double getValPerda() {
		return valPerda;
	}
	
	
}
