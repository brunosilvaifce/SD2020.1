package javaJSONRMI;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class CalculadoraJSONServerSocket {

	static Equacao desFazSerializacao( String expressao ) {

		Equacao equac = new Equacao();

		JSONTokener jsonParser = new JSONTokener(expressao);

        JSONObject content = (JSONObject) jsonParser.nextValue();
        JSONArray list = content.getJSONArray("operacoes");
        JSONArray list2 = content.getJSONArray("constantes");
        
        for (Object object : list.toList()) {
        	equac.inseriOperacoes(object.toString());
		}

        for (Object object : list2.toList()) {
        	equac.inseriConstantes(object.toString());
		}
        
        
        return equac;
	}
	
	static String montarEquacao(Equacao equac) {
		String resultado = "";
		resultado = resultado + equac.getConstantes().get(0);
		int j=0;
		for(int i = 1; i<= equac.getConstantes().size() && j<equac.getOperacoes().size();i=i+1, j=j+1){
			resultado= resultado + equac.getOperacoes().get(j);
            resultado= resultado + equac.getConstantes().get(i);
            
        }
		
		return resultado;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket welcomeSocket;
		DataOutputStream socketOutput;     	
	    DataInputStream socketInput;
	    BufferedReader socketEntrada;
	    
	    Binding binding   = new Binding();
	    GroovyShell shell = new GroovyShell(binding);
	    
	    //Código Inserido
	    InetAddress myself = null;
		try {
			myself = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		//-------------------------------------
	    
		try {
			
			//Alterado a criação do ServerSocket que estava criando no endereço 0.0.0.0
			welcomeSocket = new ServerSocket(9090,0,myself);
			
		  int i=0; //número de clientes
	  
		  //Alterado o print do servidor para indicar o local da rede que estão e facilitar o acesso ao ClienteSocket.
	      System.out.println ("Servidor no ar: "+welcomeSocket.getInetAddress().toString());
	      while(true) { 
	  
	           Socket connectionSocket = welcomeSocket.accept(); 
	           i++;
	           Object result= "-2";
	           //System.out.println ("Nova conexão: ");
	           
	           //Interpretando dados do servidor
	           socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

	           
	           String equaStr = socketEntrada.readLine().toString();

	           System.out.println ("equaStr: " + equaStr);
	           Equacao equac = desFazSerializacao(  equaStr );
	           
	           String formula = montarEquacao(equac);
	           System.out.println ("Formula: " + formula);
 
	           result = shell.evaluate(formula);
	           
	           //Enviando dados para o cliente
               socketOutput= new DataOutputStream(connectionSocket.getOutputStream());     	
	           socketOutput.writeBytes(result.toString()+ '\n');
	           System.out.println ("Resultado no server: "+result);	        
	             
	           socketOutput.flush();
	           socketOutput.close();
	           System.out.println ("Quantidade de clientes que já acessou: "+i);	 

	                    
	      }
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    
	}

}
