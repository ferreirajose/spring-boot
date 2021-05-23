package com.jose.cursomc;

import java.util.Arrays;

import com.jose.cursomc.domain.Categoria;
import com.jose.cursomc.domain.Cidade;
import com.jose.cursomc.domain.Estado;
import com.jose.cursomc.domain.Produto;

import com.jose.cursomc.repositories.CategoriaRepository;
import com.jose.cursomc.repositories.CidadeRepository;
import com.jose.cursomc.repositories.EstadoRepository;
import com.jose.cursomc.repositories.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// criando categorias no momento no qual sobe a aplicação
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "Computado", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		// adicionando produtos a categoria 1 = Informatica
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		// adicionando produtos a categoria 2 = Escritorio
		cat1.getProdutos().addAll(Arrays.asList(p2));

		// adicionando categoria as produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		/****************************************************/

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		Estado est3 = new Estado(null, "Paraiba");

		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "João Pessoa", est3);
		Cidade cid4 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid4));
		est3.getCidades().addAll(Arrays.asList(cid3));

		// Salvando primeiro os estados, pos estados tem cidades
		estadoRepository.saveAll(Arrays.asList(est1, est2, est3));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3, cid4));

	}

}
