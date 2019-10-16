package po;

import util.Constants;

import java.util.Objects;

/**
 * @Author: Mexinz
 * @Date: 2019/10/15
 */
public class Process {
    private Fraction a;
    private Fraction b;
    private String symbol;
    private Fraction answer;

    public Process(Fraction a, Fraction b, String symbol, Fraction answer) {
        this.a = a;
        this.b = b;
        this.symbol = symbol;
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return ((a.equals(process.a) && b.equals(process.b)) ||
                ((a.equals(process.b) && b.equals(process.a)))) &&
                symbol.equals(process.symbol) &&
                answer.equals(process.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, symbol, answer);
    }

    public Fraction getAnswer() {
        return answer;
    }
}
