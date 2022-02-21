package br.com.concessionaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.concessionaria.model.Carro;
import br.com.concessionaria.repository.CarroRepository;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	public CarroService(CarroRepository carroRepository) {
		this.carroRepository = carroRepository;
	}
	
	// criar cliente
	public boolean create(Carro carro) {
		try {
			this.carroRepository.save(carro);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
