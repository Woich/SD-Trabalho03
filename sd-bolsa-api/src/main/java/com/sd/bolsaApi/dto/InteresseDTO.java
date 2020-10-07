package com.sd.bolsaApi.dto;

import java.util.UUID;

import org.json.JSONObject;

public class InteresseDTO {
	
	private UUID idCliente;
	private String codEmpresa;
	private double valGanho;
	private double valPerda;
	
	public InteresseDTO() {}
	
	public InteresseDTO(UUID idCliente, String codEmpresa, double valGanho, double valPerda) {
		this.idCliente = idCliente;
		this.codEmpresa = codEmpresa;
		this.valGanho = valGanho;
		this.valPerda = valPerda;
	}

	public UUID getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(UUID idCliente) {
		this.idCliente = idCliente;
	}

	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public double getValGanho() {
		return valGanho;
	}

	public void setValGanho(double valGanho) {
		this.valGanho = valGanho;
	}

	public double getValPerda() {
		return valPerda;
	}

	public void setValPerda(double valPerda) {
		this.valPerda = valPerda;
	}
	
	@Override
	public String toString() {
		
		JSONObject object = new JSONObject(this);
		
		return object.toString();
	}
	
}
