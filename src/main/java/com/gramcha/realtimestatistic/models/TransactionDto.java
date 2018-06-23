/**
 * @author gramcha
 * 23-Jun-2018 3:09:25 PM
 * 
 */
package com.gramcha.realtimestatistic.models;

public class TransactionDto {
    private Double amount;
    private Long timestamp;
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
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
