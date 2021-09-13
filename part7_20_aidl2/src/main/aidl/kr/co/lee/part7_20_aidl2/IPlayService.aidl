// IPlayService.aidl
package kr.co.lee.part7_20_aidl2;

// Declare any non-default types here with import statements

interface IPlayService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int currentPosition();
         int getMaxDuration();
         void start();
         void stop();
         int getMediaStatus();
}