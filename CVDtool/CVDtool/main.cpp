#include <iostream>
#include <fstream>
#include <cstdint>
#include <cstring>
#include <Windows.h>

#include "BmpImage.h"
#include "CVDReColoring.h"

using namespace std;

int main(void)
{
	char fileName[] = "test_picture.bmp";

	BmpImage img(fileName);
	CVDReColoring cvdReCol(1.f);
	for (int i = 0; i < img.GetLen(); i++)
	{
		img[i] = cvdReCol.ReColor(img[i]);
	}
	img.SaveFile("test_picture_rgCVD.bmp");

	return 0;
}