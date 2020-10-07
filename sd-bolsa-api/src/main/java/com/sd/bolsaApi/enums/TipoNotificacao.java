package com.sd.bolsaApi.enums;

public enum TipoNotificacao {
	
	LOGIN(1, "Login Realizado"),
	VENDA_REALIZADA(2, "Venda Realizada"),
	COMPRA_REALIZADA(3, "Compra Realizada"),
	EMPRESA_VALOR_MINIMO(4, "Empresa atingiu valor minimo"),
	EMPRESA_VALOR_MAXIMO(5, "Empresa atingiu valor máximo");
	
	TipoNotificacao(int codigo, String descricao) {
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
	
	public static String findDescricaoByCodigo(int codigo) {
		
		for(TipoNotificacao tipoNotificacao : values()) {
			if(tipoNotificacao.getCodigo() == codigo) {
				return tipoNotificacao.getDescricao();
			}
		}
		
		return "";
	}
}
