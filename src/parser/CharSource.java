package parser;

public interface CharSource {
    boolean hasNext();
    char next();
    int getPos();
    void assignPos(int pos);
}
