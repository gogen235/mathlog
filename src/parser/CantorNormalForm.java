package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

public class CantorNormalForm {
    ArrayList<CNFElement> elements = new ArrayList<>();
    CantorNormalForm() {
        elements.add(new CNFElement(1, new CantorNormalForm(1)));
    }
    CantorNormalForm(int value) {
        elements.add(new CNFElement(value));
    }
    CantorNormalForm(CantorNormalForm other) {
        this.elements = other.elements;
    }
    CantorNormalForm(ArrayList<CNFElement> elements) {
        this.elements = elements;
    }
    public CantorNormalForm add(CantorNormalForm other) {
        if (isNum() && other.isNum()) return new CantorNormalForm(getValue() + other.getValue());
        if (isNum()) return new CantorNormalForm(other);
        if (other.isNum()) {
            ArrayList<CNFElement> newElements = new ArrayList<>(elements);
            int last = size() - 1;
            if (!elements.get(last).isNum) {
                newElements.add(new CNFElement(other.getValue()));
            } else {
                CNFElement element = newElements.get(last);
                newElements.set(last, new CNFElement(element.coefficient + other.getValue(), element.degree));
            }
            return new CantorNormalForm(newElements);
        }
        while (!elements.isEmpty() && getDegree(size() - 1).less(other.getDegree(0))) {
            elements.remove(size() - 1);
        }
        ArrayList<CNFElement> newElements = new ArrayList<>(elements);
        int last = size() - 1;
        if (!elements.isEmpty() && getDegree(size() - 1).equals(other.getDegree(0))) {
            CNFElement element = newElements.get(last);
            newElements.set(last, new CNFElement(element.coefficient + other.getCoefficient(0), element.degree));
            for (int i = 1; i < other.size(); i++) {
                newElements.add(other.elements.get(i));
            }
        } else {
            newElements.addAll(other.elements);
        }
        return new CantorNormalForm(newElements);
    }

    private CantorNormalForm mul(CNFElement other) {
        ArrayList<CNFElement> newElements = new ArrayList<>();
        if (isNum() && other.isNum) {
            newElements.add(new CNFElement(getValue() * other.coefficient));
        } else if (other.isNum) {
            if (other.coefficient != 0) {
                newElements = elements;
                newElements.set(0, new CNFElement(getCoefficient(0) * other.coefficient, getDegree(0)));
            } else {
                newElements.add(new CNFElement(0));
            }
        } else if (isNum()) {
            if (getCoefficient(0) != 0) {
                newElements.add(other);
            } else {
                newElements.add(new CNFElement(0));
            }
        } else {
            newElements.add(new CNFElement(other.coefficient, getDegree(0).add(other.degree)));
        }
        return new CantorNormalForm(newElements);
    }
    public CantorNormalForm mul(CantorNormalForm other) {
        if (isNum() && other.isNum()) return new CantorNormalForm(getValue() * other.getValue());
//        if (isNum) return new CantorNormalForm(other);
        if (other.isNum()) return mul(new CNFElement(other.getValue()));
        CantorNormalForm result = new CantorNormalForm(0);
        for (int i = 0; i < other.size(); i++) {
            result = result.add(mul(other.elements.get(i)));
        }
        return result;
    }

    public CantorNormalForm pow(CNFElement other) {
        if (other.isNum) {
            CantorNormalForm result = new CantorNormalForm(1);
            for (int i = 0; i < other.coefficient; i++) {
                result = result.mul(this);
            }
            return result;
        }
        ArrayList<CNFElement> newElements = new ArrayList<>();
        newElements.add(new CNFElement(1, getDegree(0).mul(other)));
        return new CantorNormalForm(newElements);
    }
    public CantorNormalForm pow(CantorNormalForm other) {
        CantorNormalForm result = new CantorNormalForm(1);
        if (isNum() && other.isNum()) {
            return new CantorNormalForm((int) Math.pow(getValue(), other.getValue()));
        }
        if (other.isNum()) {
            return pow(new CNFElement(other.getValue()));
        }
        if (isNum()) {
            if (getValue() == 0) {
                return new CantorNormalForm(0);
            }
            if (getValue() == 1) {
                return new CantorNormalForm(1);
            }
            ArrayList<CNFElement> newElements = new ArrayList<>();
            int flag = 0;
            if (other.elements.get(other.size() - 1).isNum) {
                flag = 1;
            }
            for (int i = 0; i < other.size() - flag; i++) {
                CantorNormalForm degree = other.getDegree(i);
                if (degree.isNum()) {
                    if (degree.getValue() == 1) {
                        degree = null;
                    } else {
                        degree = new CantorNormalForm(degree.getValue() - 1);
                    }
                }
                newElements.add(new CNFElement(other.getCoefficient(i), degree));
            }
            if (flag == 0) {
                return new CantorNormalForm().pow(new CantorNormalForm(newElements)).mul(new CantorNormalForm(1));
            }
            return new CantorNormalForm().pow(new CantorNormalForm(newElements)).mul(pow(other.elements.get(other.size() - 1)));
        }
        for (int i = 0; i < other.size(); i++) {
            result = result.mul(pow(other.elements.get(i)));
        }
        return result;
    }

    public boolean less(CantorNormalForm other) {
        if (isNum() && other.isNum()) return getValue() < other.getValue();
        if (isNum()) return true;
        if (other.isNum()) return false;
        for (int i = 0; i < min(size(), other.size()); i++) {
            if (other.getDegree(i).less(getDegree(i))) {
                return false;
            } else if (getDegree(i).less(other.getDegree(i))) {
                return true;
            } else if (getCoefficient(i) < other.getCoefficient(i)) {
                return true;
            } else if (getCoefficient(i) > other.getCoefficient(i)) {
                return false;
            }
        }
        return size() < other.size();
    }

    public boolean equals(CantorNormalForm obj) {
        return !(less(obj)) && !(obj.less(this));
    }

    private int size() {
        return elements.size();
    }

    private CantorNormalForm getDegree(int i) {
        if (elements.get(i).isNum) {
            return new CantorNormalForm(0);
        }
        return elements.get(i).degree;
    }

    private int getCoefficient(int i) {
        return elements.get(i).coefficient;
    }

    boolean isNum() {
        return elements.get(0).isNum;
    }

    private int getValue() {
        return elements.get(0).coefficient;
    }
}
