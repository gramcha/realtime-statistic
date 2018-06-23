/**
 * @author gramcha
 * 23-Jun-2018 3:39:29 PM
 * 
 */
package com.gramcha.realtimestatistic.controllers;

import java.time.LocalDateTime;

import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.gramcha.realtimestatistic.RealtimeStatisticApplication;
import com.gramcha.realtimestatistic.Utilities.TimeStampHelper;

@RunWith(SpringRunner.class)
@WebMvcTest(RealtimeStatisticApplication.class)
public class RealtimeTransactionStatisticControllerTest {
	@Autowired
    private MockMvc mockMvc;
	@Test
	public void postTransactionforValidCase() throws Exception {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"amount\": 5.0");
		json.append(",");
		json.append("\"timestamp\": " + String.valueOf(TimeStampHelper.getCurrentTimeinMS()));
		json.append("}");
		
		 this.mockMvc.perform(post("/transactions")
		.contentType("application/json;charset=UTF-8")
		.content(json.toString())
		.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isCreated())
		.andExpect(content().bytes(new byte[0]));
	}
	@Test
	public void postTransactionforInValidCaseMissingTimestamp() throws Exception {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"amount\": 5.0");
		json.append("}");
		
		 this.mockMvc.perform(post("/transactions")
		.contentType("application/json;charset=UTF-8")
		.content(json.toString())
		.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isNoContent())
		.andExpect(content().bytes(new byte[0]));
	}
	@Test
	public void postTransactionforInValidCaseMissingAmount() throws Exception {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"timestamp\": " + String.valueOf(TimeStampHelper.getCurrentTimeinMS()));
		json.append("}");
		
		 this.mockMvc.perform(post("/transactions")
		.contentType("application/json;charset=UTF-8")
		.content(json.toString())
		.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isNoContent())
		.andExpect(content().bytes(new byte[0]));
	}
	@Test
	public void postTransactionforInValidCaseOldTimeStamp() throws Exception {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"amount\": 5.0");
		json.append(",");
		json.append("\"timestamp\": " + "1529752570367");//1529752570367 some old timestamp
		json.append("}");
		
		 this.mockMvc.perform(post("/transactions")
		.contentType("application/json;charset=UTF-8")
		.content(json.toString())
		.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isNoContent())
		.andExpect(content().bytes(new byte[0]));
	}
	@Test
	public void postTransactionforInValidCaseFutureTimeStamp() throws Exception {
		long currentPlus40Secs = TimeStampHelper.getCurrentTimeinMS()+40000L;
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"amount\": 5.0");
		json.append(",");
		json.append("\"timestamp\": " + String.valueOf(currentPlus40Secs));
		json.append("}");
		 this.mockMvc.perform(post("/transactions")
		.contentType("application/json;charset=UTF-8")
		.content(json.toString())
		.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isNoContent())
		.andExpect(content().bytes(new byte[0]));
	}
	@Test
	public void getStatistics() throws Exception {
		this.mockMvc.perform(get("/statistics")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
}
