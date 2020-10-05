package com.sd.bolsaApi.resource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sd.bolsaApi.dto.AcaoAtualizacaoDTO;
import com.sd.bolsaApi.dto.OrdemDTO;
import com.sd.bolsaApi.enums.TipoOrdemEnum;
import com.sd.bolsaApi.model.Ordem;

@Path("/ordens")
public class OrdemResource {
	
	List<Ordem> listaOrdensCompra;
	List<Ordem> listaOrdensVenda;
	List<AcaoAtualizacaoDTO> listaAtualizacoes;
	
	final String uriAcoes = "http://localhost:8080/sd-bolsa-api/restapi/empresa/acoes";
	private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
	
	/* ------------------------- LÓGICA (END POINTS) ------------------------- */
	
	@POST
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
			if(dto.getTipoOrdem() == TipoOrdemEnum.COMPRA.getCodigo()) {
				uriAcoesCliente = new URI(uriAcoes +"/"+ dto.getIdCliente().toString());
			}else {
				uriAcoesCliente = new URI(uriAcoes);
			}
			
			//Constroi o request
			HttpRequest request = HttpRequest.newBuilder().GET().uri(uriAcoesCliente).build();
			
			//Obtem o response como um string
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			//Cria um objeto json para ser trabalhado com a string de reponse
			JSONObject jsonAcoes = new JSONObject(response.body().toString());
			
			//Obtem o array de ações que foi retornado
			JSONArray listaAcoesCliente = jsonAcoes.getJSONArray("acoes");
			
			listaAtualizacoes = new ArrayList<AcaoAtualizacaoDTO>();
			
			//Gera a lista de ações como a lista de atualizações possíveis a serem enviadas
			for(int j = 0; j< listaAcoesCliente.length(); j++) {
				listaAtualizacoes.add(new AcaoAtualizacaoDTO(listaAcoesCliente.getJSONObject(j), dto.getIdCliente()));
			}
			
			//Cria uma ordem de compra/venda cada ação com base na quantidade pedida
			for(int i=0; i < dto.getQuantidadeAcoesVendida(); i++) {
				
				for(AcaoAtualizacaoDTO acaoDto : listaAtualizacoes) {
					
					if(dto.getCodigoEmpresa().equals(acaoDto.getCodEmpresa()) && !acaoDto.isaVenda()) {
						
						if(dto.getTipoOrdem() == TipoOrdemEnum.COMPRA.getCodigo()) {
							//Gera a ordem de venda e salva ela
							Ordem compra = new Ordem(dto.getIdCliente(), acaoDto.getCodAcao(), 0, dto.getValorOrdem(), dto.getPrazoMin());
							registrarCompraAcao(compra);
						}else {
							//Gera a ordem de compra e salva ela
							Ordem venda = new Ordem(dto.getIdCliente(), acaoDto.getCodAcao(), dto.getValorOrdem(), 0, dto.getPrazoMin());
							registrarVendaAcao(venda);
						}
						
						acaoDto.setaVenda(true);
					}
					
				}
				
			}
			
			checkOrdens();
			
		} catch (URISyntaxException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return Response.ok().build();
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
			
			List<Ordem> comprasARemover = new ArrayList<Ordem>(0);
			Ordem vendaARemover = new Ordem();
			
			if(listaOrdensCompra != null && listaOrdensCompra.size() > 0 && listaOrdensVenda != null && listaOrdensVenda.size() > 0) {
				for(Ordem compra : listaOrdensCompra) {
					boolean isSelecionado = true;
					for(Ordem venda : listaOrdensVenda) {
						if( isSelecionado && venda.getCodigoAcao().substring(0, 4).equals(compra.getCodigoEmpresa()) && venda.getPrecoMinimoVenda() <= compra.getPrecoMaximoCompra()) {
							realizarVenda(venda, compra);
							comprasARemover.add(compra);
							vendaARemover = venda;
							isSelecionado = false;
						}
					}
					
					listaOrdensVenda.remove(vendaARemover);
				}
				
				listaOrdensCompra.removeAll(comprasARemover);
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	private synchronized void realizarVenda(Ordem venda, Ordem compra) {
		
	}
}