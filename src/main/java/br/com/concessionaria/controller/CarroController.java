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

import br.com.concessionaria.dto.CarroDTO;
import br.com.concessionaria.dto.ClienteDTO;
import br.com.concessionaria.model.Carro;
import br.com.concessionaria.model.Cliente;
import br.com.concessionaria.repository.CarroRepository;
import br.com.concessionaria.repository.ClienteRepository;
import br.com.concessionaria.service.CarroService;

@Controller
@RequestMapping("/carro")
public class CarroController {
	
	@Autowired
	private CarroService carroService;
	
	@Autowired
	private CarroRepository carroRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("")
	public String getCarros(Model model) {
		model.addAttribute("listaCarros", carroRepository.findAll());
		return "carro";
	}
		
	@GetMapping("/cadastro")
	public String getCadastroCarro(@ModelAttribute("carro") Carro carro) {
		return "/cadastro-carro";
	}
	
	@PostMapping("/cadastro")
	public String salvarCarro(@ModelAttribute("carro") Carro carro) {
		if(this.validaCadastroCarro(carro)) {
			carro.setDisponivel(true);
			carroRepository.save(carro);
			CarroDTO carroDTO = carroService.transformaDTO(carro);
			
			return "redirect:/carro";
		} else {
			return "redirect:/carro/cadastro?invalido";
		}
	}
	
	@GetMapping("/{id}")
	public String updateCarro(@PathVariable("id") long id, Model model) {
		Optional<Carro> buscaCarroId = carroRepository.findById(id);
		
		if(buscaCarroId.isEmpty()) {
			throw new IllegalArgumentException("Carro inválido.");
		}
		
		model.addAttribute("carro", buscaCarroId.get());
		return "/cadastro-carro";
	}
	
	@GetMapping("/excluir/{id}")
	public String deleteCarro(@PathVariable("id") long id) {
		Optional<Carro> buscaCarroId = carroRepository.findById(id);
		
		if(buscaCarroId.isEmpty()) {
			throw new IllegalArgumentException("Carro inválido.");
		}
		
		carroRepository.delete(buscaCarroId.get());
		return "redirect:/carro";
	}
	
	@GetMapping("/venda/{id}")
	public String getVendas(ModelMap model) {
		ClienteDTO cliDTO = new ClienteDTO();
		model.addAttribute("clienteDTO", cliDTO);
		return "busca-cliente";
	}
	
	@PostMapping("/venda/{id}")
	public String buscaCPF(@PathVariable("id") long id, Model model, ClienteDTO clienteDTO, ModelMap modelMap) {
		Optional<Carro> buscaCarroId = carroRepository.findById(id);
		
		System.out.print(buscaCarroId.get().isDisponivel());
		if(!buscaCarroId.get().isDisponivel()) {
			return "redirect:/carro/venda/{id}#cg";
		}
		
		Cliente cli = clienteRepository.findByCpf(clienteDTO.getCpf());
		
		if(cli == null) {
			return "redirect:/carro/venda/{id}#bg";
		}
		
		if(cli.isPrimeira()) {
			System.out.println("entrou aqui");
			double valor = buscaCarroId.get().getValor();
			System.out.println("Valor: " + valor);
			
			double valorFinal = valor * (1 -(1 / 100));
			System.out.println(valorFinal);
			buscaCarroId.get().setValor(valorFinal);			
		}
		
		buscaCarroId.get().setDisponivel(false);
		carroRepository.save(buscaCarroId.get());
		
		cli.setPrimeira(false);
		clienteRepository.save(cli);
		
		return "redirect:/carro?carrovendido";
		
	}
	
	@GetMapping("/totalvenda")
	public String getTotalVendas(ModelMap model) {
		double valorTotal = carroRepository.valor();
		
		model.addAttribute("valorvendas", valorTotal);
		return "total-vendas";
	}
	
	public CarroService getCarroService() {
		return new CarroService(this.carroRepository);
	}
	
	//valida informações do cadastro
	public boolean validaCadastroCarro(Carro carro) {
		if(carro.getCategoria().isEmpty() || carro.getCategoria() == null)
			return false;
		if(carro.getAno() == null)
			return false;
		if(carro.getMarca().isEmpty() || carro.getMarca() == null)
			return false;
		if(carro.getModelo().isEmpty() || carro.getModelo() == null)
			return false;
		
		return true;
	}
	
}
