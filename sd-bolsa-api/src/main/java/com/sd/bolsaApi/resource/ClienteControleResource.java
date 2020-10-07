package com.sd.bolsaApi.resource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ea.async.Async;
import com.sd.bolsaApi.dto.ClienteDTO;
import com.sd.bolsaApi.dto.InteresseDTO;
import com.sd.bolsaApi.dto.InteresseRemocaoDTO;
import com.sd.bolsaApi.dto.NotificacaoDTO;
import com.sd.bolsaApi.enums.TipoNotificacao;
import com.sd.bolsaApi.model.ClienteControle;
import com.sd.bolsaApi.model.Interesse;
import com.sd.bolsaApi.model.Notificacao;

@Path("/cliente")
public class ClienteControleResource {
	
	List<ClienteControle> listaCliente;
	List<Interesse> listaInteresses;
	
	String uriEventoNotificacao = "http://localhost:8080/sd-bolsa-api/restapi/notificacao";
	
	private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
	
	/* ------------------------- LÓGICA (END POINTS) ------------------------- */
	
	static { 
	    Async.init(); 
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findClientes() {
		
		//retorna a lista de interesses
		return Response.ok(listaCliente).build();
		
	}
	
	@POST
	@Path("/registrar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarCliente(ClienteDTO dto) {
		 //Gera novo cliente
		ClienteControle novoCliente = new ClienteControle(dto);
		
		//Tenta adicionar o cliente na lista
		boolean foiSalvo = addCliente(novoCliente);
		
		if(foiSalvo) {
			
			notificar(new NotificacaoDTO(null, novoCliente.getID(), TipoNotificacao.LOGIN.getCodigo()));
			
			//Caso consiga retorna OK com novo cliente
			return Response
					.status(Status.CREATED)
					.entity(novoCliente)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}else {
			//Caso não consiga retorna erro
			return Response.serverError().build();	
		}
		
	}
	
	@POST
	@Path("/interesse/registrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarInteresse(InteresseDTO dto) {
		
		if(dto.getIdCliente() == null) {
			//Retorna um bad request caso o não tenha um cliente informado
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new String("Deve ser informado um cliente"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}else if(dto.getCodEmpresa() == null) {
			//Retorna um bad request caso o não tenha uma empresa informada
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new String("Deve ser informado uma empresa"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}
		
		// Cria o interesse e salva ele na lista
		Interesse interesse = new Interesse(dto);
		boolean interesseAdicionado = addInteresse(interesse);
		
		if(interesseAdicionado) {
			//Retorna um Created caso consiga salvar
			return Response
					.status(Status.CREATED)
					.entity(interesse)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		
		//Retorna server error caso não consiga salvar
		return Response.serverError().build();
	}
	
	@GET
	@Path("/interesse/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findInteressesCliente(@PathParam("idCliente") UUID idCliente) {
		
		//Inicializa a lista
		List<Interesse> listaInteressesCliente = new ArrayList<Interesse>();
		
		//Percorre tudo na listas de interesses buscando os que pertecem ao cliente em quesetão
		for(Interesse interesse : listaInteresses) {
			
			if(interesse.getIdCliente().equals(idCliente)) {
				listaInteressesCliente.add(interesse);
			}
			
		}
		
		//retorna a lista de interesses encontrados para o cliente
		return Response.ok(listaInteressesCliente).build();
		
	}
	
	@GET
	@Path("/interesse")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findInteresses() {
		
		//retorna a lista de interesses
		return Response.ok(listaInteresses).build();
		
	}
	
	@PUT
	@Path("/interesse/remover")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Response removerInteresse(InteresseRemocaoDTO dto) {
		
		//Percorre a lista de interesses
		for(Interesse interesse : listaInteresses){
			
			if(interesse.getCodigoEmpresa().equals(dto.getCodEmpresaInteresse()) && interesse.getIdCliente().equals(dto.getIdCliente()) ) {
				//Caso ache, remove da lista e retorna um ok
				listaInteresses.remove(interesse);
				return Response.ok().build();
			}
			
		}
		
		//Caso não ache retorna um NOT FOUND
		return Response.status(Status.NOT_FOUND).build();
		
	}
	
	public void notificar(NotificacaoDTO dto) {
		
		Notificacao notificacao;
		
		if(dto.isEhEpresa()) {
			notificacao = new Notificacao(dto.getTipoNotificacao(), dto.getCodEmpresa(), dto.getIdCliente());
		}else {
			notificacao = new Notificacao(dto.getTipoNotificacao(), dto.getIdCliente());
		}
		
		
		try {
			//Caso encontre um atualização que ainda não foi enviada envia ela para empresa resource via  PUT
			HttpRequest request = HttpRequest.newBuilder().header("Content-Type", "application/json")
						.POST(BodyPublishers.ofString(notificacao.toString()))
						.uri(URI.create(uriEventoNotificacao))
						.build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		}catch (Exception e) {
			System.out.println(e);
		}
		
		
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
	
	private synchronized boolean addInteresse(Interesse interesse) {
		
		try {
			
			//Inicializa a lista caso esteja vazia
			if(listaInteresses == null || listaInteresses.isEmpty()) {
				listaInteresses = new ArrayList<Interesse>();
			}
			
			//Adiciona novo cliente
			listaInteresses.add(interesse);
			
			//Retorna que conseguiu salvar
			return true;
		} catch (Exception e) {
			//Caso aconteça um erro retorna que não conseguiu salvar
			return false;
		}
		
	}

}
