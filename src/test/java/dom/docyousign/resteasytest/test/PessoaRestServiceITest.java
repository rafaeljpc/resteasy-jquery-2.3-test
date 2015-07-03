package dom.docyousign.resteasytest.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.ClientErrorInterceptor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


//@RunWith(Arquillian.class)
public class PessoaRestServiceITest {

	private final static String BASE_URL = "http://localhost:8080/resteasytest/rest";
	private final static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	private PessoaRestServiceClient client;
	
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "resteasy-itest.jar")
				.addPackage(PessoaRestServiceITest.class.getPackage());
		return jar;
	}
	
	
	@Before
	public void inicializaClient() {
		ResteasyProviderFactory pf = ResteasyProviderFactory.getInstance();
		pf.addClientErrorInterceptor(new PessoaServiceExceptionInterceptor());
		client = ProxyFactory.create(PessoaRestServiceClient.class, BASE_URL);
		
		client.limpar();
	}

	
	@Test
	public void deveria_cadastrar_uma_nova_pessoa() throws Exception {
		Pessoa p = new Pessoa();
		p.setNome("a");
		p.setEmail("a@aa.com");
		p.setDataNascimento(SDF.parse("11/12/1981"));
		
		client.cadastrar(p);
	}
	
	@Test(expected=PessoaDuplicadaException.class)
	public void nao_deveria_cadastrar_uma_nova_pessoa() throws Exception {
		Pessoa p = new Pessoa();
		p.setNome("a");
		p.setEmail("a@aa.com");
		p.setDataNascimento(SDF.parse("11/12/1981"));
		
		client.cadastrar(p);
		
		client.cadastrar(p);
	}
	
	@Test
	public void deveria_retornar_uma_nova_pessoa() throws Exception {
		Pessoa p = new Pessoa();
		p.setNome("a");
		p.setEmail("a@aa.com");
		p.setDataNascimento(SDF.parse("11/12/1981"));
		
		client.cadastrar(p);
		
		List<Pessoa> lst = client.list();
		
		assertNotEquals(0, lst.size());
	}
	
	@Test
	public void deveria_limpar_uma_nova_pessoa() throws Exception {
		Pessoa p = new Pessoa();
		p.setNome("a");
		p.setEmail("a@aa.com");
		p.setDataNascimento(SDF.parse("11/12/1981"));
		
		client.cadastrar(p);
		
		List<Pessoa> lst = client.list();
		
		assertNotEquals(0, lst.size());
		
		client.limpar();
		
		lst = client.list();
		
		assertEquals(0, lst.size());
	}
	
	@Test
	public void deveria_apagar_uma_nova_pessoa() throws Exception {
		Pessoa p = new Pessoa();
		p.setNome("a");
		p.setEmail("a@aa.com");
		p.setDataNascimento(SDF.parse("11/12/1981"));
		
		client.cadastrar(p);
		
		List<Pessoa> lst = client.list();
		
		assertNotEquals(0, lst.size());
		
		client.apagar(p);
		
		lst = client.list();
		
		assertEquals(0, lst.size());
	}
	
	@Test(expected=PessoaInexistenteException.class)
	public void deveria_nao_apagar_pessoa_inexistente() throws Exception {
		Pessoa p = new Pessoa();
		p.setNome("a");
		p.setEmail("a@aa.com");
		p.setDataNascimento(SDF.parse("11/12/1981"));
		
		client.apagar(p);
	}
	
	
	
	
	
	
	
	
	@Path("/pessoa")
	public interface PessoaRestServiceClient {
		
		@POST
		@Path("/apagar")
		@Consumes(MediaType.APPLICATION_JSON)
		public void apagar(Pessoa pessoa) throws PessoaInexistenteException;
		
		@POST
		@Path("/cadastrar")
		@Consumes(MediaType.APPLICATION_JSON)
		public void cadastrar(Pessoa pessoa) throws PessoaDuplicadaException;
		
		@POST
		@Path("/limpar")
		public void limpar();
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/list")
		public List<Pessoa> list();
	}
	
	public class PessoaServiceExceptionInterceptor implements ClientErrorInterceptor {
		@Override
		public void handle(ClientResponse<?> response) throws RuntimeException {
			String data = response.getEntity(String.class);
				
			if (response.getStatus() == 500) {
			
				if (data.contains("pessoa_ja_existe")) {
					throw new PessoaDuplicadaException("pessoa_ja_existe");
				}
				else if (data.contains("pessoa_inexistente")) {
					throw new PessoaInexistenteException("pessoa_inexistente");
				}
			}
		}
	}
}
