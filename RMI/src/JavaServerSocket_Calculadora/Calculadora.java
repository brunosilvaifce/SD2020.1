package JavaServerSocket_Calculadora;

public class Calculadora {
    public String sayHello(String nome, String sobrenome) {
        return "Fala "+ nome + " " + sobrenome;
    }
    public double soma(double oper1, double oper2) {
        return oper1 + oper2;
    }
    
    //Novos m�todos
    public double sub(double oper1, double oper2) {
        return oper1 - oper2;
    }
    
    public double div(double oper1, double oper2) {
        return oper1 / oper2;
    }
    
    public double mul(double oper1, double oper2) {
        return oper1 * oper2;
    }
}