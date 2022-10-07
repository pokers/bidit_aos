package com.alexk.bidit.di

import android.os.Looper
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.R
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client

class S3Client {
    fun provideS3Client(): AmazonS3Client {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the s3Client instance"
        }
        val awsCredentials = BasicAWSCredentials(
            GlobalApplication.applicationContext().getString(R.string.S3_ACCESS_KEY),
            GlobalApplication.applicationContext().getString(R.string.S3_SECRET_KEY)
        )
        return AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2))
    }
}