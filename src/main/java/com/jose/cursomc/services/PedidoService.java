package com.jose.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.Email;

import com.jose.cursomc.domain.ItemPedido;
import com.jose.cursomc.domain.PagamentoComBoleto;
import com.jose.cursomc.domain.Pedido;
import com.jose.cursomc.domain.enums.EstadoPagamento;
import com.jose.cursomc.repositories.ItemPedidoRepository;
import com.jose.cursomc.repositories.PagamentoRepository;
import com.jose.cursomc.repositories.PedidoRepository;
import com.jose.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
    /** 
     * @Autowired instancia automaticamento a PedidoRepository utilizando injeção de depencias ou inversão de controle
     * ps: pequisar sobre inversão de controle
     * */ 
    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;


    @Autowired
    private EmailService emailService;


    public Pedido buscar(Integer id) {

        /** 
         * Optional foi add na versão 8 do JAVA, Antes era
         * public Pedido find(Integer id) {
         *   Pedido obj = repo.findOne(id);
         *   return obj;
         *  }
         * Optional foi add para resolver o problema null point exception, pois caso não tenha valor o retorno sera NULL
        */
        // Optional<Pedido> obj = repo.findById(id); 
        // return obj.orElse(null); // caso não econtre o valor informado retorna NULL

        Optional<Pedido> obj = repo.findById(id); 

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado com Id: " + id + ", Tipo: " + Pedido.class.getName()));
        
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        // pegando o cliente atraves do ID e atribuindo ao Pedido
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        
        if (obj.getPagamento() instanceof PagamentoComBoleto ) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamamentoComBoleto(pagto, obj.getInstante());
        }

        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            // associando item de pedido com produto encontrado na base de dados
            ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }

        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }

}
