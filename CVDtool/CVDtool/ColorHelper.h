#pragma once
#include <cstdint>
#include <cmath>
#include <cassert>

using FLOAT = float;

#pragma pack(1)

struct RGB24 
{
    uint8_t B;
    uint8_t G;
    uint8_t R;
};

struct HSV
{
    uint16_t H;
    FLOAT    S;
    FLOAT    V;
};

#pragma pop();

namespace ColorHelper
{
    inline bool IsFloatEqual(FLOAT lhs, FLOAT rhs, FLOAT sliceUnit)
    {
        assert(sliceUnit >= 0.f);
        return fabs(lhs - rhs) < sliceUnit;
    }

    HSV RGB24ToHSV(const RGB24& rgb);
    RGB24 HSVToRGB24(const HSV& hsv);
}