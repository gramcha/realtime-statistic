/**
 * @author gramcha
 * 23-Jun-2018 10:19:30 PM
 * 
 */
package com.gramcha.realtimestatistic.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gramcha.realtimestatistic.Utilities.TimeStampHelper;
import com.gramcha.realtimestatistic.models.StatisticsDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CircularStatisticsLotBufferTest {

	@Autowired
	CircularStatisticsLotBuffer buffer;// = new CircularStatisticsLotBuffer();
	final double epsilon = 0.01;
	final int timeIntervalSeconds = 60;// TODO: move to the prop file.

	@After
	public void setup() {
		buffer.resetBufferWithdefault();
	}

	@Test
	public void addTransactionForValidIntervalSuccessCaseThenValidateSatisticsSum() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		StatisticsLot slot = buffer.add(10, inputTimeStampInMs);
		Assert.assertEquals(10, slot.getStatistics().getSum(), epsilon);
	}

	@Test
	public void addTransactionForValidIntervalSuccessCaseThenValidateLotID() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		long inputTimeStampInSeconds = TimeStampHelper.getSecondsFromMilliSeconds(inputTimeStampInMs);
		long expectedLotId = inputTimeStampInSeconds % timeIntervalSeconds;
		StatisticsLot slot = buffer.add(10, inputTimeStampInMs);
		Assert.assertEquals(slot, buffer.get((int) expectedLotId));
	}

	@Test
	public void addTransactionForOlderThan60IntervalFailureCaseThenValidateSatisticsSum() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		System.out.println("inputTimeStampInMs =" + inputTimeStampInMs);
		inputTimeStampInMs -= (timeIntervalSeconds + 1) * 1000;// minus 61 secs
		System.out.println("after 61 sec subtractioninputTimeStampInMs =" + inputTimeStampInMs);
		StatisticsLot slot = buffer.add(10, inputTimeStampInMs);
		Assert.assertEquals(0, slot.getStatistics().getSum(), epsilon);
	}

	@Test
	public void addTransactionForValidIntervalTwiceThenValidateAggregatedSatisticsSum() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		StatisticsLot slot = buffer.add(10, inputTimeStampInMs);
		slot = buffer.add(20, inputTimeStampInMs);
		Assert.assertEquals(30, slot.getStatistics().getSum(), epsilon);
		Assert.assertEquals(2, slot.getStatistics().getCount());
		Assert.assertEquals(15, slot.getStatistics().getAvg(), epsilon);
		Assert.assertEquals(10, slot.getStatistics().getMin(), epsilon);
		Assert.assertEquals(20, slot.getStatistics().getMax(), epsilon);
	}

	@Test
	public void addingValidTranactionLotOverAnEmptyLotThenValidateResultForNewLot() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		StatisticsLot slot = new StatisticsLotImpl(inputTimeStampInMs, new StatisticsDto(10,10,10,10,1));//double sum, double avg, double min, double max, long count
		StatisticsLot emptyLot = new StatisticsLotImpl();
		StatisticsLot result = buffer.lotReplacement(inputTimeStampInMs, emptyLot, slot);
		Assert.assertSame(slot, result);
	}
	@Test
	public void addingInValidTranactionLotOverAnEmptyLotThenValidateResultForDefaultEmptyLot() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		inputTimeStampInMs -= (timeIntervalSeconds+10)*1000;
		StatisticsLot slot = new StatisticsLotImpl(inputTimeStampInMs, new StatisticsDto(10,10,10,10,1));//double sum, double avg, double min, double max, long count
		StatisticsLot emptyLot = new StatisticsLotImpl();
		StatisticsLot result = buffer.lotReplacement(TimeStampHelper.getCurrentTimeinMS(), emptyLot, slot);
		Assert.assertEquals(0L,(long)result.getTimeStamp());
		Assert.assertEquals(0.0,result.getStatistics().getSum(),epsilon);
	}
	@Test
	public void addingInValidTranactionLotOverAInvalidLotThenValidateResultForValidLot() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		StatisticsLot validLot = new StatisticsLotImpl(inputTimeStampInMs, new StatisticsDto(10,10,10,10,1));//double sum, double avg, double min, double max, long count
		inputTimeStampInMs -= (timeIntervalSeconds+10)*1000;
		StatisticsLot invalidLot = new StatisticsLotImpl(inputTimeStampInMs, new StatisticsDto(10,10,10,10,1));//double sum, double avg, double min, double max, long count
		StatisticsLot result = buffer.lotReplacement(TimeStampHelper.getCurrentTimeinMS(), validLot, invalidLot);
		Assert.assertSame(validLot, result);
	}
	@Test
	public void addingInValidTranactionLotOverAValidLotThenValidateResultForAggregatedLot() {
		long inputTimeStampInMs = TimeStampHelper.getCurrentTimeinMS();
		StatisticsLot validLot = new StatisticsLotImpl(inputTimeStampInMs, new StatisticsDto(10,10,10,10,1));//double sum, double avg, double min, double max, long count
		StatisticsLot invalidLot = new StatisticsLotImpl(inputTimeStampInMs, new StatisticsDto(20,10,10,10,1));//double sum, double avg, double min, double max, long count
		StatisticsLot result = buffer.lotReplacement(TimeStampHelper.getCurrentTimeinMS(), validLot, invalidLot);
		Assert.assertEquals(30,result.getStatistics().getSum(),epsilon);
		Assert.assertEquals(15,result.getStatistics().getAvg(),epsilon);
		Assert.assertEquals(10,result.getStatistics().getMin(),epsilon);
		Assert.assertEquals(10,result.getStatistics().getMax(),epsilon);
		Assert.assertEquals(2,result.getStatistics().getCount());
	}
}
