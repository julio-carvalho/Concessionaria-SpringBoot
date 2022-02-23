package br.com.concessionaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.concessionaria.dto.CarroDTO;
import br.com.concessionaria.model.Carro;
import br.com.concessionaria.repository.CarroRepository;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	public CarroService(CarroRepository carroRepository) {
		this.carroRepository = carroRepository;
	}
	
	// criar carro
	public boolean create(Carro carro) {
		try {
			this.carroRepository.save(carro);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public Carro crateCarro(Carro carro) {
		carro.setDisponivel(true);
		return carroRepository.save(carro);
	}
	
	public Carro salvarCarro(Carro carro) {
		return carroRepository.save(carro);
	}
	
	public List<Carro> buscarCarros() {
		return carroRepository.findAll();
	}
	
	public Optional<Carro> findById(Long id) {
		return carroRepository.findById(id);
	}
	
	public void delete(Long id) {
		carroRepository.delete(this.getCarro(id));
	}
	
	public Carro getCarro(Long id) {
		return carroRepository.getById(id);
	}
	
	public void update (Carro carro) {
		Optional<Carro> carroUpdate = carroRepository.findById(carro.getId());
		this.atualizarDados(carroUpdate, carro);
		carroRepository.save(carroUpdate.get());
	}
	
	private void atualizarDados(Optional<Carro> carroUpdate, Carro carro) {
		carroUpdate.get().setCategoria(carro.getCategoria());
		carroUpdate.get().setValor(carro.getValor());
		carroUpdate.get().setAno(carro.getAno());
		carroUpdate.get().setModelo(carro.getModelo());
		carroUpdate.get().setMarca(carro.getMarca());
	}

	public CarroDTO transformaDTO(Carro carro) {
		CarroDTO carroDTO = new CarroDTO();
		carroDTO.setId(carro.getId());
		carroDTO.setAno(carro.getAno());
		carroDTO.setCategoria(carro.getCategoria());
		carroDTO.setValor(carro.getValor());
		carroDTO.setModelo(carro.getModelo());
		carroDTO.setMarca(carro.getMarca());
		carroDTO.setDisponivel(true);
		return carroDTO;
	}
	

}
