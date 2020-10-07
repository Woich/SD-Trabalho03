package com.sd.bolsaApi.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

import com.sd.bolsaApi.model.Notificacao;

@Path("/notificacao")
public class NotificacaoEventResource {
	
	@Context
	Sse sse;
	private SseBroadcaster sseBroadcaster;
	
	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void enviarNotificacoes(@Context SseEventSink sseEventSink) {
		
		if(sseBroadcaster == null) {
			sseBroadcaster = sse.newBroadcaster();
		}
		
		sseBroadcaster.register(sseEventSink);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response eventNotificar(Notificacao notificacao) {
		sseBroadcaster.broadcast(sse.newEvent(notificacao.toString()));
		
		return Response.ok().build();
	}
}
