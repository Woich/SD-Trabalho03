package com.sd.bolsaApi.model;

import java.util.UUID;

import org.json.JSONObject;

import com.sd.bolsaApi.enums.TipoNotificacao;

public class Notificacao {
	
	private int tipoNotificao;
	private String message;
	private UUID idCliente;
	
	public Notificacao() {	}
	
	public Notificacao(int tipoNotificao, String codigoEmpresa, UUID idCliente) {
		this.idCliente = idCliente;
		this.tipoNotificao = tipoNotificao;
		this.message = "["+codigoEmpresa+"]: "+ TipoNotificacao.findDescricaoByCodigo(tipoNotificao);
	}
	
	public Notificacao(int tipoNotificao, UUID idCliente) {
		this.idCliente = idCliente;
		this.tipoNotificao = tipoNotificao;
		this.message = "["+idCliente.toString()+"]: "+ TipoNotificacao.findDescricaoByCodigo(tipoNotificao);
	}

	public String getMessage() {
		return message;
	}
	
	public int getTipoNotificao() {
		return tipoNotificao;
	}
	
	public UUID getIdCliente() {
		return idCliente;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTipoNotificao(int tipoNotificao) {
		this.tipoNotificao = tipoNotificao;
	}

	public void setIdCliente(UUID idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public String toString() {
		
		JSONObject object = new JSONObject(this);
		
		return object.toString();
	}
}
