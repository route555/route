package com.echo.framework.util;

public class BitUtil {
	public static byte[] reset(byte[] array, int flag) {
		if (array == null) {
			return array;
		}

		for (int i = 0; i < array.length; i++) {
			array[i] = (byte) flag;
		}

		return array;
	}

	public static byte[] reset(byte[] array) {
		return reset(array, 0x00);
	}

	public static int getBit(byte[] array, int bitIdx) {
		if ((array == null) || ((array.length * 8) < bitIdx)) {
			return -1;
		}

		int byteIdx = bitIdx / 8;
		int modIdx = bitIdx % 8;

		return array[byteIdx] & 0x01 << modIdx;
	}

	public static byte[] setBit(byte[] array, int bitIdx, int flag) {
		if ((array == null) || ((array.length * 8) < bitIdx)) {
			return array;
		}

		int byteIdx = bitIdx / 8;
		int modIdx = bitIdx % 8;

		array[byteIdx] |= flag << modIdx;

		return array;
	}

	public static byte[] setBit(byte[] array, int bitIdx) {
		return setBit(array, bitIdx, 0x01);
	}

	public static boolean isSet(byte[] array, int bitIdx) {
		return getBit(array, bitIdx) > 0;
	}
}
