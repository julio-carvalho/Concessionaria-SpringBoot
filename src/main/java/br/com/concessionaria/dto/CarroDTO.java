package br.com.concessionaria.dto;

public class CarroDTO {
	private Long id;
	private String categoria;
	private double valor;
	private Integer ano;
	private String modelo;
	private String marca;
	private boolean disponivel;
	
	public CarroDTO() {
		super();
	}
	public CarroDTO(Long id, String categoria, double valor, Integer ano, String modelo, String marca,
			boolean disponivel) {
		super();
		this.id = id;
		this.categoria = categoria;
		this.valor = valor;
		this.ano = ano;
		this.modelo = modelo;
		this.marca = marca;
		this.disponivel = disponivel;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public boolean isDisponivel() {
		return disponivel;
	}
	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
}
