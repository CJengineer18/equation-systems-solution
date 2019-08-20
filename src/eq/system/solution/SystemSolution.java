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
