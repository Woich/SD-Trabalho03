package com.sd.bolsaApi.dto;

import java.util.ArrayList;
import java.util.List;

import com.sd.bolsaApi.model.Acao;

public class ListaAcoesDTO {
	
	private List<Acao> acoes;
	
	public ListaAcoesDTO() {
		this.acoes = new ArrayList<Acao>();
	}
	
	public ListaAcoesDTO(List<Acao> acoes) {
		this.acoes = acoes;
	}
	
	public void addAcaoLista(Acao acao) {
		this.acoes.add(acao);
	}
	
	public List<Acao> getAcoes() {
		return acoes;
	}
	
}
