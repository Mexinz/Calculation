package po;

import java.util.List;
import java.util.Objects;

/**
 * @Author: Mexinz
 * @Date: 2019/10/10
 */
public class Formula {
    private List<String> symbols;
    private List<Fraction> numbers;

    public Formula(List<String> symbols, List<Fraction> numbers) {
        this.symbols = symbols;
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        StringBuilder formula = new StringBuilder();
        formula.append(numbers.get(0)).append(" ");
        for (int i = 0; i < symbols.size(); i++) {
            formula.append(symbols.get(i)).append(" ");
            if (i == symbols.size() -1) {
                formula.append(numbers.get(i+1));
            } else {
                formula.append(numbers.get(i+1)).append(" ");
            }
        }
        return formula.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Formula formula = (Formula) o;
        return Objects.equals(symbols, formula.symbols) &&
                Objects.equals(numbers, formula.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbols, numbers);
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public List<Fraction> getNumbers() {
        return numbers;
    }
}
