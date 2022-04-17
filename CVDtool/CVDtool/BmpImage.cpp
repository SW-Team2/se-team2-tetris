#include "BmpImage.h"

BmpImage::BmpImage(const char* path) : mData(nullptr)
{
	static_assert(sizeof(BmpFileHeader) == 14, "BmpImage.h Packing error");
	static_assert(sizeof(BmpInfoHeader) == 40, "BmpImage.h Packing error");
	static_assert(sizeof(RGB24) == 3, "BmpImage.h Packing error");

	assert(path != nullptr, "Bmp file path is nullptr");
	std::ifstream file;
	file.open(path, std::ios::binary);

	file.read(reinterpret_cast<char*>(&mFileHeader), sizeof(BmpFileHeader));
	file.read(reinterpret_cast<char*>(&mInfoHeader), sizeof(BmpInfoHeader));

	mSize = mInfoHeader.mWidth * mInfoHeader.mWidth * sizeof(RGB24);
	mLen = mSize / sizeof(RGB24);
	assert(mLen != 0, "Empty bmp file");
	mData = new RGB24[mSize / sizeof(RGB24)];
	file.seekg(mFileHeader.mRealDataOffset);
	file.read(reinterpret_cast<char*>(mData), mSize);

	file.close();
}

BmpImage::~BmpImage()
{
	delete[] mData;
}

void BmpImage::SaveFile(const char* fileName) const
{
	assert(mData != nullptr, "Bmp Data is nullptr");
	assert(fileName != nullptr, "Bmp file name is nullptr");
	std::ofstream file;
	file.open(fileName, std::ios::binary | std::ios::trunc);

	file.write(reinterpret_cast<const char*>(&mFileHeader), sizeof(BmpFileHeader));
	file.write(reinterpret_cast<const char*>(&mInfoHeader), sizeof(BmpInfoHeader));
	file.write(reinterpret_cast<const char*>(mData), mSize);

	file.close();
}

RGB24& BmpImage::operator[](size_t index) const
{
	assert(index < mLen);
	return mData[index];
}

size_t BmpImage::GetSize() const
{
	return mSize;
}

size_t BmpImage::GetLen() const
{
	return mLen;
}