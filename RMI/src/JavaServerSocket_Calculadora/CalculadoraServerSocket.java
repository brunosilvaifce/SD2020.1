package JavaServerSocket_Calculadora;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class CalculadoraServerSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket welcomeSocket;
		DataOutputStream socketOutput;     	
	    DataInputStream socketInput;
	    BufferedReader socketEntrada;
	    Calculadora calc = new Calculadora();
	    
	    //C�digo Inserido
	    InetAddress myself = null;
		try {
			myself = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		//-------------------------------------
	    
		try {
			
			//Alterado a cria��o do ServerSocket que estava criando no endere�o 0.0.0.0
			welcomeSocket = new ServerSocket(9090,0,myself);
			
		  int i=0; //n�mero de clientes
	  
		  //Alterado o print do servidor para indicar o local da rede que est�o e facilitar o acesso ao ClienteSocket.
	      System.out.println ("Servidor no ar: "+welcomeSocket.getInetAddress().toString());
	      while(true) { 
	  
	           Socket connectionSocket = welcomeSocket.accept(); 
	           i++;
	           String result= "";
	           System.out.println ("Nova conex�o: ");
	           
	           //Interpretando dados do servidor
	           socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
               String operacao= socketEntrada.readLine();
               String oper1=socketEntrada.readLine();
               String oper2=socketEntrada.readLine();
               
               //Alterado a calculadora para adequar as novas funcionalidades.
               //System.out.println ("operacao: "+operacao+" oper1 "+ oper1+ " oper2 "+ oper2);
               if (operacao.equals( "1")) {//somar
            	   result= ""+calc.soma(Double.parseDouble(oper1),Double.parseDouble(oper2));
               }
               else if(operacao.equals( "2")) {//sub
            	   result= ""+calc.sub(Double.parseDouble(oper1),Double.parseDouble(oper2));
               }
               else if(operacao.equals( "3")) {//div
            	   result= ""+calc.div(Double.parseDouble(oper1),Double.parseDouble(oper2));
               }
               else if(operacao.equals( "4")) {//mul
            	   result= ""+calc.mul(Double.parseDouble(oper1),Double.parseDouble(oper2));
               }  //-------------------------------
               //Enviando dados para o cliente
               socketOutput= new DataOutputStream(connectionSocket.getOutputStream());     	
	           socketOutput.writeBytes(result+ '\n');
	           System.out.println ("Resultado no server: "+result);	        
	             
	           socketOutput.flush();
	           socketOutput.close();
	           System.out.println ("Quantidade de clientes que j� acessou: "+i);	 

	                    
	      }
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    
	}

}
