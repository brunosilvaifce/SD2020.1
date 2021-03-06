package ExemploRMI.src;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class CalculadoraCliente {
	
	public static void main(String[] args) {
		Registry reg = null;
		ICalculadora calc;		
		try {
			reg = LocateRegistry.getRegistry(1099);
			calc = (ICalculadora) reg.lookup("calculadora");
			System.out.println("A soma de 3 + 2 = "+calc.soma(3,2));
			System.out.println("A subtra��o de 3 - 2 = "+calc.sub(3,2));
			System.out.println("A multiplica��o de 3 * 2 = "+calc.mul(3,2));
			System.out.println("A divis�o de 6 / 2 = "+calc.div(6,2));
			System.out.println("O potencial neperiano de e^2 = "+calc.exp(2));
			System.out.println("O operador m�dulo = s"+calc.modulo(-4));
		} catch (RemoteException | NotBoundException e) {
				System.out.println(e);
				System.exit(0);
		}
	}		

}
