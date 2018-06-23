/**
 * @author gramcha
 * 23-Jun-2018 3:48:33 PM
 * 
 */
package com.gramcha.realtimestatistic.Utilities;

import java.util.concurrent.TimeUnit;

public class TimeStampHelper {
	public static long getCurrentTimeinMS() {
		return System.currentTimeMillis();
	}
	public static long getCurrentTimeinSeconds() {
		return getSecondsFromMilliSeconds(getCurrentTimeinMS());
	}
	public static long getSecondsFromMilliSeconds(long timeInMs) {
		return TimeUnit.MILLISECONDS.toSeconds(timeInMs);
	}
	public static boolean isInLastXSecondsInterval(long timestampInMs, long xSeconds) {
		long currentTimeinSeconds = getCurrentTimeinSeconds();
		long timestampinSeconds = getSecondsFromMilliSeconds(timestampInMs);
		long diffSeconds = currentTimeinSeconds - timestampinSeconds;
		System.out.println("differnce ="+diffSeconds);
//		System.out.println("System.currentTimeMillis() "+System.currentTimeMillis());
		if(diffSeconds >= 0L && diffSeconds <= xSeconds)
			return true;
		System.out.println("not in interval");
		return false;
	}
}
