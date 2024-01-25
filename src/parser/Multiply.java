package parser;

import parser.BinaryOperation;
import parser.CantorNormalForm;
import parser.Expression;

public class Multiply extends BinaryOperation {
    protected Multiply(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    public CantorNormalForm makeAction(CantorNormalForm first, CantorNormalForm second) {
        return first.mul(second);
    }
}
