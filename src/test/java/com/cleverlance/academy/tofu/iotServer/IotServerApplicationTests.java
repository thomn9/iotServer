package com.cleverlance.academy.tofu.iotServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

@SpringBootTest
class IotServerApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {
		//todo make separate test on ranges
		Range<LocalTime> rangeToCheck = Range.between(LocalTime.of(8,0),LocalTime.of(10,0));

		Range<LocalTime> range1 = Range.between(LocalTime.of(7,0),LocalTime.of(9,0));
		Range<LocalTime> range2 = Range.between(LocalTime.of(9,0),LocalTime.of(11,0));
		Range<LocalTime> range3 = Range.between(LocalTime.of(8,0),LocalTime.of(10,0));
		Range<LocalTime> range4 = Range.between(LocalTime.of(8, 30),LocalTime.of(9,30));
		Range<LocalTime> range5no = Range.between(LocalTime.of(7, 0),LocalTime.of(8,0));
		Range<LocalTime> range6no = Range.between(LocalTime.of(10, 0),LocalTime.of(11,30));
		Range<LocalTime> range7 = Range.between(LocalTime.of(7, 0),LocalTime.of(12,30));
		Range<LocalTime> range8 = Range.between(LocalTime.of(8,0),LocalTime.of(9,0));
		Range<LocalTime> range9 = Range.between(LocalTime.of(9,0),LocalTime.of(10,0));
		Range<LocalTime> range10 = Range.between(LocalTime.of(10,30),LocalTime.of(10,45));

		assert rangeToCheck.isOverlappedBy(range1)  && rangeToCheck.intersectionWith(range1).getMinimum() != rangeToCheck.intersectionWith(range1).getMaximum();
		assert rangeToCheck.isOverlappedBy(range2);
		assert rangeToCheck.isOverlappedBy(range3);
		assert rangeToCheck.isOverlappedBy(range4);
		assert rangeToCheck.isOverlappedBy(range5no) && rangeToCheck.intersectionWith(range5no).getMinimum() == rangeToCheck.intersectionWith(range5no).getMaximum();
		assert rangeToCheck.isOverlappedBy(range6no) && rangeToCheck.intersectionWith(range5no).getMinimum() == rangeToCheck.intersectionWith(range5no).getMaximum();
		assert rangeToCheck.isOverlappedBy(range7);
		assert rangeToCheck.isOverlappedBy(range8);
		assert rangeToCheck.isOverlappedBy(range9);
		assert !rangeToCheck.isOverlappedBy(range10);

	}

}
