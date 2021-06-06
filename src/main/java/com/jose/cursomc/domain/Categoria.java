package com.jose.cursomc.domain;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
public class Categoria implements Serializable {
	/**  
	 * Serializable = diz que a class pode ser convertida para uma sequencia de bytes, que ela pode trafegar pela rede HTTP: ex: em formato JSON, que os objetos
	 * podem ser gravados em disco/texto
	*/

	private static final long serialVersionUID = 1L; // todo a class que implementa Serializable, deve informa a versão da class que 1L = a primeira versão

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;

	/** 
	 * o mesmo mapeamento que foi feito em produto, esta sendo feito em categoria
	 * a propriedade mappedBy, "meio que espelha o trabalho feito em produto"
	 * 
	 *  junto com @JsonIgnore server para evitar o problema de referencia circula entr
     * categoria e produtos, evitando que gere um json onde gerar um encadeamento sem fim de categorias e produto
	*/

	
	@ManyToMany(mappedBy="categorias")
	private List<Produto> produtos = new ArrayList<>();
	
	// Esse ocorreu pois não tinha um construtor vazio public Categoria() {} No default constructor for entity: :
	
	public Categoria() {
	}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
