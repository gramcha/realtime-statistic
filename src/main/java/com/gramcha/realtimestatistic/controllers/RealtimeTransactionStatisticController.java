/**
 * @author gramcha
 * 23-Jun-2018 1:51:59 PM
 * 
 */
package com.gramcha.realtimestatistic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gramcha.realtimestatistic.models.StatisticsDto;
import com.gramcha.realtimestatistic.models.TransactionDto;

@RestController
public class RealtimeTransactionStatisticController {

	@RequestMapping(value="/")
	public ResponseEntity<String> healthCheck(){
		return ResponseEntity.ok("ping - pong. Service health ok.");
	}
	
	@RequestMapping(value="/transactions",method=RequestMethod.POST)
	public ResponseEntity<?> createTransaction(@RequestBody TransactionDto transactionDto){
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="/statistics",method=RequestMethod.GET)
	public StatisticsDto getStatistics(){
		return new StatisticsDto();
	}
}
