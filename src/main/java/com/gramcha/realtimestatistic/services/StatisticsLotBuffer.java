/**
 * @author gramcha
 * 23-Jun-2018 6:09:04 PM
 * 
 */
package com.gramcha.realtimestatistic.services;

public interface StatisticsLotBuffer {
	StatisticsLot add(double amount, Long timeStamp);
	StatisticsLot get(int i);
    int size();
}
