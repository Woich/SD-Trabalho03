package com.sd.bolsaApi.model;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import com.sd.bolsaApi.dto.ClienteDTO;

/*
 * 
 * Autores:
 * Lucas Shoiti (lucastakahashi@alunos.utfpr.edu.br)
 * Pedro Henrique Woiciechovski (minewoichbr@gmail.com)
 * 
 * */
@XmlRootElement
public class ClienteControle implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String uri = "http://localhost:<port>/api/mensagem";
	
	public ClienteControle() {
		this.id = UUID.randomUUID();
	}
	
	public ClienteControle(ClienteDTO dto) {
		this.id = UUID.randomUUID();
		this.uri = uri.replace("<port>", dto.getPort());
	}
	
	public UUID getID() {
		return this.id;
	}
	
	public String getUri() {
		return uri;
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
		ClienteControle other = (ClienteControle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
