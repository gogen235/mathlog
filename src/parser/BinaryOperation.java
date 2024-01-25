package parser;

public abstract class BinaryOperation implements Expression {
    private final Expression first;
    private final Expression second;

    protected BinaryOperation(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public CantorNormalForm evaluate() {
        return makeAction(first.evaluate(), second.evaluate());
    }
    public abstract CantorNormalForm makeAction(CantorNormalForm first, CantorNormalForm second);
}
