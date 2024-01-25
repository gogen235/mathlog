package parser;

import parser.CantorNormalForm;
import parser.Expression;

public class W implements Expression {
    @Override
    public CantorNormalForm evaluate() {
        return new CantorNormalForm();
    }
}
