package br.com.concessionaria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.concessionaria.dto.ClienteDTO;
import br.com.concessionaria.model.Carro;
import br.com.concessionaria.model.Cliente;
import br.com.concessionaria.repository.CarroRepository;
import br.com.concessionaria.repository.ClienteRepository;


public class VendasController {
	
	private double somaVendidos = 0;
	
	@Autowired
	private CarroRepository carroRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	

}
