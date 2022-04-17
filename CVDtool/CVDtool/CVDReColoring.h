#pragma once
#include <assert.h>

#include "ColorHelper.h"

class CVDReColoring
{
public:

	CVDReColoring(FLOAT userParam);
	~CVDReColoring();
	CVDReColoring(const CVDReColoring&) = delete;
	CVDReColoring& operator=(const CVDReColoring&) = delete;

	RGB24 ReColor(const RGB24& rgb);

private: 

	FLOAT mUserParam;

};