package com.sd.bolsaApi.resource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sd.bolsaApi.dto.AcaoAtualizacaoDTO;
import com.sd.bolsaApi.dto.EmpresaAtualizacaoDTO;
import com.sd.bolsaApi.dto.OrdemDTO;
import com.sd.bolsaApi.enums.TipoOrdemEnum;
import com.sd.bolsaApi.model.Acao;
import com.sd.bolsaApi.model.Ordem;

@Path("/ordens")
public class OrdemResource {
	
	List<Ordem> listaOrdensCompra;
	List<Ordem> listaOrdensVenda;
	
	List<AcaoAtualizacaoDTO> listaAcoesAtualizacao;
	List<EmpresaAtualizacaoDTO> listaEmpresaAtualizacao;
	
	final String uriAcoes = "http://localhost:8080/sd-bolsa-api/restapi/empresa/acoes";
	
	final String uriAtualizaAcoes = "http://localhost:8080/sd-bolsa-api/restapi/empresa/atualizaacao";
	final String uriAtualizaEmpresa = "http://localhost:8080/sd-bolsa-api/restapi/empresa/atualizaempresa";
	
	private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
	
	/* ------------------------- LÓGICA (END POINTS) ------------------------- */
	
	@POST
	@Path("/registrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarOrdem(OrdemDTO dto) {
		
		//Retorna um bad request em caso de não ter uma empresa informada
		if(dto.getCodigoEmpresa() == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new String("Deve ser informada empresa"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}else if(dto.getQuantidadeAcoesVendida() <= 0) {
			//Retorna um bad request em caso de não ter uma quntidade informada acima de zero
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new String("Deve ser informada uma quantidade maior que zero"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}else if(dto.getIdCliente() == null) {
			//Retorna um bad request em caso de não ter um cliente informado
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new String("Deve ser informada o dono da ordem"))
					.type(MediaType.TEXT_PLAIN)
					.build();
		}
		
		URI uriAcoesCliente;
		
		try {
			//Constroi a uri com o id do cliente
			if(dto.getTipoOrdem() == TipoOrdemEnum.VENDA.getCodigo()) {
				uriAcoesCliente = new URI(uriAcoes +"/"+ dto.getIdCliente().toString());
				
				//Constroi o request
				HttpRequest request = HttpRequest.newBuilder().GET().uri(uriAcoesCliente).build();
				
				//Obtem o response como um string
				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
				
				//Cria um objeto json para ser trabalhado com a string de reponse
				JSONObject jsonAcoes = new JSONObject(response.body().toString());
				
				//Obtem o array de ações que foi retornado
				JSONArray listaAcoesCliente = jsonAcoes.getJSONArray("acoes");
				
				List<Acao> listaAcoes = new ArrayList<Acao>();
				
				//Gera a lista de ações como a lista de atualizações possíveis a serem enviadas
				for(int j = 0; j< listaAcoesCliente.length(); j++) {
					listaAcoes.add(new Acao(listaAcoesCliente.getJSONObject(j), dto.getIdCliente()));
				}
				
				//Cria uma ordem de compra/venda cada ação com base na quantidade pedida
				for(int i=0; i < dto.getQuantidadeAcoesVendida(); i++) {
					
					boolean isSelecionado = true;
					for(Acao acao : listaAcoes) {
						
						if(dto.getCodigoEmpresa().equals(acao.getCodigoEmpresa()) && isSelecionado) {
							
							
							if(!acao.isaVenda()) {
								//Gera a ordem de compra e salva ela
								Ordem venda = new Ordem(dto.getIdCliente(), acao.getCodigo(), dto.getValorOrdem(), 0, dto.getPrazoMin());
								registrarVendaAcao(venda);
								
								acao.setaVenda(true);
								isSelecionado = false;
								//Gera uma atualização para informar que a ação está a venda
								if(listaAcoesAtualizacao == null || listaAcoesAtualizacao.isEmpty()) {
									listaAcoesAtualizacao = new ArrayList<AcaoAtualizacaoDTO>();
								}
								
								listaAcoesAtualizacao.add(new AcaoAtualizacaoDTO(acao.getCodigoEmpresa(), acao.getCodigo(), acao.getIdClienteDono(), acao.getPrecoDeCompra(), true));
							}
							
							
						}
						
					}
					
				}
			}else {
				for(int i=0; i < dto.getQuantidadeAcoesVendida(); i++) {
					//Gera a ordem de venda e salva ela
					Ordem compra = new Ordem(dto.getIdCliente(), dto.getCodigoEmpresa(), dto.getValorOrdem(), dto.getPrazoMin());
					registrarCompraAcao(compra);
				}
			}
			
			
			checkOrdens();
			
		} catch (URISyntaxException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return Response.status(Status.CREATED).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterOrdensCompra() {
		return Response.ok(listaOrdensCompra).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterOrdensVenda() {
		return Response.ok(listaOrdensVenda).build();
	}
	
	/* ------------------------- LÓGICA (INTERNA) ------------------------- */
	
	private synchronized boolean registrarCompraAcao(Ordem ordemCompra) {
		
		try {
			//Inicializa a lista caso esteja vazia
			if(listaOrdensCompra == null || listaOrdensCompra.isEmpty()) {
				listaOrdensCompra = new ArrayList<Ordem>();
			}
			
			//Adiciona todas as ordens geradas
			listaOrdensCompra.add(ordemCompra);
			
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	
	private synchronized boolean registrarVendaAcao(Ordem ordemVenda) {
		
		try {
			//Inicializa a lista caso esteja vazia
			if(listaOrdensVenda == null || listaOrdensVenda.isEmpty()) {
				listaOrdensVenda = new ArrayList<Ordem>();
			}
			
			//Adiciona todas as ordens geradas
			listaOrdensVenda.add(ordemVenda);
			
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	
	private synchronized void checkOrdens() {
		
		try {
			//Inicializa as listas
			List<Ordem> comprasARemover = new ArrayList<Ordem>(0);
			Ordem vendaARemover = new Ordem();
			
			//Caso exista compras e vendas
			if(listaOrdensCompra != null && listaOrdensCompra.size() > 0 && listaOrdensVenda != null && listaOrdensVenda.size() > 0) {
				for(Ordem compra : listaOrdensCompra) {
					//Percorre a lista de compras
					boolean isSelecionado = true;
					for(Ordem venda : listaOrdensVenda) {
						//Percorre a lista de vendas
						if( isSelecionado && venda.getCodigoAcao().substring(0, 5).equals(compra.getCodigoEmpresa()) && venda.getPrecoMinimoVenda() <= compra.getPrecoMaximoCompra()) {
							//Caso ainda não tenha acontecido o match, e a venda e a compra sejam da mesma empresa
							//com os valor da compra >= ao da venda
							
							realizarVenda(venda, compra); //Realiza a venda
							comprasARemover.add(compra); //Adiciona a compra na lista de remoção
							vendaARemover = venda; //Adiciona a venda para remoção
							isSelecionado = false; //Marca que ocorreu o match
						}
					}
					
					listaOrdensVenda.remove(vendaARemover); //Remove a venda da lista
				}
				
				listaOrdensCompra.removeAll(comprasARemover); //Remove todas as compras que aconteceram
			}
			
			
			realizarAtualizacoes();
			
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	private synchronized void realizarVenda(Ordem venda, Ordem compra) {
		
		if(listaEmpresaAtualizacao == null || listaEmpresaAtualizacao.isEmpty()) {
			listaEmpresaAtualizacao = new ArrayList<EmpresaAtualizacaoDTO>();
		}
		
		//Adiciona uma atualização na lista de atualização de empresas
		listaEmpresaAtualizacao.add(new EmpresaAtualizacaoDTO(compra.getCodigoEmpresa(), compra.getPrecoMaximoCompra()));
		
		if(listaAcoesAtualizacao == null || listaAcoesAtualizacao.isEmpty()) {
			listaAcoesAtualizacao = new ArrayList<AcaoAtualizacaoDTO>();
		}
		
		//Adiciona uma atualização na lista de atualização de ações
		listaAcoesAtualizacao.add(new AcaoAtualizacaoDTO(compra.getCodigoEmpresa(), venda.getCodigoAcao(), compra.getIdCliente(), compra.getPrecoMaximoCompra(), false));
		
	}
	
	private synchronized void realizarAtualizacoes() {
		try {
			
			//Para evitar erro do servidor em caso lista vazia inicializa
			if(listaEmpresaAtualizacao == null || listaEmpresaAtualizacao.isEmpty()) {
				listaEmpresaAtualizacao = new ArrayList<EmpresaAtualizacaoDTO>();
			}
			
			//Percorre a lista
			for(EmpresaAtualizacaoDTO empresaDto : listaEmpresaAtualizacao) {
				
				if(!empresaDto.isAtualizado()) {
					//Caso encontre um atualização que ainda não foi enviada envia ela para empresa resource via  PUT
					HttpRequest request = HttpRequest.newBuilder().header("Content-Type", "application/json").PUT(BodyPublishers.ofString(empresaDto.toString())).uri(URI.create(uriAtualizaEmpresa)).build();
					HttpResponse<?> response;
					
					response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
					
					//Caso consiga realizar a atualização marca que a atualização aconteceu
					if(response.statusCode() == 200) {
						empresaDto.setAtualizado(true);
					}
				}
				
			}
			
			//Para evitar erro do servidor em caso lista vazia inicializa
			if(listaAcoesAtualizacao == null || listaAcoesAtualizacao.isEmpty()) {
				listaAcoesAtualizacao = new ArrayList<AcaoAtualizacaoDTO>();
			}
			
			
			//Percorre a lista
			for(AcaoAtualizacaoDTO acaoDto : listaAcoesAtualizacao) {
				
				if(!acaoDto.isAtualizado()) {
					//Caso encontre um atualização que ainda não foi enviada envia ela para empresa resource via  PUT
					HttpRequest request = HttpRequest.newBuilder().header("Content-Type", "application/json").PUT(BodyPublishers.ofString(acaoDto.toString())).uri(URI.create(uriAtualizaAcoes)).build();
					HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
					
					//Caso consiga realizar a atualização marca que a atualização aconteceu
					if(response.statusCode() == 200) {
						acaoDto.setAtualizado(true);
					}
				}
				
			}
		
		} catch (IOException | InterruptedException e) {
			System.out.println(e);
		}
		
	}
}
