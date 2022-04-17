#pragma once
#include <iostream>
#include <fstream>
#include <cstdint>
#include <cassert>

#include "ColorHelper.h"

#pragma pack(1)

struct BmpFileHeader 
{
	uint8_t     mFileMarker1;
	uint8_t     mFileMarker2;
	uint32_t    mTotalSize;
	uint16_t    mIgnore1;
	uint16_t    mIgnore2;
	uint32_t    mRealDataOffset;
};

struct BmpInfoHeader
{
    uint32_t    mInfoHeaderSize;
    int32_t     mWidth;
    int32_t     mHeight;
    uint16_t    mPlanes;
    uint16_t    mBitPerPix;
    uint32_t    mBiCompression;
    uint32_t    mSizeImage;
    int32_t     mXPixPerMeter;
    int32_t     mYPixPerMeter;
    uint32_t    mNumUsedColors;
    uint32_t    mNumSigColors;
};

#pragma pack(pop)

class BmpImage
{
public:

    BmpImage(const char* path);
    ~BmpImage();
    BmpImage(const BmpImage&) = delete;
    BmpImage& operator=(const BmpImage&) = delete;

    void SaveFile(const char* fileName) const;

    RGB24& operator[](size_t index) const;
    size_t GetSize() const;
    size_t GetLen() const;

private:

    RGB24* mData;
    size_t mLen;
    size_t mSize;
    size_t mHeaderSize;
    BmpFileHeader mFileHeader;
    BmpInfoHeader mInfoHeader;

};