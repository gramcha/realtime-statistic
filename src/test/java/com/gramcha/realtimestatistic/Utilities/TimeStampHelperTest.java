/**
 * @author gramcha
 * 24-Jun-2018 8:28:16 PM
 * 
 */
package com.gramcha.realtimestatistic.Utilities;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gramcha.realtimestatistic.config.AppllicationProperties;



@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeStampHelperTest {

	@Autowired
	AppllicationProperties props;
	@Test
	public void validateMillisecondsToSecondsConversion() {
		Assert.assertEquals(10, TimeStampHelper.getSecondsFromMilliSeconds(10000L));
	}
	@Test
	public void validateInInTimeInvervalCaseForValidInterval() {
		Assert.assertEquals(true, TimeStampHelper.isInTimeInterval(10000L, 15000L, props.getTimeInterval()));
	}
	@Test
	public void validateInInTimeInvervalCaseForValidBorderInterval() {
		Assert.assertEquals(true, TimeStampHelper.isInTimeInterval(10000L, 70000L, props.getTimeInterval()));
	}
	@Test
	public void validateInInTimeInvervalCaseForInValidIntervalOlderThan60Seconds() {
		Assert.assertEquals(false, TimeStampHelper.isInTimeInterval(10000L, 75000L, props.getTimeInterval()));
	}
	@Test
	public void validateInInTimeInvervalCaseForInValidIntervalNewerThan60Seconds() {
		Assert.assertEquals(false, TimeStampHelper.isInTimeInterval(75000L, 10000L, props.getTimeInterval()));
	}
}
