package com.docyousign.resteasytest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;

public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = -8395019407178420111L;
	
	private String nome;
	private String email;
	private Date dataNascimento;
	
	private List<Telefone> telefones = new ArrayList<Telefone>();
	
	
	public String getNome() {
		return nome;
	}
	
	@FormParam("nome")
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	@FormParam("email")
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	@FormParam("dataNascimento") @DateFormat("dd/MM/yyyy")
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@FormParam("telefones")
	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", email=" + email + ", dataNascimento=" + dataNascimento + "]";
	}
	
	
}
