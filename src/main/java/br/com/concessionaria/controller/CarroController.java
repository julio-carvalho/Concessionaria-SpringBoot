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

import br.com.concessionaria.model.Carro;
import br.com.concessionaria.repository.CarroRepository;
import br.com.concessionaria.service.CarroService;

@Controller
@RequestMapping("/carro")
public class CarroController {
	
	@Autowired
	private CarroRepository carroRepository;
	
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
	
	public CarroService getCarroService() {
		return new CarroService(this.carroRepository);
	}
	
}
