package com.sd.bolsaApi.dto;

import java.util.UUID;

public class InteresseRemocaoDTO {
	
	private UUID idCliente;
	private String codEmpresaInteresse;
	
	public InteresseRemocaoDTO() {}
	
	public InteresseRemocaoDTO(UUID idCliente, String codEmpresaInteresse) {
		this.idCliente = idCliente;
		this.codEmpresaInteresse = codEmpresaInteresse;
	}

	public UUID getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(UUID idCliente) {
		this.idCliente = idCliente;
	}

	public String getCodEmpresaInteresse() {
		return codEmpresaInteresse;
	}

	public void setCodEmpresaInteresse(String codEmpresaInteresse) {
		this.codEmpresaInteresse = codEmpresaInteresse;
	}
	
	
	
}
