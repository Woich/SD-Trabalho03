package com.sd.bolsaApi.model;

import java.util.UUID;

import com.sd.bolsaApi.enums.TipoNotificacao;

public class Notificacao {
	
	private int tipoNotificao;
	private String message;
	
	public Notificacao() {	}
	
	public Notificacao(int tipoNotificao, String codigoEmpresa) {
		this.tipoNotificao = tipoNotificao;
		this.message = "["+codigoEmpresa+"]: "+ TipoNotificacao.findDescricaoByCodigo(tipoNotificao);
	}
	
	public Notificacao(int tipoNotificao, UUID idCliente) {
		this.tipoNotificao = tipoNotificao;
		this.message = "["+idCliente.toString()+"]: "+ TipoNotificacao.findDescricaoByCodigo(tipoNotificao);
	}

	public int getTipoNotificao() {
		return tipoNotificao;
	}

	public String getMensagemNotificacao() {
		return message;
	}
}
