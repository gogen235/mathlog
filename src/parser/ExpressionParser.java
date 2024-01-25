package parser;

import java.util.List;

public class ExpressionParser {
    public Expression parse(final String source) {
        return parse(new StringSource(source));
    }

    public static Expression parse(final CharSource source) {
        return new Parser(source).parseNPriority(1);
    }

    private static class Parser extends BaseParser {
        private static final int maxPriority = 4;

        protected Parser(CharSource source) {
            super(source);
        }

        private Expression parseMaxPriority() {
            Expression result;
            if (between('0', '9')) {
                result = parseConst();
            } else if (test('w')) {
                result = parseW();
            } else {
                take('(');
                result = parseNPriority(1);
                take(')');
            }
            return result;
        }

        private Expression parsePow() {
            Expression result = parseMaxPriority();
            while (true) {
                if (take("^")) {
                    result = getExpression("^", result, parsePow());
                    continue;
                }
                return result;
            }
        }

        private Expression parseNPriority(int n) {
            if (n == maxPriority) {
                return parseMaxPriority();
            } else if (n == maxPriority - 1) {
                return parsePow();
            } else {
                Expression result = parseNPriority(n + 1);
                loop:
                while (true) {
                    for (String operation : getOperation(n)) {
                        if (take(operation)) {
                            result = getExpression(operation, result, parseNPriority(n + 1));
                            continue loop;
                        }
                    }
                    return result;
                }
            }
        }

        private Expression parseW() {
            take();
            return new W();
        }

        private Expression parseConst() {
            StringBuilder sb = new StringBuilder();
            while (between('0', '9')) {
                sb.append(take());
            }
            return new Const(Integer.parseInt(sb.toString()));
        }

        private Expression getExpression(String sign, Expression first, Expression second) {
            if (sign.equals("+")) {
                return new Add(first, second);
            } else if (sign.equals("*")) {
                return new Multiply(first, second);
            } else {
                return new Pow(first, second);
            }
        }

        private List<String> getOperation(int priority) {
            if (priority == 1) {
                return List.of("+");
            } else {
                return List.of("*");
            }
        }
    }

}
