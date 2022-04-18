#include "CVDReColoring.h"

using namespace ColorHelper;

CVDReColoring::CVDReColoring(FLOAT userParam) : mUserParam(userParam)
{
	assert(0.f <= userParam && userParam <= 1.f);
}

CVDReColoring::~CVDReColoring()
{

}

RGB24 CVDReColoring::ReColor(const RGB24& rgb)
{
	// Only for Red Green CVD
	RGB24 result = rgb;

	HSV hsv = RGB24ToHSV(rgb);
	int alpha;
	int beta;
	int H2;
	int H1;
	int H = hsv.H;
	double V = hsv.V;
	double deltaV;
	int pB;
	assert(0 <= H && H <= 360);
	// Hue correction
	//{
	//	double hueRatio;
	//	if (60 <= H && H <= 180)
	//	{
	//		hueRatio = (H - 60.) / (double)(180 - 60);
	//		H = 120 + 60 * hueRatio;
	//	}
	//	else if (240 <= H && H <= 360)
	//	{
	//		hueRatio = (H - 240.) / (double)(360 - 240);
	//		H = 300 + 60 * hueRatio;
	//	}
	//	hsv.H = H;
	//	result = HSVToRGB24(hsv);
	//}
	// Value correction
	{
		if (0 <= H && H <= 50)
		{
			H1 = 0;
			H2 = 50;
			alpha = 255;
			beta = 39;
		}
		else  if (300 <= H && H <= 350)
		{
			H1 = 300;
			H2 = 350;
			alpha = 44;
			beta = 255;
		}
		else if (350 < H && H <= 360)
		{
			H1 = 350;
			H2 = 360;
			alpha = 39;
			beta = 44;
		}
		else
		{
			// Return origin value
			goto EXIT;
		}
		deltaV = V - (((static_cast<float>(alpha - beta) / (H2 - H1)) * (H - H1) + beta) / (255.f)) * V;

		assert(0.f <= mUserParam && mUserParam <= 1.f);
		pB = result.B + 3 * deltaV * mUserParam;
		//if (pB > 255)
		//{
		//	pB = 255;
		//}
		//else if (pB < 0)
		//{
		//	pB = 0;
		//}
		result.B += pB;
	}
EXIT:
	return result;
}