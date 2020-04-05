import java.util.Iterator;

/**
 * Implementation of a matrix using only 1D array
 */
public class Matrix {
	
	private final int rows, cols;
	private final double[] values;
	
	/**
	 * Initialize the matrix with given values
	 * @param values values of the matrix.
	 * 	the value of M[i,j] should be in values[i][j]
	 */
	public Matrix(double[][] values) {
		/* init */
		this.rows = values.length;
		this.cols = values[0].length;
		
		this.values = new double[this.rows*this.cols];
		
		/* insert values into matrix */
		int currentCell = 0;
		
		for (double[] row : values) {
			for (double cell : row) {
				this.values[currentCell] = cell;
				currentCell++;
			}
		}
	}
	
	/**
	 * Initialize the matrix with given values
	 * @param values all row values concatenated starting from row 0 to row n-1
	 * @param rows number of rows
	 */
	public Matrix(double[] values, int rows) {
		this.values = new double[values.length];
		this.rows = rows;
		this.cols = values.length / rows;
		
		System.arraycopy(values, 0, this.values, 0, values.length);
	}
	
	/**
	 * Get values of flat array
	 * @return the matrix values
	 */
	public double[] values() {
		return this.values;
	}
	
	/**
	 * @return number of rows 
	 */
	public int rows() {
		return this.rows;
	}
	
	/**
	 * @return number of columns
	 */
	public int cols() {
		return this.cols;
	}
	
	/**
	 * @return total number of cells
	 */
	public int cellsCount() {
		return this.values.length;
	}
	
	public double cell(int i, int j) {
		return this.values[i*this.cols() + j];
	}
	
	/**
	 * Get an iterator over a given row in the matrix
	 * @param i the row index
	 * @return a double iterator
	 */
	public Iterator<Double> row(int i) {
		return new MatrixIterator(i*this.rows(), 1, this.cols());
	}
	
	/**
	 * Get an iterator over a given column in the matrix
	 * @param j the column index
	 * @return a double iterator
	 */
	public Iterator<Double> col(int j) {
		return new MatrixIterator(j, this.cols(), this.rows());
	}
	
	/**
	 * Get an iterator over all the values of the matrix
	 * The values order is by:
	 * M[0,0], M[0, 1] ... M[1, 0], M[1, 1] ... M[n-1, n-1]
	 * @return a double iterator
	 */
	public Iterator<Double> cells() {
		return new MatrixIterator(0, 1, cellsCount());
	}
	
	/**
	 * An iterator over this matrix.
	 */
	private class MatrixIterator implements Iterator<Double> {
		
		private int current;
		private int jump;
		private int maxIndex;
		
		/**
		 * Creates a MatrixIterator instance
		 * @param start the start point in the values array to read from
		 * @param jump the number of elements in the values array that separate
		 * 	between two subsequent elements of this iterator
		 * @param numElements the total number of elements that should be read from values[]
		 */
		public MatrixIterator(int start, int jump, int numElements) {
			this.current = start;
			this.jump = jump;
			this.maxIndex = start + jump*(numElements - 1);
		}

		@Override
		public boolean hasNext() {
			return current <= maxIndex;
		}

		@Override
		public Double next() {
			double next = values[current];
			current += jump;
			return next;
		}
	}

}
