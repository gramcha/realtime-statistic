/**
 * @author gramcha
 * 23-Jun-2018 4:19:30 PM
 * 
 */
package com.gramcha.realtimestatistic.services;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gramcha.realtimestatistic.Utilities.TimeStampHelper;
import com.gramcha.realtimestatistic.config.AppllicationProperties;
import com.gramcha.realtimestatistic.models.StatisticsDto;

@Service
public class TransactionStatisticServiceImpl implements TransactionStatisticService {

	@Autowired
	AppllicationProperties props;
	
	@Autowired
	private CircularStatisticsLotBuffer lotBuffer;
	@Autowired
	StatisticsAggregator aggregator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gramcha.realtimestatistic.services.TransactionStatisticService#
	 * addTransaction(double, long)
	 */
	@Override
	public void addTransaction(double amount, long timestamp) {
		// TODO Auto-generated method stub
		StatisticsLot result = lotBuffer.add(amount, timestamp);
		System.out.println("Added Result = "+result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gramcha.realtimestatistic.services.TransactionStatisticService#
	 * getStatisticsForInterval()
	 */
	@Override
	public StatisticsDto getStatisticsForInterval() {
		// TODO Auto-generated method stub
		StatisticsDto result = new StatisticsDto();
		long currentTimeStamp = TimeStampHelper.getCurrentTimeinMS();
		for (int i = 0; i < lotBuffer.size(); i++) {
			StatisticsLot lot = lotBuffer.get(i);
			if(TimeStampHelper.isInTimeInterval(lot.getTimeStamp(), currentTimeStamp, props.getTimeInterval())) {//move 60 seconds to prop file.
				result = aggregator.aggregateTwoLots(result, lot.getStatistics());
			}
		}
		return result;
	}
	@VisibleForTesting
	void resetBuffer() {
		lotBuffer.resetBufferWithdefault();
	}
}
