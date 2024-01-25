package parser;

public class Add extends BinaryOperation {
    protected Add(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    public CantorNormalForm makeAction(CantorNormalForm first, CantorNormalForm second) {
        return first.add(second);
    }
}
