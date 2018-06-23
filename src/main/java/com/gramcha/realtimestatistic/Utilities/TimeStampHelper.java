/**
 * @author gramcha
 * 23-Jun-2018 3:48:33 PM
 * 
 */
package com.gramcha.realtimestatistic.Utilities;

import java.util.concurrent.TimeUnit;

public class TimeStampHelper {
	public static long getCurrentTimeinMS() {
		return System.currentTimeMillis() % 1000;
	}
	public static long getCurrentTimeinSeconds() {
		return TimeUnit.MILLISECONDS.toSeconds(getCurrentTimeinMS());
	}	
}
