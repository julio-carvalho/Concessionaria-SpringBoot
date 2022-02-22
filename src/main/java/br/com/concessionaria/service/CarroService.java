package br.com.concessionaria.service;

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
