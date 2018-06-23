/**
 * @author gramcha
 * 23-Jun-2018 3:10:37 PM
 * 
 */
package com.gramcha.realtimestatistic.models;

public class StatisticsDto {
	private double sum;
	private double avg;
	private double min;
	private double max;
	private long count;
	
	public void setSum(double sum) {
		this.sum = sum;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public double getSum() {
		return sum;
	}
	public double getAvg() {
		return avg;
	}
	public double getMin() {
		return min;
	}
	public double getMax() {
		return max;
	}
	public long getCount() {
		return count;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatisticsDto [sum=");
		builder.append(sum);
		builder.append(", avg=");
		builder.append(avg);
		builder.append(", min=");
		builder.append(min);
		builder.append(", max=");
		builder.append(max);
		builder.append(", count=");
		builder.append(count);
		builder.append("]");
		return builder.toString();
	}
	
}
