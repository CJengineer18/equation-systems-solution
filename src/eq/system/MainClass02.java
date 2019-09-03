/*
 * Copyright (c) 2019 Cristian José Jiménez Diazgranados
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
		int nVariables;
		int p;
		int i;
		String str;

		System.out.println("Solution of equation systems");
		System.out.println();
		System.out.println("Step 1: Definition of the system of equations (Ax=B)");
		System.out.print("Indicate the number of variables / equations (System nxn): ");

		nVariables = keyboard.nextInt();
		a = new double[nVariables][nVariables];
		b = new double[nVariables];
		initial = new double[nVariables];

		System.out.println();
		System.out.println("Insert the values of the rows of matrix A, each value separated by comma:");

		for (i = 0; i < nVariables; i++) {
			System.out.printf("Row %d: ", (i + 1));
			a[i] = convertToVector(keyboard.next(), nVariables);
		}

		System.out.println();
		System.out.print("Insert vector B, with comma separated values: ");

		b = convertToVector(keyboard.next(), nVariables);

		System.out.println();
		System.out.println("Step 2: Selection of method to use");
		System.out.print("Enter the initial values (x0), separated by comma: ");
		initial = convertToVector(keyboard.next(), nVariables);

		System.out.println();
		System.out.print("Insert the precision digits (-1 to indicate all digits): ");
		p = keyboard.nextInt();

		System.out.println();
		System.out.println("Select the method to use: ");
		System.out.println();
		System.out.println("1- Jacobi");
		System.out.println("2- Gauss-Seidel");
		System.out.println();
		System.out.print("Choose: ");
		i = keyboard.nextInt();

		while (i > 2 || i <= 0) {
			System.err.println("Invalid choose!");
			System.out.print("Choose: ");
			i = keyboard.nextInt();
		}

		System.out.println();
		System.out.print("Selected method: ");

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
		System.out.println("Calculating convergence criteria ...");
		if (currentMethod.convergence(a)) {
			System.out.println("Executing...");
			currentMethod.solution(a, b, initial, p);
		} else {
			System.out.println();
			System.out.println("The convergence criterion indicates divergence, therefore the");
			System.out.println("solution cannot be calculated with the selected method. Do you");
			System.out.print("want to try the other method? (y/n) ");
			str = keyboard.next();
			System.out.println();

			while (!(str.equals("y") || str.equals("n"))) {
				System.err.println("Wrong answer!");
				System.out.print("Do you want to try the other method? (y/n)");
				str = keyboard.next();
			}

			if (str.equals("y")) {
				System.out.print("Switching to ");
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
				System.out.println("Calculating convergence criteria ...");
				if (currentMethod.convergence(a)) {
					System.out.println("Executing ...");
					currentMethod.solution(a, b, initial, p);
				} else {
					System.err.println("The two methods diverge!");
				}
			}
		}

		System.out.println("Cleaning...");
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
