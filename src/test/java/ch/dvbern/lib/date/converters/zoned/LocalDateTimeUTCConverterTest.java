/*
 * Copyright 2020. DV Bern AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * limitations under the License.
 */
package ch.dvbern.lib.date.converters.zoned;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link LocalDateTimeUTCConverter}
 */
public class LocalDateTimeUTCConverterTest {



	@Test
	public void unmarshallMarshallTest(){
		LocalDateTimeUTCConverter defaultUTCToLocalDateConverter = new LocalDateTimeUTCConverter();
		final LocalDateTime unmrashalledDate = defaultUTCToLocalDateConverter.unmarshal("2020-01-14T19:05:53.432Z");
		assert unmrashalledDate != null;
		assertEquals(2020, unmrashalledDate.getYear());
		assertEquals(1, unmrashalledDate.getMonthValue());
		assertEquals(14, unmrashalledDate.getDayOfMonth());
		final ZoneOffset offset = ZoneId.of("Europe/London").getRules().getOffset(LocalDateTime.now());
		assertEquals("Wegen dem Zeitzonenunterschied sind wir hier  1 Stunde verschoben ",
			19 , unmrashalledDate.minusSeconds(offset.getTotalSeconds()).getHour());
		assertEquals(5, unmrashalledDate.getMinute());
		assertEquals(53, unmrashalledDate.getSecond());
		assertEquals(432000000, unmrashalledDate.getNano());

		final String marshal = defaultUTCToLocalDateConverter.marshal(unmrashalledDate);
		assertEquals("2020-01-14T19:05:53.432Z", marshal);

	}

	@Test
	public void unmarshallMarshallTestMoskau(){
		final ZoneId moscowZone = ZoneId.of("Europe/Moscow");
		LocalDateTimeUTCConverter moscowConverter = new LocalDateTimeUTCConverter(moscowZone);
		final LocalDateTime unmrashalledDate = moscowConverter.unmarshal("2020-01-14T19:05:53.432Z");
		assert unmrashalledDate != null;
		assertEquals(2020, unmrashalledDate.getYear());
		assertEquals(1, unmrashalledDate.getMonthValue());
		assertEquals(14, unmrashalledDate.getDayOfMonth());
		final ZoneOffset offset = moscowZone.getRules().getOffset(LocalDateTime.now());
		assertEquals("Wegen dem Zeitzonenunterschied sind wir hier zB 3 Stunde verschoben ",
			19 , unmrashalledDate.minusSeconds(offset.getTotalSeconds()).getHour());
		assertEquals(22, unmrashalledDate.getHour());  //actual time in moscaw at this instant
		assertEquals(5, unmrashalledDate.getMinute());
		assertEquals(53, unmrashalledDate.getSecond());
		assertEquals(432000000, unmrashalledDate.getNano());

		final String marshal = moscowConverter.marshal(unmrashalledDate);
		assertEquals("2020-01-14T19:05:53.432Z", marshal);

	}

	@Test
	public void unmarshallMarshallTestLA(){
		final ZoneId losAnglesZone = ZoneId.of("America/Los_Angeles");
		LocalDateTimeUTCConverter laConverter = new LocalDateTimeUTCConverter(losAnglesZone);
		final LocalDateTime unmrashalledDate = laConverter.unmarshal("2020-01-14T19:05:53.432Z");
		assert unmrashalledDate != null;
		assertEquals(2020, unmrashalledDate.getYear());
		assertEquals(1, unmrashalledDate.getMonthValue());
		assertEquals(14, unmrashalledDate.getDayOfMonth());
		assertEquals(11, unmrashalledDate.getHour()); //actual time in la at this instant
		assertEquals(5, unmrashalledDate.getMinute());
		assertEquals(53, unmrashalledDate.getSecond());
		assertEquals(432000000, unmrashalledDate.getNano());

		final String marshal = laConverter.marshal(unmrashalledDate);
		assertEquals("2020-01-14T19:05:53.432Z", marshal);

	}
}
