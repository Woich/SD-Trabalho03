package com.sd.bolsaApi.dto;

import java.util.UUID;

public class NotificacaoDTO {
	
	private String codEmpresa;
	private UUID idCliente;
	private boolean ehEpresa;
	private int tipoNotificacao;
	
	public NotificacaoDTO() {}
	
	public NotificacaoDTO(String codEmpresa, UUID idCliente, int tipoNotificacao) {
		this.codEmpresa = codEmpresa;
		this.idCliente = idCliente;
		this.tipoNotificacao = tipoNotificacao;
		this.ehEpresa = codEmpresa != null;
	}

	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public UUID getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(UUID idCliente) {
		this.idCliente = idCliente;
	}

	public boolean isEhEpresa() {
		return ehEpresa;
	}

	public void setEhEpresa(boolean ehEpresa) {
		this.ehEpresa = ehEpresa;
	}

	public int getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(int tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}
	
	
	
}
