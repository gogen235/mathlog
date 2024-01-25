package parser;

import parser.BinaryOperation;
import parser.CantorNormalForm;
import parser.Expression;

public class Pow extends BinaryOperation {
    protected Pow(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    public CantorNormalForm makeAction(CantorNormalForm first, CantorNormalForm second) {
        return first.pow(second);
    }
}
