package JavaClientSocket_Calculadora.src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class CalculadoraClientSocket {

	public static void main(String[] args) {
		
		double oper1=20,oper2=10;
		int operacao=1; //1-somar 2-subtrair 3-dividir 4-multiplicar
		String result="";
		while(operacao < 5){
	        try {
	
	        	//Inserido IP da Conex�o com o Servidor
	            Socket clientSocket = new Socket("192.168.56.1", 9090);
	            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());
	            
	            //Enviando os dados
	            socketSaidaServer.writeBytes(operacao+"\n");
	            socketSaidaServer.writeBytes(oper1+ "\n");
	            socketSaidaServer.writeBytes(oper2+ "\n");
	            socketSaidaServer.flush();
	
	            //Recebendo a resposta
	            BufferedReader messageFromServer = new BufferedReader
	                    (new InputStreamReader(clientSocket.getInputStream()));
	            result=messageFromServer.readLine();
	            
	            System.out.println("Resultado no cliente = "+result);
	            clientSocket.close();
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        operacao ++;
		}
	}

}
