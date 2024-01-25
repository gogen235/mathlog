package parser;

public class Const implements Expression {
    int value;
    public Const(int value) {
        this.value = value;
    }
    @Override
    public CantorNormalForm evaluate() {
        return new CantorNormalForm(value);
    }
}
