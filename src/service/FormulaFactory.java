package service;

import po.Formula;
import po.Fraction;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @Author: Mexinz
 * @Date: 2019/10/10
 */
public class FormulaFactory implements Callable<Formula> {

    private int max;

    public FormulaFactory(int max) {
        this.max = max;
    }

    @Override
    public Formula call() {
        int random = (int) (Integer.MAX_VALUE * Math.random());
        int figureNum = (random % 3) + 2;
        List<String> symbolList = new ArrayList<>();
        List<Fraction> fractionList = new ArrayList<>();
        fractionList.add(getRandomFraction(max));
        for (int i = 1; i < figureNum ; i++) {
            random = (int) (Integer.MAX_VALUE * Math.random());
            Fraction b = getRandomFraction(max);
            String symbol = Constants.symbols[random % 4];
            while ("-".equals(symbol)) {
                if (!fractionList.get(i-1).compare(b)) {
                    random = (int) (Integer.MAX_VALUE * Math.random());
                    symbol = Constants.symbols[random % 4];
                } else {
                    break;
                }
            }
            symbolList.add(symbol);
            fractionList.add(b);
        }
        return new Formula(symbolList,fractionList);
    }

    /**
     * 生成一个随机的数
     * @param max 限值
     * @return 随机的分数
     */
    public Fraction getRandomFraction(Integer max) {
        int numerator = (int) (Math.random() * max);
        while (numerator == 0) {
            numerator = (int) (Math.random() * max);
        }
        if (Math.random() > 0.5) {
            int denominator = (int) (Math.random() * max);
            while (denominator == 0) {
                denominator = (int) (Math.random() * max);
            }
            return new Fraction(numerator,denominator);
        } else {
            return new Fraction(numerator,1);
        }
    }
}
