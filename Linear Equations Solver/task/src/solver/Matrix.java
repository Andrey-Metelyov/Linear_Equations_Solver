package solver;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Matrix implements Serializable {
    private final double[][] data;
    private final int rows;
    private final int columns;

    Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        data = new double[rows][columns];
    }
    Matrix(double[][] data) {
        this.data = data;
        this.rows = data.length;
        this.columns = data[0].length;
    }

    public Matrix(Matrix other) {
        rows = other.rows;
        columns = other.columns;
        data = new double[rows][];
        for (int i = 0; i < rows; i++) {
            data[i] = Arrays.copyOf(other.data[i], other.data[i].length);
        }
    }

    static public Matrix getMatrix(int rows, int columns, int element) {
        assert rows > 0 && columns > 0;
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < result.getRows(); i++) {
            Arrays.fill(result.data[i], element);
        }
        return result;
    }

    public static void fillRandom(Matrix matrix) {
        Random random = new Random();
        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.columns; j++) {
                matrix.data[i][j] = random.nextGaussian();
            }
        }
    }

    public static Matrix getMatrix(Scanner scanner) {
        System.out.print("Enter the size of matrix: ");
        int[] dim = Stream.of(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        System.out.println("Enter matrix:");
        Matrix matrix = new Matrix(dim[0], dim[1]);
        for (int i = 0; i < dim[0]; i++) {
            double[] row = Stream.of(scanner.nextLine().split(" "))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            for (int j = 0; j < dim[1]; j++) {
                matrix.setAt(i, j, row[j]);
            }
        }
        return matrix;

    }

    public double getAt(int row, int column) {
        assert row < data.length && column < data[row].length;
        return data[row][column];
    }

    public void setAt(int row, int column, double value) {
        assert row < data.length && column < data[row].length;
        data[row][column] = value;
    }

    public void setRow(int rowSet, Matrix other, int rowGet) {
        assert this.data[rowSet].length == other.data[rowGet].length;
        this.data[rowSet] = Arrays.copyOf(other.data[rowGet], other.data[rowGet].length);
    }

    public Matrix addRow(double value) {
        double[] row = new double[columns];
        Arrays.fill(row, value);
        return addRow(row);
    }

    public Matrix addRow(double[] row) {
        return addRow(row, getRows());
    }

    public Matrix addRow(double[] row, int position) {
        assert position <= rows && row.length == columns;
        Matrix result = new Matrix(getRows() + 1, getColumns());
        for (int i = 0; i < position; i++) {
            result.data[i] = Arrays.copyOf(data[i], data[i].length);
        }
        result.data[position] = Arrays.copyOf(row, row.length);
        for (int i = position; i < getRows(); i++) {
            result.data[i + 1] = Arrays.copyOf(data[i], data[i].length);
        }
        return result;
    }

    public Matrix getRow(int row) {
        assert row > 0 && row < rows;
        Matrix result = new Matrix(1, getColumns());
        for (int i = 0; i < getColumns(); i++) {
            result.setAt(0, i, getAt(row, i));
        }
        return result;
    }

    public Matrix getColumn(int column) {
        assert column < columns;
        Matrix result = new Matrix(getRows(), 1);
        for (int i = 0; i < getRows(); i++) {
            result.setAt(i, 0, getAt(i, column));
        }
        return result;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Matrix multiply(Matrix other) {
        assert columns == other.rows;
        int rows = this.getRows();
        int columns = other.getColumns();
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double sum = 0.0;
                for (int r = 0; r < this.getColumns(); r++) {
                    sum += this.getAt(i, r) * other.getAt(r, j);
                }
                result.setAt(i, j, sum);
            }
        }
        return result;
    }

    public Matrix multiply(double number) {
        Matrix result = new Matrix(getRows(), getColumns());
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                result.setAt(i, j, getAt(i, j) * number);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                sb.append(String.format("%d ", (int)getAt(i, j)));
//                sb.append(String.format("%.3f ", getAt(i, j)));
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public Matrix transpose() {
        double[][] result = new double[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[j][i] = data[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix func(UnaryOperator<Double> f) {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.data[i][j] = f.apply(data[i][j]);
            }
        }
        return result;
    }

    public Matrix subtract(Matrix other) {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.data[i][j] = data[i][j] - other.data[i][j];
            }
        }
        return result;
    }

    public Matrix add(Matrix other) {
//        assert this.rows == other.rows && this.columns == other.columns;
        if (this.rows != other.rows || this.columns != other.columns) {
            throw new IllegalArgumentException("ERROR");
        }
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.data[i][j] = data[i][j] + other.data[i][j];
            }
        }
        return result;
    }

    public Matrix dot(Matrix other) {
        assert rows == other.rows && columns == other.columns;
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.data[i][j] = data[i][j] * other.data[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        double[][] data = {
//                {1,-2,4,3208,-8379},
//                {2,4,-781,-5,-9},
//                {0,0,1,-54,0},
//                {0,0,0,3,-879},
//                {0,0,0,0,3}
//        };
        double[][] data = {
                {2,-1,0},
                {0,1,2},
                {1,1,0}
        };
        double[][] ones = {{1},{1},{1},{1}};
        double[][] data2 = {
                {1,2,3,4},
                {5,6,7,8},
                {9,0,1,2},
                {3,4,5,6}
        };
        Matrix m = new Matrix(data);
        System.out.println(m.inv().toStringDouble());
        System.out.println(m.toString());
        System.out.println(m.addColumn(999.0, 0));
        Matrix o = new Matrix(ones);
        System.out.println(m.multiply(o).toString());
        System.out.println(m.addRow(new double[]{1, 2, 3, 4}));
        Matrix p = getMatrix(10, 20, 1);
        System.out.println(p);
        p = getMatrix(1, 5, 1);
        System.out.println(p);
        p = getMatrix(5, 1, 1);
        System.out.println(p);
        /*Matrix n = new Matrix(data2);
        System.out.println(n.toString());
        System.out.println(m.multiply(n).toString());
        System.out.println(m.multiply(2));
        System.out.println(n.multiply(0));*/
    }

    public Matrix minus(Matrix other) {
        Matrix result = new Matrix(rows, columns);
        if (other.columns == 1) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    result.data[i][j] = data[i][j] - other.data[i][0];
                }
            }
        } else if (other.rows == 1) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    result.data[i][j] = data[i][j] - other.data[0][j];
                }
            }
        } else if (rows == other.rows && columns == other.columns) {
            result = subtract(other);
        } else {
            System.out.println("error in minus()");
        }
        return result;
    }

    public Matrix addColumn(double value, int position) {
        Matrix result = new Matrix(rows, columns + 1);
        for (int i = 0; i < rows; i++) {
            if (position >= 0) {
                System.arraycopy(data[i], 0, result.data[i], 0, position);
            }
            result.data[i][position] = value;
            if (columns - position >= 0) {
                System.arraycopy(data[i], position, result.data[i], position + 1, columns - position);
            }
        }
        return result;
    }

    public Matrix transposeSide() {
        double[][] result = new double[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[columns - j - 1][rows - i - 1] = data[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix transposeVertical() {
        double[][] result = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = data[i][columns - j - 1];
            }
        }
        return new Matrix(result);
    }

    public Matrix transposeHorizontal() {
        double[][] result = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            if (columns >= 0) {
                System.arraycopy(data[rows - i - 1], 0, result[i], 0, columns);
            }
        }
        return new Matrix(result);
    }

    public String toStringDouble() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
//                sb.append(String.format("%d ", (int)getAt(i, j)));
                sb.append(String.format("%.2f ", getAt(i, j)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public double det() {
        assert rows == columns;
        if (rows == 1) {
            return data[0][0];
        }
        if (rows == 2) {
            return data[0][0] * data[1][1] - data[0][1] * data[1][0];
        }
        double result = 0.0;
        for (int i = 0; i < columns; i++) {
            Matrix minor = getMinor(0, i);
            result += ((i % 2 == 0 ? 1.0 : -1.0) * data[0][i] * minor.det());
        }
        return result;
    }

    private Matrix getMinor(int row, int column) {
        double[][] result = new double[rows - 1][columns - 1];
        int ii = 0;
        int jj;
        for (int i = 0; i < rows; i++) {
            if (i == row) {
                continue;
            }
            jj = 0;
            for (int j = 0; j < columns; j++) {
                if (j == column) {
                    continue;
                }
                result[ii][jj++] = data[i][j];
            }
            ii++;
        }
        return new Matrix(result);
    }

    public Matrix inv() {
        assert rows == columns;
        double[][] cofactors = new double[rows][columns];
        double det = det();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Matrix minor = getMinor(i, j);
                cofactors[i][j] = (((i + j) % 2 == 0 ? 1.0 : -1.0) * minor.det());
            }
        }
        return new Matrix(cofactors).transpose().multiply(1.0 / det);
    }

    public void setRow(int row, double[] line) {
        System.arraycopy(line, 0, data[row], 0, line.length);
    }

    public double[][] toArray() {
        return data.clone();
    }
}
