package com.sd.bolsaApi.model;

import com.sd.bolsaApi.enums.TipoNotificacao;

public class Notificacao {
	
	private int tipoNotificao;
	private String mensagemNotificacao;
	
	public Notificacao() {	}
	
	public Notificacao(int tipoNotificao, String codigoEmpresa) {
		this.tipoNotificao = tipoNotificao;
		this.mensagemNotificacao = "["+codigoEmpresa+"]: "+ TipoNotificacao.findDescricaoByCodigo(tipoNotificao);
	}

	public int getTipoNotificao() {
		return tipoNotificao;
	}

	public String getMensagemNotificacao() {
		return mensagemNotificacao;
	}
}
