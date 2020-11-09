package eney.util;

import java.math.BigInteger;

/**
 * Base 32 Encoding with Extended Hex Alphabet encoder.
 *
 * @see https://tools.ietf.org/html/rfc4648
 */
public class Base32HexUtil {
	public static final BigInteger BASE = BigInteger.valueOf(32);
	public static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUV";
	public static final String REGEXP = "^[0-9A-Za-z]+$";

	/**
	 * Encodes a number using Encoding with Extended Hex Alphabet.
	 *
	 * @param number
	 *            a positive integer
	 * @return a Base32 Encoding with Extended Hex Alphabet string
	 * @throws IllegalArgumentException
	 *             if <code>number</code> is a negative integer
	 */
	public static String encode(BigInteger number) {
		if (number.compareTo(BigInteger.ZERO) == -1) { // number < 0
			throw new IllegalArgumentException("number must not be negative");
		}
		StringBuilder result = new StringBuilder();
		while (number.compareTo(BigInteger.ZERO) == 1) { // number > 0
			BigInteger[] divmod = number.divideAndRemainder(BASE);
			number = divmod[0];
			int digit = divmod[1].intValue();
			result.insert(0, DIGITS.charAt(digit));
		}
		return (result.length() == 0) ? DIGITS.substring(0, 1) : result.toString();
	}

	public static String encode(long number) {
		return encode(BigInteger.valueOf(number));
	}

	/**
	 * Decodes a string using Base32 decoding with Extended Hex Alphabet.
	 *
	 * @param string
	 *            a Base32 decoding with Extended Hex Alphabet string
	 * @return a positive integer
	 * @throws IllegalArgumentException
	 *             if <code>string</code> is empty
	 */
	public static BigInteger decode(final String string) {
		if (string.length() == 0) {
			throw new IllegalArgumentException("string must not be empty");
		}
		BigInteger result = BigInteger.ZERO;
		int digits = string.length();
		for (int index = 0; index < digits; index++) {
			int digit = DIGITS.indexOf(string.charAt(digits - index - 1));
			result = result.add(BigInteger.valueOf(digit).multiply(BASE.pow(index)));
		}
		return result;
	}
}