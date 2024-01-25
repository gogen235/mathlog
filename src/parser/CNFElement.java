package parser;

public class CNFElement {
    int coefficient;
    CantorNormalForm degree;
    boolean isNum;
    CNFElement(int coefficient) {
        this.coefficient = coefficient;
        this.isNum = true;
    }
    CNFElement(int coefficient, CantorNormalForm degree) {
        this.isNum = degree == null;
        this.coefficient = coefficient;
        this.degree = degree;
    }
}
