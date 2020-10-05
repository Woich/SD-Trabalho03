package com.sd.bolsaApi.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("restapi")
public class RestEasyServices extends Application{
	
	private Set<Object> singletons = new HashSet<>();
	
	public RestEasyServices(){
		this.singletons.add(new HelloWorldResource());
		this.singletons.add(new ClienteControleResource());
		this.singletons.add(new EmpresaResource());
		this.singletons.add(new OrdemResource());
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
