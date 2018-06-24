/**
 * @author gramcha
 * 23-Jun-2018 1:51:59 PM
 * 
 */
package com.gramcha.realtimestatistic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gramcha.realtimestatistic.Utilities.TimeStampHelper;
import com.gramcha.realtimestatistic.models.StatisticsDto;
import com.gramcha.realtimestatistic.models.TransactionDto;
import com.gramcha.realtimestatistic.services.TransactionStatisticService;

@RestController
public class RealtimeTransactionStatisticController {

	long inervalSeconds = 60L;// TODO: move to the application.properties file.
	@Autowired
	TransactionStatisticService transactionStatisticService;

	@RequestMapping(value = "/")
	public ResponseEntity<String> healthCheck() {
		return ResponseEntity.ok("ping - pong. Service health ok.");
	}

	private boolean isValidTransactionRequest(TransactionDto transactionDto) {
		if (transactionDto == null || transactionDto.getAmount() == null || transactionDto.getTimestamp() == null) {
			// System.out.println("missing data");
			return false;
		}
		return TimeStampHelper.isInTimeInterval(transactionDto.getTimestamp(), TimeStampHelper.getCurrentTimeinMS(),
				inervalSeconds);
	}

	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public ResponseEntity<?> createTransaction(@RequestBody TransactionDto transactionDto) {
		if (!isValidTransactionRequest(transactionDto)) {
//			System.out.println("sending 204");
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}
		transactionStatisticService.addTransaction(transactionDto.getAmount(), transactionDto.getTimestamp());
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public StatisticsDto getStatistics() {
		return transactionStatisticService.getStatisticsForInterval();
	}
}
