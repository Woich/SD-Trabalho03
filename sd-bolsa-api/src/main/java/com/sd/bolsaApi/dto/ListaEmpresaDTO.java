package com.sd.bolsaApi.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sd.bolsaApi.model.Empresa;

public class ListaEmpresaDTO {
	List<Empresa> empresas;
	
	public ListaEmpresaDTO() {
		this.empresas = new ArrayList<Empresa>();
	}
	
	public ListaEmpresaDTO(JSONObject json) {
		this.empresas = new ArrayList<Empresa>();
		
		
		JSONArray listaEmpresas = json.getJSONArray("empresas");
		
		for(int i = 0; i< listaEmpresas.length(); i++) {
			empresas.add(new Empresa(listaEmpresas.getJSONObject(i)));
		}
	}

	public void adicionarLista(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	@Override
	public String toString() {
		
		JSONObject object = new JSONObject(this);
		
		return object.toString();
	}
}
