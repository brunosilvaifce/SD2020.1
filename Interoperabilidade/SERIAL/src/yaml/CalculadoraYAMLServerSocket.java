package yaml;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class CalculadoraYAMLServerSocket {

	static Equacao desFazSerializacao( String expressao ) {
		
		Equacao equac = new Yaml(new Constructor(Equacao.class)).loadAs(
				expressao, Equacao.class);
		
    	List<String> operacoes = new ArrayList<String>();
    	for(String strs: equac.getOperacoes()) {
    	
	    	if (strs.equals("o")) {
	    		operacoes.add( "+" );//+expressao.charAt(i)
			}
			if (strs.equals("s" )) {
				operacoes.add( "-" );//+expressao.charAt(i)
			}
			if (strs.equals("d" )) {
				operacoes.add( "/" );//+expressao.charAt(i)
			}
			if (strs.equals("m" )) {
				operacoes.add( "*" );//+expressao.charAt(i)
			}
    	}
    	equac.setOperacoes(operacoes);     
        
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
	           
	           //Interpretando dados do servidor
	           DataInputStream dsf = new DataInputStream(connectionSocket.getInputStream());
	           String equaStr = "";
	           while(true) {
	               
	               // reads characters encoded with modified UTF-8
	        	   
	        	   if(dsf.available()>0) {
	        		   equaStr = dsf.readUTF();
		        	   if (!equaStr.equals(""))
		        		   break;
		        	   }
	           		}
	           
	           Equacao equac = desFazSerializacao(  equaStr );
	           
	           String formula = montarEquacao(equac);
	           System.out.println ("Formula: " + formula);
 
	           result = shell.evaluate(formula);
	           
	           //Enviando dados para o cliente
               socketOutput= new DataOutputStream(connectionSocket.getOutputStream());     	
	           socketOutput.writeUTF(result.toString());
	           socketOutput.flush();
	           System.out.println ("Resultado no server: "+result);	
	           socketOutput.close();
	           System.out.println ("Quantidade de clientes que já acessou: "+i);	 

	                    
	      }
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    
	}

}
