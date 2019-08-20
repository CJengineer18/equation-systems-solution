package eq.system;

import java.util.Scanner;

import eq.system.solution.SystemSolution;
import eq.system.solution.method.GaussSeidel;
import eq.system.solution.method.Jacobi;

public class MainClass02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner keyboard = new Scanner(System.in);
		SystemSolution currentMethod = null;

		double[][] a;
		double[] b;
		double[] initial;
		// double[] roots;
		int nVariables;
		int p;
		int i;
		String str;

		System.out.println("Solucion de sistemas de ecuaciones");
		System.out.println();
		System.out.println("Paso 1: Definicion del sistema de ecuaciones (Ax=B)");
		System.out.print("Indique el numero de variables/ecuaciones (Sistema nxn): ");

		nVariables = keyboard.nextInt();
		a = new double[nVariables][nVariables];
		b = new double[nVariables];
		initial = new double[nVariables];
		// roots = new double[nVariables];

		System.out.println();
		System.out.println("Inserte los valores de la matrix A, cada fila separada por coma:");

		for (i = 0; i < nVariables; i++) {
			System.out.printf("Fila %d: ", (i + 1));
			a[i] = convertToVector(keyboard.next(), nVariables);
		}

		System.out.println();
		System.out.print("Inserte el vector B, separados por coma: ");

		b = convertToVector(keyboard.next(), nVariables);

		System.out.println();
		System.out.println("Paso 2: Seleccion de metodo a utilizar");
		System.out.print("Escriba los valores iniciales, separados por coma: ");
		initial = convertToVector(keyboard.next(), nVariables);

		System.out.println();
		System.out.println("Inserte los digitos de precision (-1 para indicar todos los");
		System.out.print("digitos): ");
		p = keyboard.nextInt();

		System.out.println();
		System.out.println("Seleccione el metodo a utilizar: ");
		System.out.println();
		System.out.println("1- Jacobi");
		System.out.println("2- Gauss-Seidel");
		System.out.println();
		System.out.print("Seleccione: ");
		i = keyboard.nextInt();

		while (i > 2 || i <= 0) {
			System.err.println("Seleccion invalida!");
			System.out.print("Seleccione: ");
			i = keyboard.nextInt();
		}

		System.out.println();
		System.out.print("Metodo seleccionado: ");

		switch (i) {
		case 1:
			currentMethod = new Jacobi();
			System.out.println("Jacobi");
			break;
		case 2:
			currentMethod = new GaussSeidel();
			System.out.println("Gauss-Seidel");
			break;
		}

		System.out.println();
		System.out.println("Calculando criterio de convergencia...");
		if (currentMethod.convergence(a)) {
			System.out.println("Ejecutando...");
			currentMethod.solution(a, b, initial, p);
		} else {
			System.out.println();
			System.out.println("El criterio de convergencia indica divergencia, por lo");
			System.out.println("tanto no se puede calcular la solucion con el metodo");
			System.out.print("seleccionado. Desea probar el otro metodo? (y/n) ");
			str = keyboard.next();
			System.out.println();

			while (!(str.equals("y") || str.equals("n"))) {
				System.err.println("Respuesta incorrecta!");
				System.out.print("Desea probar el otro metodo? (y/n)");
				str = keyboard.next();
			}

			if (str.equals("y")) {
				System.out.print("Cambiando a ");
				switch (i) {
				case 1:
					currentMethod = new GaussSeidel();
					System.out.println("Gauss-Seidel");
					break;
				case 2:
					currentMethod = new Jacobi();
					System.out.println("Jacobi");
					break;
				}
				System.out.println();
				System.out.println("Calculando criterio de convergencia...");
				if (currentMethod.convergence(a)) {
					System.out.println("Ejecutando...");
					currentMethod.solution(a, b, initial, p);
				} else {
					System.err.println("Los dos metodos divergen!");
				}
			}
		}

		System.out.println("Limpiando...");
		keyboard.close();
	}

	public static double[] convertToVector(String str, int limit) {
		String[] svec = str.split(",");
		double[] vec = new double[limit];

		for (int i = 0; i < vec.length; i++) {
			vec[i] = Double.parseDouble(svec[i]);
		}

		return vec;
	}

}
