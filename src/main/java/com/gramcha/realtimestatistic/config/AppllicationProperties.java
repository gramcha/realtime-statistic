/**
 * @author gramcha
 * 24-Jun-2018 2:36:10 PM
 * 
 */
package com.gramcha.realtimestatistic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Configuration
//@EnableConfigurationProperties
@Component
@ConfigurationProperties
public class AppllicationProperties {
	private long timeInterval;

	public long getTimeInterval() {
//		if(timeInterval == 0) {
//			timeInterval = 60;
//			System.out.println("Adding default 60secs property since it is missing in prop file.");
//		}
		return timeInterval;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}
}
