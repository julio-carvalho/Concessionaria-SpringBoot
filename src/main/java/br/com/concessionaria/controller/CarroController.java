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
import br.com.concessionaria.service.CarroService;

@Controller
@RequestMapping("/carro")
public class CarroController {
	
	private double somaVendidos = 0;
	
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
		
		carroRepository.save(carro);
		carroService.alteraCarroDisponivel(carro);
		return "redirect:/carro";
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
			return "redirect:/carro?carroindisponivel";
		}
		
		Cliente cli = clienteRepository.findByCpf(clienteDTO.getCpf());
		
		if(cli == null) {
			return "redirect:/carro?clienteinvalido";
		}
		
		carroService.alteraCarro(buscaCarroId.get());
		
		this.somaVendidos += buscaCarroId.get().getValor();
		
		return "redirect:/";
	}
	
	public CarroService getCarroService() {
		return new CarroService(this.carroRepository);
	}
	
}
