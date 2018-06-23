/**
 * @author gramcha
 * 23-Jun-2018 6:33:06 PM
 * 
 */
package com.gramcha.realtimestatistic.services;

import com.gramcha.realtimestatistic.models.StatisticsDto;


public class StatisticsLotImpl implements StatisticsLot {
	private final long timeStamp;

    private final StatisticsDto statisticsDto;

    StatisticsLotImpl(long timeStamp, StatisticsDto statisticsDto) {
        this.timeStamp = timeStamp;
        this.statisticsDto = statisticsDto;
    }
    StatisticsLotImpl(){
    		this.timeStamp = 0L; 
        this.statisticsDto = new StatisticsDto(0,0,0,0,0);
    }
	/* (non-Javadoc)
	 * @see com.gramcha.realtimestatistic.services.StatisticsLot#getTimeStamp()
	 */
	@Override
	public Long getTimeStamp() {
		// TODO Auto-generated method stub
		return timeStamp;
	}

	/* (non-Javadoc)
	 * @see com.gramcha.realtimestatistic.services.StatisticsLot#getStatistics()
	 */
	@Override
	public StatisticsDto getStatistics() {
		// TODO Auto-generated method stub
		return statisticsDto;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatisticsLotImpl [timeStamp=");
		builder.append(timeStamp);
		builder.append(", statisticsDto=");
		builder.append(statisticsDto);
		builder.append("]");
		return builder.toString();
	}

	
}
