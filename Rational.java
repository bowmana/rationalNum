/*Alex Bowman
 * Professor Silvestri
 * HW#3
 * 2/27/20
 */
package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rational extends Object implements Comparable<Rational> {

	private final int num;
	private final int den;

	public final static Rational ZERO = new Rational(0);
	public final static Rational ONE = new Rational(1);

	// ****************************************************************

	public Rational(String string) {

		final String regex = "^(-?\\d+)(\\/?)(\\d*)$";

		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(string);

		if (matcher.find()) {
			this.num = Integer.parseInt(matcher.group(1));

			if (!matcher.group(2).isEmpty()) {
				this.den = Integer.parseInt(matcher.group(3));

			} else {
				this.den = 1;
			}
		} else {
			this.num = 0;
			this.den = 0;
		}

	}

	public int getNumerator() {
		return num;
	}

	public int getDenominator() {
		return den;
	}

	// ****************************************************************

	public Rational() {
		this(0, 1);
	}

	public Rational(int num) {
		this(num, 1);
	}

	public Rational(int[] fract) {
		this(fract[0], fract[1]);
	}

	public Rational(int numerator, int denominator) {
		if (denominator == 0) {
			throw new ArithmeticException("denominator is zero");
		}

		// reduce fraction
		int g = gcf(numerator, denominator);

		if (denominator > 0) {
			this.num = numerator / g;
			this.den = denominator / g;
		} else {
			this.num = -numerator / g;
			this.den = -denominator / g;
		}
	}

	// ****************************************************************

	public Rational times(Rational b) {
		Rational a = this;
		int num = a.num * b.num;
		int den = a.den * b.den;
		return new Rational(num, den);
	}

	public Rational plus(Rational b) {
		Rational a = this;

		// special cases (Really Not Needed)
		if (a.equals(Rational.ZERO))
			return b;
		if (b.compareTo(Rational.ZERO) == 0)
			return a;

		int num = a.num * b.den + a.den * b.num;
		int den = a.den * b.den;

		return new Rational(num, den);
	}

	public Rational negate() {
		return new Rational(-this.num, this.den);
	}

	public Rational minus(Rational b) {
		Rational a = this;
		return a.plus(b.negate());
	}

	public Rational divides(Rational b) {
		Rational a = this;
		return a.times(b.reciprocal());
	}

	public Rational reciprocal() {
		return new Rational(this.den, this.num);
	}

	public Rational abs() {
		if (this.num >= 0)
			return this;
		else
			return this.negate();
	}

	// ****************************************************************

	public double toDouble() {
		return (double) this.num / this.den;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Rational other = (Rational) obj;
		if (den != other.den)
			return false;
		if (num != other.num)
			return false;
		return true;
	}

	public String toString() {
		if (den == 1)
			return num + "";
		else
			return num + "/" + den;
	}

	// return { -, 0, + } if a < b, a = b, or a > b
	// a.compareTo(b) < 0 if a < b
	public int compareTo(Rational b) {
		Rational a = this;
		int lhs = a.num * b.den;
		int rhs = a.den * b.num;
		if (lhs < rhs)
			return -1;
		if (lhs > rhs)
			return 1;
		return 0;
	}

	private static int gcf(int m, int n) {
		int retval = 0;
		if (m < 0)
			m = -m;
		if (n < 0)
			n = -n;
		if (n == 0)
			retval = m;
		else {
			int rem;
			while ((rem = m % n) != 0) {
				m = n;
				n = rem;
			}
			retval = n;
		}
		return retval;
	}

	// Not needed or used, but just cool to see
	@SuppressWarnings("unused")
	private static int lcm(int m, int n) {
		if (m < 0)
			m = -m;
		if (n < 0)
			n = -n;
		return m * n / gcf(m, n);
	}

}