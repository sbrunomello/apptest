package com.brunomello.apptest;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.brunomello.apptest.domain.Categoria;
import com.brunomello.apptest.domain.Cidade;
import com.brunomello.apptest.domain.Cliente;
import com.brunomello.apptest.domain.Endereco;
import com.brunomello.apptest.domain.Estado;
import com.brunomello.apptest.domain.Pagamento;
import com.brunomello.apptest.domain.PagamentoComBoleto;
import com.brunomello.apptest.domain.PagamentoComCartao;
import com.brunomello.apptest.domain.Pedido;
import com.brunomello.apptest.domain.Produto;
import com.brunomello.apptest.domain.enums.EstadoPagamento;
import com.brunomello.apptest.domain.enums.TipoCliente;
import com.brunomello.apptest.repositories.CategoriaRepository;
import com.brunomello.apptest.repositories.CidadeRepository;
import com.brunomello.apptest.repositories.ClienteRepository;
import com.brunomello.apptest.repositories.EnderecoRepository;
import com.brunomello.apptest.repositories.EstadoRepository;
import com.brunomello.apptest.repositories.PagamentoRepository;
import com.brunomello.apptest.repositories.PedidoRepository;
import com.brunomello.apptest.repositories.ProdutoRepository;

@SpringBootApplication
public class ApptestApplication implements CommandLineRunner {

	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(ApptestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		
		
		
		
		Estado est1= new Estado (null, "Minas Gerais");
		Estado est2= new Estado (null, "São Paulo");
		
		Cidade c1 = new Cidade (null, "Uberlândia", est1);
		Cidade c2 = new Cidade (null, "São Paulo", est2);
		Cidade c3 = new Cidade (null, "Campinas", est2);
		
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "09174689756", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("128976567","876490287"));
		
		Endereco e1 = new Endereco(null, "Rua Floresta", "89", "Apto404", "Jardim", "82387876", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Pedro Paulo", "467", "Sobrado", "Centro", "35678665", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2018 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("15/10/2018 09:02"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("25/10/2018 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		
	}

}
