/**
 * @author gramcha
 * 23-Jun-2018 6:00:36 PM
 * 
 */
package com.gramcha.realtimestatistic.services;

import com.gramcha.realtimestatistic.models.StatisticsDto;

public interface StatisticsLot {
	Long getTimeStamp();
	StatisticsDto getStatistics();
}
