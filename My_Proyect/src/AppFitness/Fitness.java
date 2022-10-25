package AppFitness;

import javax.swing.JOptionPane;

public class Fitness {
	
	public static void pesoIdeal() { /*metodo para averiguar el peso ideal de una persona*/
			
			String genero;
			int pesoIdeal = 0;
			int altura;
			
			do {
				genero = JOptionPane.showInputDialog("Introduce tu genero (H/M)"); // no distingue entre mayusculas y minisculas.
			}while (genero.equalsIgnoreCase("H") == false && genero.equalsIgnoreCase("M") == false);
				altura = Integer.parseInt(JOptionPane.showInputDialog("Intruduce altura en cm")); //showInputDialog convierte int en String.
			
			if (genero.equalsIgnoreCase("H")) {
				pesoIdeal = altura - 110;
			}else if  (genero.equalsIgnoreCase("M")){
				pesoIdeal = altura - 120;
			}
			System.out.println("Tu peso ideal es: " + pesoIdeal + (" kg"));
		}
	

	public static void main(String[] args) {

		pesoIdeal();
	}

}
