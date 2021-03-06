package com.cooksys.ftd.assignments.objects;

public class SimplifiedRational implements IRational {
	private int numerator;
	private int denominator;
	
    /**
     * Determines the greatest common denominator for the given values
     *
     * @param a the first value to consider
     * @param b the second value to consider
     * @return the greatest common denominator, or shared factor, of `a` and `b`
     * @throws IllegalArgumentException if a < 0 or b <= 0
     */
    public static int gcd(int a, int b) throws IllegalArgumentException {
        if (a < 0 || b < 0) {
        	 throw new IllegalArgumentException("Invalid Parameters were passed to calculate the GCF.");
        }
        if (b == 0) {
        	return a;
        }
        return gcd(b, a%b);
    }
    
    public static void main(String[] args) {
    	int[] result = simplify(-5,10);
    	System.out.println(result[0] + "/" + result[1]);
    }
    

    /**
     * Simplifies the numerator and denominator of a rational value.
     * <p>
     * For example:
     * `simplify(10, 100) = [1, 10]`
     * or:
     * `simplify(0, 10) = [0, 1]`
     *
     * @param numerator   the numerator of the rational value to simplify
     * @param denominator the denominator of the rational value to simplify
     * @return a two element array representation of the simplified numerator and denominator
     * @throws IllegalArgumentException if the given denominator is 0
     */
    public static int[] simplify(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0) {
        	throw new IllegalArgumentException("Denominator cannot be zero! Try again.");
        }
        int factor = gcd(Math.abs(numerator), Math.abs(denominator));
        int newNumerator = numerator / factor;
        int newDenominator = denominator / factor;
        int[] result = new int[2];
        result[0] = newNumerator;
        result[1] = newDenominator;
        return result;        
    }

    /**
     * Constructor for rational values of the type:
     * <p>
     * `numerator / denominator`
     * <p>
     * Simplification of numerator/denominator pair should occur in this method.
     * If the numerator is zero, no further simplification can be performed
     *
     * @param numerator   the numerator of the rational value
     * @param denominator the denominator of the rational value
     * @throws IllegalArgumentException if the given denominator is 0
     */
    public SimplifiedRational(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0) {
        	throw new IllegalArgumentException("Denominator cannot be zero, Try again!");
        	
        }
        int[] result = simplify(numerator, denominator);
        this.numerator = result[0];
        this.denominator = result[1];
    }

    /**
     * @return the numerator of this rational number
     */
    @Override
    public int getNumerator() {
        return this.numerator;
    }

    /**
     * @return the denominator of this rational number
     */
    @Override
    public int getDenominator() {
        return this.denominator;
    }

    /**
     * Specializable constructor to take advantage of shared code between Rational and SimplifiedRational
     * <p>
     * Essentially, this method allows us to implement most of IRational methods directly in the interface while
     * preserving the underlying type
     *
     * @param numerator   the numerator of the rational value to construct
     * @param denominator the denominator of the rational value to construct
     * @return the constructed rational value (specifically, a SimplifiedRational value)
     * @throws IllegalArgumentException if the given denominator is 0
     */
    @Override
    public SimplifiedRational construct(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0) {
        	throw new IllegalArgumentException("Denominator cannot be zero, Try Again!");
        }
        int[] simplied = simplify(numerator, denominator);
        SimplifiedRational result = new SimplifiedRational(simplied[0], simplied[1]);
        return result;
    }

    /**
     * @param obj the object to check this against for equality
     * @return true if the given obj is a rational value and its
     * numerator and denominator are equal to this rational value's numerator and denominator,
     * false otherwise
     */
    @Override
    public boolean equals(Object obj) {
    	if (obj == this) {
    		return true;
    	}
    	
    	if (!(obj instanceof SimplifiedRational)) {
    		return false;
    	}
        SimplifiedRational o = (SimplifiedRational) obj;
        return (this.getNumerator() == o.getNumerator() && this.getDenominator() == o.getDenominator());
    }

    /**
     * If this is positive, the string should be of the form `numerator/denominator`
     * <p>
     * If this is negative, the string should be of the form `-numerator/denominator`
     *
     * @return a string representation of this rational value
     */
    @Override
    public String toString() {
    	if (this.getNumerator() < 0 && this.getDenominator() < 0) {
        	return Math.abs(this.getNumerator()) + "/" + Math.abs(this.getDenominator());
        } else if (this.getNumerator() < 0 && this.getDenominator() > 0) {
        	return "-" + Math.abs(this.getNumerator()) + "/" + Math.abs(this.getDenominator());
        } else if (this.getNumerator() > 0 && this.getDenominator() < 0) {
        	return "-" + Math.abs(this.getNumerator()) + "/" + Math.abs(this.getDenominator());
        }
        return this.getNumerator() + "/" + this.getDenominator();
    }
}
