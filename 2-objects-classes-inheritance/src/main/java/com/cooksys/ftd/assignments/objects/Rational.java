package com.cooksys.ftd.assignments.objects;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Rational implements IRational {
    /**
     * Constructor for rational values of the type:
     * <p>
     * `numerator / denominator`
     * <p>
     * No simplification of the numerator/denominator pair should occur in this method.
     *
     * @param numerator   the numerator of the rational value
     * @param denominator the denominator of the rational value
     * @throws IllegalArgumentException if the given denominator is 0
     */
	private int numerator;
	private int denominator;
	
    public Rational(int numerator, int denominator) throws IllegalArgumentException {
    	if (denominator == 0) {
    		throw new IllegalArgumentException("Denominator is 0, Cannot divide by zero!");
    	}
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * @return the numerator of this rational number
     */
    @Override
    public int getNumerator() {
        return numerator;
    }

    /**
     * @return the denominator of this rational number
     */
    @Override
    public int getDenominator() {
        return denominator;
    }

    /**
     * Specializable constructor to take advantage of shared code between Rational and SimplifiedRational
     * <p>
     * Essentially, this method allows us to implement most of IRational methods directly in the interface while
     * preserving the underlying type
     *
     * @param numerator the numerator of the rational value to construct
     * @param denominator the denominator of the rational value to construct
     * @return the constructed rational value
     * @throws IllegalArgumentException if the given denominator is 0
     */
    @Override
    public Rational construct(int numerator, int denominator) throws IllegalArgumentException {
    	if (denominator == 0) {
    		throw new IllegalArgumentException("The deniminator is 0, Cannot divide by 0!");
    	}
        Rational rational = new Rational(numerator, denominator);
        return rational;
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
    	
    	if (!(obj instanceof Rational)) {
    		return false;
    	}
        Rational o = (Rational) obj;
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
