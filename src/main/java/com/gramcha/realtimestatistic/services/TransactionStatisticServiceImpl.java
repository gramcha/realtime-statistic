/**
 * @author gramcha
 * 23-Jun-2018 4:19:30 PM
 * 
 */
package com.gramcha.realtimestatistic.services;
import org.springframework.stereotype.Service;
import com.gramcha.realtimestatistic.models.StatisticsDto;

@Service
public class TransactionStatisticServiceImpl implements TransactionStatisticService {

	/* (non-Javadoc)
	 * @see com.gramcha.realtimestatistic.services.TransactionStatisticService#addTransaction(double, long)
	 */
	@Override
	public void addTransaction(double amount, long timestamp) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.gramcha.realtimestatistic.services.TransactionStatisticService#getStatisticsForInterval()
	 */
	@Override
	public StatisticsDto getStatisticsForInterval() {
		// TODO Auto-generated method stub
		return null;
	}

}
