/**
 * @author gramcha
 * 23-Jun-2018 10:00:50 PM
 * 
 */
package com.gramcha.realtimestatistic.services;

import org.springframework.stereotype.Service;

import com.gramcha.realtimestatistic.models.StatisticsDto;
@Service
public class StatisticsAggregator {
	public StatisticsDto aggregateTwoLots(StatisticsDto lot1Statistics, StatisticsDto lot2Statistics) {
		double sum = lot1Statistics.getSum() + lot2Statistics.getSum();
		System.out.println("lot 1 min = "+lot1Statistics.getMin());
		System.out.println("lot 2 min = "+lot2Statistics.getMin());
		double min=Math.min(lot1Statistics.getMin(), lot2Statistics.getMin());
		System.out.println("new min = "+min);
		double max=Math.max(lot1Statistics.getMax(), lot2Statistics.getMax());
		long count=lot1Statistics.getCount() + lot2Statistics.getCount();
		double avg = sum/count;
		
		StatisticsDto newStatistics =  new StatisticsDto(sum,avg,min,max,count);//double sum, double avg, double min, double max, long count
		return newStatistics;
	}
}
