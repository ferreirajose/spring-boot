package com.jose.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.jose.cursomc.domain.Categoria;
import com.jose.cursomc.domain.Cidade;
import com.jose.cursomc.domain.Cliente;
import com.jose.cursomc.domain.Endereco;
import com.jose.cursomc.domain.Estado;
import com.jose.cursomc.domain.ItemPedido;
import com.jose.cursomc.domain.Pagamento;
import com.jose.cursomc.domain.PagamentoComBoleto;
import com.jose.cursomc.domain.PagamentoComCartao;
import com.jose.cursomc.domain.Pedido;
import com.jose.cursomc.domain.Produto;
import com.jose.cursomc.domain.enums.EstadoPagamento;
import com.jose.cursomc.domain.enums.TipoCliente;
import com.jose.cursomc.repositories.CategoriaRepository;
import com.jose.cursomc.repositories.CidadeRepository;
import com.jose.cursomc.repositories.ClienteRepository;
import com.jose.cursomc.repositories.EnderecoRepository;
import com.jose.cursomc.repositories.EstadoRepository;
import com.jose.cursomc.repositories.ItemPedidoRepository;
import com.jose.cursomc.repositories.PagamentoRepository;
import com.jose.cursomc.repositories.PedidoRepository;
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

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

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

		/****************************************************/

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "09487329448", TipoCliente.PF);
		cli1.getTelefones().addAll(Arrays.asList("83993820492", "11993940292"));
		
		Endereco end1 = new Endereco(null, "Ruas Flores", "300", "Apto 303", "Jardim", "38220834", cli1, cid1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cid2);

		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));

		/****************************************************/

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, end2);

		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pag1);

		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pag2);


		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));


		/************************ ITEM PEDIDO ****************************/

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3= new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));


	/************************ ITEM PEDIDO ****************************/



	}

}
