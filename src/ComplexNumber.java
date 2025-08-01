public class ComplexNumber{

    private double real;
    private double imaginary;
    
    public ComplexNumber(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal(){
        return this.real;
    }

    public double getImaginary(){
        return this.imaginary;
    }

    public void setReal(double real){
        this.real = real;
    }

    public void setImaginary(double imaginary){
        this.imaginary = imaginary;
    }

    public ComplexNumber add(ComplexNumber c2){
        double real = this.real + c2.getReal();
        double imaginary = this.getImaginary() + c2.getImaginary();
        
        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber subtract(ComplexNumber c2){
        double real = this.getReal() - c2.getReal();
        double imaginary = this.getImaginary() - c2.getImaginary();

        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber multiply(ComplexNumber c2){
        double real = this.getReal()*c2.getReal() - this.getImaginary()*c2.getImaginary();
        double imaginary = this.getReal()*c2.getImaginary() + this.getImaginary()*c2.getReal();

        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber divide(ComplexNumber c2){
        double denominator = c2.getImaginary()*c2.getImaginary() + c2.getReal()*c2.getReal();
        if (denominator == 0) {
            throw new ArithmeticException("Cannot divide by zero-complex number.");
        }
        double real_numerator = this.getReal()*c2.getReal() + this.getImaginary()*c2.getImaginary();
        double imaginary_numerator = this.getImaginary()*c2.getReal() - this.getReal()*c2.getImaginary();

        return new ComplexNumber(real_numerator/denominator, imaginary_numerator/denominator);
    }

    @Override
    public String toString() {
        if (imaginary >= 0)
            return real + " + " + imaginary + "i";
        else
            return real + " - " + (-imaginary) + "i";
    }
}