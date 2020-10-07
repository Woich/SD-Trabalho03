package com.sd.bolsaApi.resource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import com.sd.bolsaApi.dto.AcaoAtualizacaoDTO;
import com.sd.bolsaApi.dto.EmpresaAtualizacaoDTO;
import com.sd.bolsaApi.dto.EmpresaDTO;
import com.sd.bolsaApi.dto.ListaAcoesDTO;
import com.sd.bolsaApi.dto.ListaEmpresaDTO;
import com.sd.bolsaApi.model.Acao;
import com.sd.bolsaApi.model.Empresa;

@Path("/empresa")
public class EmpresaResource {
	
	List<Empresa> listaEmpresa;
	List<Acao> listaAcoes;
	
	final String uriInteresse = "http://localhost:8080/sd-bolsa-api/restapi/cliente/interesse/notificar";
	
	private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
	
	/* ------------------------- LÓGICA (END POINTS) ------------------------- */
	
	@POST
	@Path("/registrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarEmpresa(EmpresaDTO empresaDTO) {
		
		if(empresaDTO != null) {
			
			if(empresaDTO.getNome() == null) {
				//Retorna um bad request caso o nome não tenha sido informado
				return Response
						.status(Status.BAD_REQUEST)
						.entity(new String("Deve ser informado o nome da empresa"))
						.type(MediaType.TEXT_PLAIN)
						.build();
			} else if(empresaDTO.getQuantidadeTotalAcoes() <= 0) {
				//Retorna um bad request caso a quantidade de ações seja menor ou igual a zero
				return Response
						.status(Status.BAD_REQUEST)
						.entity(new String("Deve ser informada a quantidade de ações da empresa"))
						.type(MediaType.TEXT_PLAIN)
						.build();
			}else if(empresaDTO.getIdCliente() == null) {
				//Retorna um bad request caso não possua um id do cliente que fez a requisição
				return Response
						.status(Status.BAD_REQUEST)
						.entity(new String("Deve ser informado o cliente dono da empresa"))
						.type(MediaType.TEXT_PLAIN)
						.build();
			}
			
			Empresa novaEmpresa;
			
			if(listaEmpresa == null || listaEmpresa.isEmpty()) {
				//Gera nova empresa com codigo final 0
				novaEmpresa = new Empresa(empresaDTO, 0);
			}else {
				//Gera nova empresa com codigo final sendo o tamnho da lista
				novaEmpresa = new Empresa(empresaDTO, listaEmpresa.size());
			}
			
			//Gera as ações com o dono sendo o id do cliente
			novaEmpresa.gerarAcoesComCliente(empresaDTO.getIdCliente());
			
			boolean empresaSalva = addEmpresa(novaEmpresa);
			
			if(empresaSalva) {
				//Tenta add as ações
				boolean acoesSalvas = addListaAcoes(novaEmpresa.getAcoes());
				
				if(acoesSalvas) {
					//Caso consiga criar a emrepsa e as ações retorna um CREATED
					return Response
							.status(Status.CREATED)
							.entity(novaEmpresa)
							.type(MediaType.APPLICATION_JSON)
							.build();
				}else {
					//Caso não consiga criar as ações retorna um INTERNAL SERVER ERRO
					return Response
							.status(Status.INTERNAL_SERVER_ERROR)
							.entity(new String("Erro ao registrar as ações!"))
							.type(MediaType.TEXT_PLAIN)
							.build();
				}
				
			}else {
				//Caso não consiga criar a emrepsa retorna um INTERNAL SERVER ERRO
				return Response
						.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new String("Erro ao registrar a empresa!"))
						.type(MediaType.TEXT_PLAIN)
						.build();
			}
		}
		//Retorna um bad request caso o DTO esteja vazio
		return Response
				.status(Status.BAD_REQUEST)
				.entity(new String("Empresa informa inválida"))
				.type(MediaType.TEXT_PLAIN)
				.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListaEmpresa() {
		
		if(listaEmpresa == null) {
			listaEmpresa = new ArrayList<Empresa>(0);
		}
		//Retorna a lista de empresas
		return Response.ok(listaEmpresa).build();
		
	}
	
	@GET
	@Path("/acoes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListaAcoes() {
		
		if(listaAcoes == null) {
			listaAcoes = new ArrayList<Acao>(0);
		}
		
		//retorna a lista de ações
		return Response.ok(listaAcoes).build();
		
	}
	
	@GET
	@Path("/acoes/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAcoesCliente(@PathParam("idCliente") UUID idCliente) {
		
		//Inicializa a lista
		ListaAcoesDTO listaAcoesCliente = new ListaAcoesDTO();
		
		//Percorre todas as listas de ações buscando as que pertecem ao cliente en quesetão
		for(Acao acao : listaAcoes) {
			
			if(acao.getIdClienteDono().equals(idCliente)) {
				listaAcoesCliente.addAcaoLista(acao);
			}
			
		}
		
		//retorna a lista de ações criada
		return Response.ok(listaAcoesCliente).build();
		
	}
	
	@POST
	@Path("/valorizacao/{valor}")
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Response setValorizacaoEmpresas(@PathParam("valor") double valor) {
		
		//Percorre a lista atualizando os valores
		for(Empresa empresa : listaEmpresa) {
			
			empresa.setValorEmpresa(empresa.getValorEmpresa() + valor);
			
		}
		
		notificarInteresse();
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/desvalorizacao/{valor}")
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Response setDesvalorizacaoEmpresas(@PathParam("valor") double valor) {
		
		//Percorre a lista atualizando os valores
		for(Empresa empresa : listaEmpresa) {
			
			empresa.setValorEmpresa(empresa.getValorEmpresa() - valor);
			
		}
		
		notificarInteresse();
		
		return Response.ok().build();
	}
	
	@PUT
	@Path("/atualizaempresa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarEmpresa(EmpresaAtualizacaoDTO dto) {
		
		//Retorna um bad request em caso de não ter o código da empresa
		if(dto.getCodEmpresa() == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new String("Deve ser informado o código da empresa"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}
		
		//Tenta atualizar a empresa
		boolean atualizouEmpresa = atualizarEmpresa(dto.getCodEmpresa(), dto.getValorNovo());
		
		if(atualizouEmpresa) {
			//Caso consida retorna um ok
			
			notificarInteresse();
			
			return Response.ok().build();
		}else {
			//Caso não, retorna um Internal Server Erro
			return Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String("Erro ao atualizar empresa"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}
	}
	
	@PUT
	@Path("/atualizaacao")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarAcao(AcaoAtualizacaoDTO dto) {
		
		if(dto.getCodAcao() == null) {
			//Retorna um bad request caso não tenha uma ação informada
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new String("Deve ser informado o código da ação"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}else if(dto.getIdNovoDono() == null) {
			//Retorna um bad request caso não tenha um dono informado
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new String("Deve ser informado o id do dono da acao"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}
		
		//Tenta atualiza a ação
		boolean atualizouAcao = atualizarAcao(dto.getCodAcao(), dto.getIdNovoDono(), dto.getValorCompra(), dto.isVendendo());
		
		if(atualizouAcao) {
			//Caso consiga retorna OK
			return Response.ok().build();
		}else {
			//Caso não consiga retorna INTERNAL SERVER ERROR
			return Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String("Erro ao atualizar empresa"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}
	}
	
	
	/* ------------------------- LÓGICA (INTERNA) ------------------------- */
	
	private synchronized boolean addEmpresa(Empresa empresa) {
		
		try {
			
			//Inicializa a lista caso esteja vazia
			if(listaEmpresa == null || listaEmpresa.isEmpty()) {
				listaEmpresa = new ArrayList<Empresa>();
			}
			
			//Adiciona na lista a empresa passada
			listaEmpresa.add(empresa);
			
			return true;
		} catch (Exception e) {
			//Caso aconteça um erro retorna que não conseguiu salvar
			return false;
		}
		
	}
	
	private synchronized boolean addListaAcoes(List<Acao> acoes) {
		
		try {
			
			//Inicializa a lista caso esteja vazia
			if(listaAcoes == null || listaAcoes.isEmpty()) {
				listaAcoes = new ArrayList<Acao>();
			}
			
			//Adiciona na lista a lista de ações informadas
			listaAcoes.addAll(acoes);
			
			return true;
		} catch (Exception e) {
			//Caso aconteça um erro retorna que não conseguiu salvar
			return false;
		}
		
	}
	
	private synchronized boolean atualizarEmpresa(String codEmpresa, double valorNovo) {
		try {
			
			Empresa empr = this.listaEmpresa.stream().filter(e -> e.getCodigo().equals(codEmpresa)).findAny().orElse(null); //busca a empresa informada
			
			if(empr != null) {
				//Caso exista a empresa, remove ela, atualiza os valores e add ela novamente
				listaEmpresa.remove(empr);
				
				empr.setValorEmpresa(valorNovo);
				
				listaEmpresa.add(empr);
				
			}else {
				return false;
			}
			
			return true;
		} catch (Exception e) {
			//Caso aconteça um erro retorna que não conseguiu salvar
			return false;
		}
	}
	
	private synchronized boolean atualizarAcao(String codAcao, UUID idNovoDono,double valorCompra, boolean aVenda) {
		try {
			
			Acao acao = this.listaAcoes.stream().filter(e -> e.getCodigo().equals(codAcao)).findAny().orElse(null); //busca a ação informada
			
			if(acao != null) {
				//Caso exista a ação, remove ela, atualiza os valores e add ela novamente
				listaAcoes.remove(acao);
				
				acao.setIdClienteDono(idNovoDono);
				
				acao.setPrecoDeCompra(valorCompra);
				
				acao.setaVenda(aVenda);
				
				listaAcoes.add(acao);
				
			}else {
				return false;
			}
			
			return true;
		} catch (Exception e) {
			//Caso aconteça um erro retorna que não conseguiu salvar
			return false;
		}
	}
	
	private void notificarInteresse() {
		
		try {
			
			ListaEmpresaDTO listaAtualizacao = new ListaEmpresaDTO();
			
			listaAtualizacao.adicionarLista(listaEmpresa);
			
			//Envia a notificação de atualização
			HttpRequest request = HttpRequest.newBuilder().header("Content-Type", "application/json")
						.POST(BodyPublishers.ofString(listaAtualizacao.toString()))
						.uri(URI.create(uriInteresse))
						.build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			System.out.println(response.statusCode());
			System.out.println(response.body());
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
