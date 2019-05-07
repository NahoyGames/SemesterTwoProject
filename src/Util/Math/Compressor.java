package Util.Math;

public class Compressor
{

	public static byte scaleFloatToByte(float value, float min, float max)
	{
		float valueScale = (value - min) / (max - min);
		int byteRange = Byte.MAX_VALUE - Byte.MIN_VALUE;

		return (byte)(Byte.MIN_VALUE + (byte)(valueScale * (float)byteRange));
	}


	public static float scaleByteToFloat(byte value, float min, float max)
	{
		float valueScale = (float)(value - Byte.MIN_VALUE) / (Byte.MAX_VALUE - Byte.MIN_VALUE);
		float floatRange = max - min;

		return Math.round((min + valueScale * floatRange) * 100f) / 100f;
	}

}
