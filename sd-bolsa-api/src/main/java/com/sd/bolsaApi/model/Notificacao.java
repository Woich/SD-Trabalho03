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

	public int getTipoNotificao() {
		return tipoNotificao;
	}

	public String getMensagemNotificacao() {
		return message;
	}
	
	public UUID getIdCliente() {
		return idCliente;
	}

	@Override
	public String toString() {
		
		JSONObject object = new JSONObject(this);
		
		return object.toString();
	}
}
