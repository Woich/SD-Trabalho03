package com.sd.bolsaApi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sd.bolsaApi.dto.EmpresaDTO;

@XmlRootElement
public class Empresa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String nome;
	private String codigo;
	private int quantidadeTotalAcoes;
	private List<Acao> acoes;
	private double valorEmpresa;
	
	public Empresa() {
		this.id = UUID.randomUUID();
	}
	
	public Empresa(JSONObject json) {
		this.id = UUID.fromString(json.getString("id"));
		this.nome = json.getString("nome");
		this.valorEmpresa = json.getDouble("valorEmpresa");
		
		JSONArray listaAcoes = json.getJSONArray("acoes");
		
		acoes = new ArrayList<Acao>();
		
		for(int i =0; i< listaAcoes.length(); i++) {
			acoes.add(new Acao(listaAcoes.getJSONObject(i)));
		}
	}
	
	public Empresa(String nome, int quantidadeTotalAcoes) {
		this.id = UUID.randomUUID();
		this.nome = nome;
		this.codigo = nome.substring(0, 3).toUpperCase();
		
		this.quantidadeTotalAcoes = quantidadeTotalAcoes;
		
		if(acoes == null) {
			this.acoes = new ArrayList<Acao>();
		}
		
		valorEmpresa = 0;
	}
	
	public Empresa(EmpresaDTO dto, int listSize) {
		this.id = UUID.randomUUID();
		
		this.nome = dto.getNome();
		
		this.codigo = nome.substring(0, 3).toUpperCase();
		this.codigo = this.codigo +"-"+ listSize;
		
		this.quantidadeTotalAcoes = dto.getQuantidadeTotalAcoes();
		
		if(acoes == null) {
			this.acoes = new ArrayList<Acao>();
		}
		
		valorEmpresa = 0;
	}
	
	public void gerarAcoes() {
		for(int i=0; i<quantidadeTotalAcoes; i++) {
			String codigo = this.codigo + "-" + i;
			acoes.add(new Acao(codigo, 0, this));
		}
	}
	
	public void gerarAcoesComCliente(UUID idClienteDono) {
		for(int i=0; i<quantidadeTotalAcoes; i++) {
			String codigo = this.codigo + "-" + i;
			acoes.add(new Acao(codigo, 0, this, idClienteDono));
		}
	}
	
	/*GETS E SETS*/
	
	public UUID getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getQuantidadeTotalAcoes() {
		return quantidadeTotalAcoes;
	}

	public List<Acao> getAcoes() {
		return acoes;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getValorEmpresa() {
		return valorEmpresa;
	}

	public void setValorEmpresa(double valorEmpresa) {
		this.valorEmpresa = valorEmpresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}