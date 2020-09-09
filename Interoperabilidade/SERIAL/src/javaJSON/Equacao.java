package javaJSON;

import java.util.ArrayList;
import java.util.List;

public class Equacao {
	
	List<String> operacoes = new ArrayList<String>();
	
	List<String> constantes = new ArrayList<String>();
	
	public List<String> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(List<String> operacoes) {
		this.operacoes = operacoes;
	}

	public void inseriOperacoes(String arg0) {
		this.operacoes.add(arg0);
	}

	public List<String> getConstantes() {
		return constantes;
	}

	public void setConstantes(List<String> constantes) {
		this.constantes = constantes;
	}
	
	public void inseriConstantes(String arg0) {
		this.constantes.add(arg0);
	}



	
}
