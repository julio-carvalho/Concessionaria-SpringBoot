package br.com.concessionaria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.concessionaria.model.Cliente;
import br.com.concessionaria.repository.ClienteRepository;
import br.com.concessionaria.service.ClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("")
	public String getClientes(Model model) {
		model.addAttribute("listaClientes", clienteRepository.findAll());
		return "cliente";
	}
		
	@GetMapping("/cadastro")
	public String getCadastroCliente(@ModelAttribute("cliente") Cliente cliente) {
		return "/cadastro-cliente";
	}
	
	@PostMapping("/cadastro")
	public String salvarCliente(@ModelAttribute("cliente") Cliente cliente) {
		cliente.setPrimeira(true);
		clienteRepository.save(cliente);
		
		return "redirect:/cliente";
	}
	
	@GetMapping("/{id}")
	public String updateCliente(@PathVariable("id") long id, Model model) {
		Optional<Cliente> buscaClienteId = clienteRepository.findById(id);
		
		if(buscaClienteId.isEmpty()) {
			throw new IllegalArgumentException("Cliente inválido.");
		}
		
		model.addAttribute("cliente", buscaClienteId.get());
		return "/cadastro-cliente";
	}
	
	@GetMapping("/excluir/{id}")
	public String deleteCliente(@PathVariable("id") long id) {
		Optional<Cliente> buscaClienteId = clienteRepository.findById(id);
		
		if(buscaClienteId.isEmpty()) {
			throw new IllegalArgumentException("Cliente inválido.");
		}
		
		clienteRepository.delete(buscaClienteId.get());
		return "redirect:/cliente";
	}
	
	public ClienteService getClienteService() {
		return new ClienteService(this.clienteRepository);
	}
}
