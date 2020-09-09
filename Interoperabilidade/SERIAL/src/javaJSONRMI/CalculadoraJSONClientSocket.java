package javaJSONRMI;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONObject;
import org.json.JSONStringer;

public class CalculadoraJSONClientSocket {
	
	
	static String fazSerializacao( String expressao ) {
		
		Equacao equac = new Equacao();
		
		for(int i=0;i<expressao.length();i++) {
			if (Character.isDigit(expressao.charAt(i))) {
				equac.inseriConstantes( ""+expressao.charAt(i) );		
			}
			else {
				equac.inseriOperacoes( ""+expressao.charAt(i) );		
			}
		}
		
		JSONObject jo = new JSONObject(equac);
        return jo.toString();
	}

	public static void main(String[] args) {
		
		//double oper1=20,oper2=10;
		//int operacao=1; //1-somar 2-subtrair 3-dividir 4-multiplicar
		String result="" , expressao = "5-3/8+5*9";
		
	    try {
	
	        	//Inserido IP da Conexão com o Servidor
	            Socket clientSocket = new Socket("192.168.56.1", 9090);
	            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());
	            
	            //Enviando os dados
	            String df = fazSerializacao(expressao);
	            System.out.println("String  = "+df);
	            socketSaidaServer.writeBytes(df+"\n");
	            socketSaidaServer.flush();
	            System.out.println("Enviou, esperando ...");
	            //Recebendo a resposta
	            BufferedReader messageFromServer = new BufferedReader
	                    (new InputStreamReader(clientSocket.getInputStream()));
	            result=messageFromServer.readLine();
	            
	            System.out.println("Resultado no cliente = "+result);
	            clientSocket.close();
	
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
