package com.sd.bolsaApi.enums;

public enum TipoNotificacao {
	
	VENDA_REALIZADA(1, "Venda Realizada"),
	COMPRA_REALIZADA(2, "Compra Realizada"),
	EMPRESA_VALOR_MINIMO(3, "Empresa atingiu valor minimo"),
	EMPRESA_VALOR_MAXIMO(4, "Empresa atingiu valor máximo");
	
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
