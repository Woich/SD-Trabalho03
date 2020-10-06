package com.sd.bolsaApi.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sd.bolsaApi.model.ClienteControle;
import com.sd.bolsaApi.model.Interesse;
import com.sd.bolsaApi.model.Notificacao;

@Path("/cliente")
public class ClienteControleResource {
	
	List<ClienteControle> listaCliente;
	List<Interesse> listaInteresses;
	
	/* ------------------------- LÓGICA (END POINTS) ------------------------- */
	
	@POST
	@Path("/registrar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarCliente() {
		 //Gera novo cliente
		ClienteControle novoCliente = new ClienteControle();
		
		//Tenta adicionar o cliente na lista
		boolean foiSalvo = addCliente(novoCliente);
		
		if(foiSalvo) {
			//Caso consiga retorna OK com novo cliente
			return Response.ok(novoCliente).build();
		}else {
			//Caso não consiga retorna erro
			return Response.serverError().build();	
		}
		
	}
	
	@POST
	@Path("/interesse/registrar")
	public Response cadastrarInteresse() {
		return Response.serverError().build();
	}
	
	@GET
	@Path("/notificacao")
	@Produces(MediaType.APPLICATION_JSON)
	public Response notificar() {
		
		Notificacao not = new Notificacao(1, "PED-1");
		
		return Response.ok(not).build();
	}
	
	/* ------------------------- LÓGICA (INTERNA) ------------------------- */
	
	private synchronized boolean addCliente(ClienteControle novoCliente) {
		
		try {
			
			//Inicializa a lista caso esteja vazia
			if(listaCliente == null || listaCliente.isEmpty()) {
				listaCliente = new ArrayList<ClienteControle>();
			}
			
			//Adiciona novo cliente
			listaCliente.add(novoCliente);
			
			//Retorna que conseguiu salvar
			return true;
		} catch (Exception e) {
			//Caso aconteça um erro retorna que não conseguiu salvar
			return false;
		}
		
	}

}
