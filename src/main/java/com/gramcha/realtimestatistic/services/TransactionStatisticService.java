/**
 * @author gramcha
 * 23-Jun-2018 4:16:00 PM
 * 
 */
package com.gramcha.realtimestatistic.services;

import com.gramcha.realtimestatistic.models.StatisticsDto;

public interface TransactionStatisticService {
	void addTransaction(double amount, long timestamp);
    StatisticsDto getStatisticsForInterval();
}
