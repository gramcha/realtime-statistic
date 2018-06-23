/**
 * @author gramcha
 * 24-Jun-2018 12:28:17 AM
 * 
 */
package com.gramcha.realtimestatistic.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gramcha.realtimestatistic.Utilities.TimeStampHelper;
import com.gramcha.realtimestatistic.models.StatisticsDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionStatisticServiceTest {
	@Autowired
	TransactionStatisticService transactionStatisticServiceImpl;
	private final double epsilon = 0.01;
	@After
	public void cleanup() {
		TransactionStatisticServiceImpl impl = (TransactionStatisticServiceImpl)transactionStatisticServiceImpl;
		impl.resetBuffer();
	}
	@Test
	public void addMultipleTransactionsForValidIntervalSuccessCaseThenValidateAggregatedResult() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		inputTimeStampInMs = inputTimeStampInMs - 15000;//backtracking 15 seconds
		transactionStatisticServiceImpl.addTransaction(100, inputTimeStampInMs);
		transactionStatisticServiceImpl.addTransaction(100, inputTimeStampInMs+1000);
		transactionStatisticServiceImpl.addTransaction(100, inputTimeStampInMs+2000);
		transactionStatisticServiceImpl.addTransaction(150, inputTimeStampInMs+3000);
		StatisticsDto result = transactionStatisticServiceImpl.getStatisticsForInterval();
		System.out.println("result = "+result);
		Assert.assertEquals(450, result.getSum(), epsilon);
		Assert.assertEquals(112.50, result.getAvg(), epsilon);
		Assert.assertEquals(150.0, result.getMax(), epsilon);
		Assert.assertEquals(100.0, result.getMin(), epsilon);
		Assert.assertEquals(4, result.getCount());
	}
	@Test
	public void addMultipleTransactionsForValidlAndInvalidIntervalThenValidateAggregatedResultIgnoredTheInvalid() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		inputTimeStampInMs = inputTimeStampInMs - 15000;//backtracking 15 seconds
		transactionStatisticServiceImpl.addTransaction(100, inputTimeStampInMs);
		transactionStatisticServiceImpl.addTransaction(100, inputTimeStampInMs+1000);
		transactionStatisticServiceImpl.addTransaction(100, inputTimeStampInMs+2000);
		inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		transactionStatisticServiceImpl.addTransaction(150, inputTimeStampInMs+3000);//ignored since it is invalid interval
		transactionStatisticServiceImpl.addTransaction(950, inputTimeStampInMs+4000);//ignored since it is invalid interval
		StatisticsDto result = transactionStatisticServiceImpl.getStatisticsForInterval();
		System.out.println("result = "+result);
		Assert.assertEquals(300, result.getSum(), epsilon);//added value due to previous test is in buffer.
		Assert.assertEquals(100, result.getAvg(), epsilon);
		Assert.assertEquals(100.0, result.getMax(), epsilon);
		Assert.assertEquals(100.0, result.getMin(), epsilon);
		Assert.assertEquals(3, result.getCount());
	}
}
