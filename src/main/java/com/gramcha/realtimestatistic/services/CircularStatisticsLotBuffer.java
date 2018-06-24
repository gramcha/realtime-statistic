/**
 * @author gramcha
 * 23-Jun-2018 6:15:39 PM
 * 
 */
package com.gramcha.realtimestatistic.services;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BinaryOperator;

import javax.annotation.PostConstruct;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gramcha.realtimestatistic.config.AppllicationProperties;
import com.gramcha.realtimestatistic.Utilities.TimeStampHelper;
import com.gramcha.realtimestatistic.models.StatisticsDto;

@Service
public class CircularStatisticsLotBuffer implements StatisticsLotBuffer {

	@Autowired
	AppllicationProperties props;
	
//	private final int ((int)props.getTimeInterval()) = 60;// intervel seconds //TODO: move to application.properties file

	StatisticsLot[] lotBuffers = null;

	private AtomicReferenceArray<StatisticsLot> lots = null;
	@Autowired
	StatisticsAggregator aggregator;

	@PostConstruct
	void init() {
		lotBuffers = createLotBuffers();
		lots = new AtomicReferenceArray<>(lotBuffers);;
	}
	private StatisticsLot[] createLotBuffers() {
		System.out.println("");
		StatisticsLot[] lotBuffers = new StatisticsLot[(int)props.getTimeInterval()];
		Arrays.fill(lotBuffers, new StatisticsLotImpl());
		return lotBuffers;
	}
	
	
	private int getLotIdForGivenTimestamp(long timeStamp) {
		long timestampInSeconds = TimeStampHelper.getSecondsFromMilliSeconds(timeStamp);
		return (int) timestampInSeconds % ((int)props.getTimeInterval());
	}

	private StatisticsLot getDefaultStatistics() {
		StatisticsLot defaultLot = new StatisticsLotImpl();
		return defaultLot;
	}

	private StatisticsDto getStatisticsForSingleTransaction(double amount) {
		StatisticsDto singleStatistics = new StatisticsDto(amount, amount / 1, amount, amount, 1);// double sum, double
																									// avg, double min,
																									// double max, long
																									// count
		return singleStatistics;
	}

	private BinaryOperator<StatisticsLot> getAccumulatorFunction(long timeStamp) {
		
		BinaryOperator<StatisticsLot> accumulator = (lot1, lot2) -> {
			return lotReplacement(timeStamp, lot1, lot2);
		};
		return accumulator;
	}
	@VisibleForTesting
	StatisticsLot lotReplacement(long timeStamp, StatisticsLot lot1, StatisticsLot lot2) {
		if (lot1.getTimeStamp() == 0
				|| false == TimeStampHelper.isInTimeInterval(lot1.getTimeStamp(), timeStamp, ((int)props.getTimeInterval()))) {
			System.out.println("lot1.getTimeStamp() = "+lot1.getTimeStamp());
			System.out.println("@accumulator timeStamp ="+timeStamp);
			// lot1 actualy default one or lot1 timestamp is older than 60 secs
			if (TimeStampHelper.isInTimeInterval(lot2.getTimeStamp(),timeStamp, ((int)props.getTimeInterval()))) {
				System.out.println("lot2 check = "+ "true");
				return lot2;
			} else {
				return getDefaultStatistics();
			}
		}
		System.out.println("3");
		if (false == TimeStampHelper.isInTimeInterval(lot2.getTimeStamp(), timeStamp, ((int)props.getTimeInterval()))) {
			return lot1;
		}
		System.out.println("4");
		// add two lots statistics if both are in valid timeinterval
		StatisticsDto newStatistics = aggregator.aggregateTwoLots(lot1.getStatistics(), lot2.getStatistics());
		return new StatisticsLotImpl(lot1.getTimeStamp(), newStatistics);
	}

	// private StatisticsDto aggregateTwoLots(StatisticsLot lot1, StatisticsLot
	// lot2) {
	// StatisticsDto lot1Statistics = lot1.getStatistics();
	// StatisticsDto lot2Statistics = lot2.getStatistics();
	// double sum = lot1Statistics.getSum() + lot2Statistics.getSum();
	// double min=Math.min(lot1Statistics.getMin(), lot2Statistics.getMin());
	// double max=Math.max(lot1Statistics.getMax(), lot2Statistics.getMax());
	// long count=lot1Statistics.getCount() + lot2Statistics.getCount();
	// double avg = sum/count;
	//
	// StatisticsDto newStatistics = new
	// StatisticsDto(sum,avg,min,max,count);//double sum, double avg, double min,
	// double max, long count
	// return newStatistics;
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gramcha.realtimestatistic.services.StatisticsLotBuffer#add(double,
	 * java.lang.Long)
	 */
	@Override
	public StatisticsLot add(double amount, Long timeStamp) {
		System.out.println("1");
		if(false == TimeStampHelper.isInTimeInterval(timeStamp, TimeStampHelper.getCurrentTimeinMS(), ((int)props.getTimeInterval())) ) {
			return getDefaultStatistics();
		}
		System.out.println("11");
		long timestampInSeconds = TimeStampHelper.getSecondsFromMilliSeconds(timeStamp);
		int targetLotId = getLotIdForGivenTimestamp(timeStamp);
		StatisticsDto singleStatistics = getStatisticsForSingleTransaction(amount);
		StatisticsLot newLot = new StatisticsLotImpl(timeStamp, singleStatistics);
		System.out.println("targetLotId ="+targetLotId);
		System.out.println("newLot timestamp ="+timeStamp);
		
		return this.lots.accumulateAndGet(targetLotId, newLot, getAccumulatorFunction(timeStamp));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gramcha.realtimestatistic.services.StatisticsLotBuffer#get(int)
	 */
	@Override
	public StatisticsLot get(int i) {
		// TODO Auto-generated method stub
		return this.lots.get(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gramcha.realtimestatistic.services.StatisticsLotBuffer#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return ((int)props.getTimeInterval());// equal to this.lots.length();
	}

	@VisibleForTesting 
	void resetBufferWithdefault() {
		for(int i=0;i<lots.length();i++) {
			lots.set(i, new StatisticsLotImpl());
		}
	}
}
