package po;

import java.util.Objects;

import static util.FractionUtil.getCommonDenominator;
import static util.FractionUtil.getCommonDivisor;

/**
 * @Author: Mexinz
 * @Date: 2019/10/10
 */
public class Fraction {
    /**
     * 分子
     */
    private int numerator;
    /**
     * 分母
     */
    private int denominator;

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }


    public Fraction add(Fraction num) {
        int commonDenominator = getCommonDenominator(denominator,num.denominator);
        int ansNumerator  = commonDenominator / denominator * numerator +
                commonDenominator / num.denominator * num.numerator;
        return reduceFraction(new Fraction(ansNumerator,commonDenominator));
    }

    public Fraction subtract(Fraction num) {
        int commonDenominator = getCommonDenominator(denominator,num.denominator);
        int ansNumerator = commonDenominator / denominator * numerator -
                commonDenominator / num.denominator * num.numerator;
        return reduceFraction(new Fraction(ansNumerator,commonDenominator));
    }

    public Fraction multiply(Fraction num) {
        int ansDenominator = denominator * num.denominator;
        int ansNumerator = numerator * num.numerator;
        return reduceFraction(new Fraction(ansNumerator,ansDenominator));
    }

    public Fraction divide(Fraction num) {
        Fraction multiplier = new Fraction(num.denominator,num.numerator);
        return multiply(multiplier);
    }

    /**
     * 将字符串转为可以计算的Fraction
     * @param num 数字字符串
     * @return 转化的Fraction
     */
    public static Fraction stringToFraction(String num) {
        if (num.contains("-")) {
            return new Fraction(-Integer.MAX_VALUE, 1);
        } else if (num.contains("/")) {
            return new Fraction(Integer.parseInt(num.substring(0, num.indexOf("/"))),
                    Integer.parseInt(num.substring(num.indexOf("/") + 1)));
        } else {
            return new Fraction(Integer.parseInt(num),1);
        }
    }


    /**
     * 约分
     * @param num 需要约分的分数
     * @return 最简分数
     */
    public static Fraction reduceFraction(Fraction num) {
        int divisor = getCommonDivisor(num.denominator,num.numerator);
        num.denominator = num.denominator / divisor;
        num.numerator = num.numerator / divisor;
        return num;
    }

    /**
     * 比较大小
     * @param num 比较数
     * @return true为本数比num大
     */
    public boolean compare(Fraction num) {
        int commonDenominator = getCommonDenominator(denominator,num.denominator);
        int newNumerator = commonDenominator / denominator * numerator;
        int newNumerator2 = commonDenominator / num.denominator * num.numerator;
        return newNumerator > newNumerator2;
    }

//    /**
//     * 通分
//     * @param num 与本数通分的另一个分数
//     */
//    private int toCommonDenominator(Fraction num) {
//        int commonDenominator = denominator;
//        if (denominator != num.denominator) {
//            commonDenominator = getCommonDenominator(denominator,num.denominator);
//            numerator = numerator * (commonDenominator/denominator);
//            num.numerator = num.numerator * (commonDenominator/num.denominator);
//        }
//        denominator = commonDenominator;
//        num.denominator = commonDenominator;
//    }



    @Override
    public String toString() {
        if (denominator == 1) {
            return Integer.toString(numerator);
        } else if (numerator == denominator) {
            return "1";
        } /*else if (numerator > denominator) {
            return numerator / denominator + "'" + numerator % denominator + "/" + denominator;
        }*/
        return  numerator + "/" + denominator;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return numerator == fraction.numerator &&
                denominator == fraction.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }
}
