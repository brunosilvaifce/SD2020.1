package ExemploRMI.src;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Calculadora  implements ICalculadora {

	private static final long serialVersionUID = 1L;
	
	private static int chamadas = 0;
	private static int chamadasSomas = 0;
	private static int chamadasSub = 0;
	private static int chamadasMul = 0;
	private static int chamadasDiv = 0;
	private static int chamadasPot = 0;
	private static int chamadasMod = 0;
	
	
	public int soma(int a, int b) throws RemoteException {
		System.out.println("O total de chamadas " + chamadas++);
		System.out.println("M�todo soma foi chamado " + chamadasSomas++);
		return a + b;
	}
	
	public int sub(int a, int b) throws RemoteException {
		System.out.println("O total de chamadas " + chamadas++);
		System.out.println("M�todo subtra��o foi chamado " + chamadasSub++);
		return a - b;
	}
	
	public int mul(int a, int b) throws RemoteException {
		System.out.println("O total de chamadas " + chamadas++);
		System.out.println("M�todo multiplica��o foi chamado " + chamadasMul++);
		return a * b;
	}
	
	public int div(int a, int b) throws RemoteException {
		System.out.println("O total de chamadas " + chamadas++);
		System.out.println("M�todo divis�o foi chamado " + chamadasDiv++);
		return a / b;
	}
	
	public double exp(int a) throws RemoteException {
		System.out.println("O total de chamadas " + chamadas++);
		System.out.println("M�todo potencia��o foi chamado " + chamadasPot++);
		return  Math.exp((double)a);
	}
	
	public int modulo(int a) throws RemoteException {
		System.out.println("O total de chamadas " + chamadas++);
		System.out.println("M�todo m�dulo foi chamado " + chamadasMod++);
		return Math.abs(a);
	}

	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException  {
		Calculadora calculadora = new Calculadora();		
		Registry reg = null;
		ICalculadora stub = (ICalculadora) UnicastRemoteObject.
				exportObject(calculadora, 1100);
		try {
			System.out.println("Creating registry...");
			reg = LocateRegistry.createRegistry(1099);
		} catch (Exception e) {
			try {
				reg = LocateRegistry.getRegistry(1099);
			} catch (Exception e1) {
				System.exit(0);
			}
		}
		reg.rebind("calculadora", stub);
	}
}
