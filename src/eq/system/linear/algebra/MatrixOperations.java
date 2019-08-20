package eq.system.linear.algebra;

import org.jblas.DoubleMatrix;
import org.jblas.Solve;

public abstract class MatrixOperations {

	public static DoubleMatrix inverse(DoubleMatrix matrix) {
		return matrix.isSquare() ? Solve.solve(matrix.dup(), DoubleMatrix.eye(matrix.rows)) : null;
	}

}
