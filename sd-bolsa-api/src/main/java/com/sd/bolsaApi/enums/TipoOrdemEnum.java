package com.sd.bolsaApi.enums;

public enum TipoOrdemEnum {
	
	
	VENDA(1, "Venda"),
	COMPRA(2, "Compra");
	
	TipoOrdemEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	private int codigo;
	private String descricao;
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	
}
