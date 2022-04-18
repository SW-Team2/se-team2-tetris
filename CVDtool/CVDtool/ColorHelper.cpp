#include "ColorHelper.h"

HSV ColorHelper::RGB24ToHSV(const RGB24& rgb)
{
	double rgbSliceUnit = 1 / 255.f;

	double R = rgb.R / 255.f;
	double G = rgb.G / 255.f;
	double B = rgb.B / 255.f;
	double maxRGBf = std::fmax(std::fmax(R, G), B);
	double minRGBf = std::fmin(std::fmin(R, G), B);

	int H;
	double	 S;
	double	 V;
	// Calc V
	V = maxRGBf;
	// Calc S
	if (IsFloatEqual(V, 0.f, rgbSliceUnit)) // V == 0
	{
		S = 0.f;
	}
	else
	{
		S = (V - minRGBf) / V;
	}
	// Calc H
	if (V == minRGBf)
	{
		V += 0.000001;
	}
	H = 60 * (G - B) / (V - minRGBf);
	if (IsFloatEqual(V, R, rgbSliceUnit)) // V == R
	{
		H += 0;
	}
	else if (IsFloatEqual(V, G, rgbSliceUnit)) // V == G
	{
		H += 120;
	}
	else if (IsFloatEqual(V, B, rgbSliceUnit)) // V == B
	{
		H += 240;
	}
	else
	{
		assert(false);
	}

	if (H < 0)
	{
		H += 360;
	}
	assert(0 <= H && H <= 360);

	HSV result;
	result.H = H;
	result.S = S * 100;
	result.V = V * 100;
	return result;
}

RGB24 ColorHelper::HSVToRGB24(const HSV& hsv)
{
	assert(0 <= hsv.H && hsv.H < 360);
	assert(0.f <= hsv.S && hsv.S <= 100.f);
	assert(0.f <= hsv.V && hsv.V <= 100.0001f);
	
	FLOAT H = hsv.H;
	FLOAT S = hsv.S / 100;
	FLOAT V = hsv.V / 100;
	FLOAT C = S * V;
	FLOAT X = C * (1 - fabs(fmod(H / 60.f, 2) - 1));
	FLOAT m = V - C;

	FLOAT R, G, B;
	if (H >= 0 && H < 60) 
	{
		R = C, G = X, B = 0;
	}
	else if (H >= 60 && H < 120) 
	{
		R = X, G = C, B = 0;
	}
	else if (H >= 120 && H < 180) 
	{
		R = 0, G = C, B = X;
	}
	else if (H >= 180 && H < 240) 
	{
		R = 0, G = X, B = C;
	}
	else if (H >= 240 && H < 300) 
	{
		R = X, G = 0, B = C;
	}
	else 
	{
		R = C, G = 0, B = X;
	}

	RGB24 result;
	result.R = (R + m) * 255;
	result.G = (G + m) * 255;
	result.B = (B + m) * 255;
	assert(result.R < 256);
	assert(result.G < 256);
	assert(result.B < 256);

	return result;
}