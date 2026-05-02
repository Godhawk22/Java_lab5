package lab5;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



/**
 *
 * @author Oleg
 */
public class RecIntegral extends Thread implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private double from, to, step;
    private Double result; // может быть null

    // ===================== Проверка диапазона =====================
    private boolean isValid(double value) {
        return value >= 0.000001 && value <= 1000000;
    }

    // ===================== Конструктор =====================
    public RecIntegral(double from, double to, double step) throws InvalidRangeException {

        if (!isValid(from) || !isValid(to) || !isValid(step)) {
            throw new InvalidRangeException(
                "Значения должны быть в диапазоне от 0.000001 до 1000000"
            );
        }

        this.from = from;
        this.to = to;
        this.step = step;
    }

    // ===================== Парсинг строки =====================
    public static double parseAndValidate(String value) throws InvalidRangeException {

        if (value == null || value.isEmpty()) {
            throw new InvalidRangeException("Пустое значение");
        }

        if (!value.matches("[-+]?[0-9]+([.,][0-9]+)?")) {
            throw new InvalidRangeException("Допустимы только числовые значения");
        }

        value = value.replace(',', '.');

        double parsedValue = Double.parseDouble(value);

        if (parsedValue < 0.000001 || parsedValue > 1000000) {
            throw new InvalidRangeException(
                "Значения должны быть в диапазоне от 0.000001 до 1000000"
            );
        }

        return parsedValue;
    }

    // ===================== Поток вычисления =====================
    @Override
    public void run() {

        System.out.println(
            Thread.currentThread().getName()
            + " start: [" + from + " ; " + to + "]"
        );

        double sum = 0.0;

        for (double x = from; x < to; x += step) {

            double xNext = (x + step < to) ? (x + step) : to;

            double f1 = Math.tan(x);
            double f2 = Math.tan(xNext);

            sum += (f1 + f2) / 2 * (xNext - x);
        }

        result = sum;

        System.out.println(
            Thread.currentThread().getName()
            + " end: [" + from + " ; " + to + "]"
            + " Result = " + result
        );
    }

    // ===================== Getters =====================
    public double getFrom() { return from; }
    public double getTo() { return to; }
    public double getStep() { return step; }
    public Double getResult() { return result; }

    // ===================== Setters =====================
    public void setFrom(double from) { this.from = from; }
    public void setTo(double to) { this.to = to; }
    public void setStep(double step) { this.step = step; }
    public void setResult(Double result) { this.result = result; }

    // ===================== Вложенный калькулятор =====================
    public static class IntegralCalculator {

        public static double integrateTan(double a, double b, double h) {
            double sum = 0.0;

            for (double x = a; x < b; x += h) {
                double xNext = (x + h < b) ? (x + h) : b;

                double f1 = Math.tan(x);
                double f2 = Math.tan(xNext);

                sum += (f1 + f2) / 2 * (xNext - x);
            }

            return sum;
        }
    }

    // ===================== Исключение =====================
    public static class InvalidRangeException extends Exception {

        public InvalidRangeException(String message) {
            super(message);
        }
    }
}
