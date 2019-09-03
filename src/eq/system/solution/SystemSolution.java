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

package eq.system.solution;

import org.jblas.DoubleMatrix;

import eq.system.linear.algebra.MatrixOperations;

public abstract class SystemSolution {

	public abstract void solution(double[][] a, double[] b, double[] initialValues, int precision, double[] roots);

	public abstract boolean convergence(double[][] a);

	public final void solution(double[][] a, double[] b, double[] initialValues, int precision) {
		solution(a, b, initialValues, precision, null);
	};

	// Jacobi
	protected final DoubleMatrix iterationMatrix(DoubleMatrix a) {
		DoubleMatrix[] ldu = decompose(a);
		DoubleMatrix inverse = MatrixOperations.inverse(ldu[1]);

		return (inverse == null) ? null : inverse.mmul(ldu[0].add(ldu[2])).neg();
	}

	protected final DoubleMatrix[] decompose(DoubleMatrix matrix) {
		double[][] data = matrix.toArray2();
		double[][] upper = new double[matrix.rows][matrix.columns];
		double[][] lower = new double[matrix.rows][matrix.columns];
		DoubleMatrix diag = DoubleMatrix.diag(matrix.diag());

		int i;
		int j;

		for (i = 0; i < data.length; i++) {
			for (j = 0; j < data[i].length; j++) {
				if (i < j) {
					upper[i][j] = data[i][j];
				} else if (i > j) {
					lower[i][j] = data[i][j];
				}
			}
		}

		return new DoubleMatrix[] { new DoubleMatrix(lower), diag, new DoubleMatrix(upper) };
	}

	protected final DoubleMatrix roundVector(int precision, DoubleMatrix vector) {
		return vector.isVector() ? new DoubleMatrix(roundVector(precision, vector.toArray())) : null;
	}

	protected final double[] roundVector(int precision, double[] vector) {
		double[] r = new double[vector.length];
		double pow10 = Math.pow(10, precision);
		int i;

		for (i = 0; i < vector.length; i++) {
			r[i] = (double) Math.round(vector[i] * pow10) / pow10;
		}

		return r;
	}

}
