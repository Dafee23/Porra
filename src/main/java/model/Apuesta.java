package model;

import java.math.BigDecimal;

public class Apuesta {
	
	private BigDecimal golesL;
	private BigDecimal golesV;
	private Long numApuestas;
	
	public Apuesta(BigDecimal golesL, BigDecimal golesV, Long numApuestas) {
		super();
		this.golesL = golesL;
		this.golesV = golesV;
		this.numApuestas = numApuestas;
	}

	public BigDecimal getGolesL() {
		return golesL;
	}

	public void setGolesL(BigDecimal golesL) {
		this.golesL = golesL;
	}

	public BigDecimal getGolesV() {
		return golesV;
	}

	public void setGolesV(BigDecimal golesV) {
		this.golesV = golesV;
	}

	public Long getNumApuestas() {
		return numApuestas;
	}

	public void setNumApuestas(Long numApuestas) {
		this.numApuestas = numApuestas;
	}
	
}