package eq.system.solution.method;

import java.util.ArrayList;
import java.util.Locale;

import org.jblas.DoubleMatrix;
import org.jblas.Eigen;

import com.github.cjengineer18.desktopwindowtemplate.util.async.AsyncProcessLoading;

import eq.system.gui.Window01;
import eq.system.linear.algebra.MatrixOperations;
import eq.system.solution.SystemSolution;

public class GaussSeidel extends SystemSolution {

	@Override
	public void solution(double[][] a, double[] b, double[] initialValues, int precision, double[] roots) {
		// TODO Auto-generated method stub
		DoubleMatrix matrixA = new DoubleMatrix(a);
		DoubleMatrix iteration = iterationMatrix(matrixA);
		DoubleMatrix c = new DoubleMatrix(b).div(matrixA.diag());
		ArrayList<double[]> possibleResults = new ArrayList<double[]>();
		ArrayList<Double> absError = new ArrayList<Double>();

		AsyncProcessLoading.loadAsyncProcess(null, new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				DoubleMatrix result = new DoubleMatrix(initialValues).dup();

				DoubleMatrix k1;
				double current;
				double n2;
				int i;

				do {
					k1 = result.dup();

					for (i = 0; i < iteration.rows; i++) {
						current = iteration.getRow(i).mmul(result).sum() + c.get(i);
						result.put(i, current);
					}

					if (precision > -1) {
						result = roundVector(precision, result);
					}

					possibleResults.add(result.toArray());
					n2 = result.sub(k1).norm2();

					if (precision > -1) {
						absError.add(roundVector(precision, new double[] { n2 })[0]);
					} else {
						absError.add(n2);
					}

				} while (n2 >= 0.0001);
			}

		}, "Calculating", "Calculating ...");

		try {
			System.out.println("Showing results ...");
			new Window01("Gauss-Seidel", possibleResults, absError);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean convergence(double[][] a) {
		// TODO Auto-generated method stub
		DoubleMatrix[] ldu = decompose(new DoubleMatrix(a));
		DoubleMatrix gsIteration = MatrixOperations.inverse(ldu[1].add(ldu[0])).mmul(ldu[2]).neg();
		double r = Eigen.eigenvalues(gsIteration).normmax();

		System.err.printf(Locale.ENGLISH, "%f < 1 [%b]%n", r, (r < 1));

		return r < 1;
	}

}
