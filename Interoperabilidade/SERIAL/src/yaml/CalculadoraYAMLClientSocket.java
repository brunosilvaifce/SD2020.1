package yaml;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;

public class CalculadoraYAMLClientSocket {
	
	
	static String fazSerializacao( String expressao ) {
		
		Equacao equac = new Equacao();

		for(int i=0;i<expressao.length();i++) {
			if (Character.isDigit(expressao.charAt(i))) {
				equac.inseriConstantes( ""+expressao.charAt(i) );
			}
			else {
				if (expressao.charAt(i) == '+' ) {
					equac.inseriOperacoes( "o" );//+expressao.charAt(i)
				}
				if (expressao.charAt(i) == '-' ) {
					equac.inseriOperacoes( "s" );//+expressao.charAt(i)
				}
				if (expressao.charAt(i) == '/' ) {
					equac.inseriOperacoes( "d" );//+expressao.charAt(i)
				}
				if (expressao.charAt(i) == '*' ) {
					equac.inseriOperacoes( "m" );//+expressao.charAt(i)
				}
			}
		}
		YamlMapping equacion = Yaml.createYamlDump(
				equac
			).dumpMapping();
		
        return equacion.toString();
	}

	public static void main(String[] args) {
		
		//double oper1=20,oper2=10;
		//int operacao=1; //o-somar s-subtrair d-dividir m-multiplicar
		String result="" , expressao = "5-8/8+1*9";
		
	    try {
	
	        	//Inserido IP da Conexão com o Servidor
	            Socket clientSocket = new Socket("192.168.56.1", 9090);
	            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());
	            
	            //Enviando os dados
	            System.out.println("Equacao  = "+expressao);
	            String df = fazSerializacao(expressao);
	            System.out.println("String  = "+df);
	           	
	            socketSaidaServer.writeUTF(df);
	            socketSaidaServer.flush();
	            
	            System.out.println("Enviou, esperando ...");
	            DataInputStream dsf = new DataInputStream(clientSocket.getInputStream());
	            while(true)
	            {
	            	//result = dsf.readUTF();
		        	if(dsf.available()>0) {
		        		result = dsf.readUTF();
		            	if (!result.equals("")){
		            		break;
		            	}
		        	}
	            }
	            
	            System.out.println("Resultado no cliente = "+result);
	            clientSocket.close();
	
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
