package com.docyousign.resteasytest;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.Form;

@Path("/pessoa")
public class PessoaRestService {

	private static final Logger log = Logger.getLogger(PessoaRestService.class.getName());
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	private static List<Pessoa> PESSOA_DB = new LinkedList<Pessoa>();
	
	
	@POST
	@Path("/apagar")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void apagar(@Form Pessoa pessoa) throws PessoaInexistenteException {
		log.info("Apagando pessoa=" + pessoa);
		if (!PESSOA_DB.contains(pessoa))
			throw new PessoaInexistenteException("pessoa_inexistente");
		boolean apagou = PESSOA_DB.remove(pessoa);
		if (!apagou)
			throw new PessoaInexistenteException("pessoa_inexistente");
	}
	
	@POST
	@Path("/apagar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void apagarJSON(Pessoa pessoa) throws PessoaInexistenteException {
		apagar(pessoa);
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void cadastrar(@Form Pessoa pessoa) throws PessoaDuplicadaException {
		log.info("Cadastrando pessoa=" + pessoa);
		if (PESSOA_DB.contains(pessoa))
			throw new PessoaDuplicadaException("pessoa_ja_existe");
		PESSOA_DB.add(pessoa);
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void cadastrarJSON(Pessoa pessoa) throws PessoaDuplicadaException {
		cadastrar(pessoa);
	}
	
	
	@POST
	@Path("/limpar")
	public void limpar() {
		log.info("Limpando");
		PESSOA_DB.clear();;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public List<Pessoa> list() {
		return Collections.unmodifiableList(PESSOA_DB);
	}

	
	@Provider
	public static class PessoaDuplicadaExceptionMapper implements ExceptionMapper<PessoaDuplicadaException> {

		@Override
		public Response toResponse(PessoaDuplicadaException exception) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
		}
		
	}
	
	@Provider
	public static class PessoaInexistenteExceptionMapper implements ExceptionMapper<PessoaInexistenteException> {

		@Override
		public Response toResponse(PessoaInexistenteException exception) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
		}
		
	}
}

