package com.sd.bolsaApi.dto;

public class ClienteDTO {
	private String port;
	
	public ClienteDTO () {}
	
	public ClienteDTO (String port) {
		this.port = port;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	
}
