/**
 * @author gramcha
 * 23-Jun-2018 3:09:25 PM
 * 
 */
package com.gramcha.realtimestatistic.models;

public class TransactionDto {
    private double amount;
    private long timestamp;
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransactionDto [amount=");
		builder.append(amount);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append("]");
		return builder.toString();
	}
    
}
