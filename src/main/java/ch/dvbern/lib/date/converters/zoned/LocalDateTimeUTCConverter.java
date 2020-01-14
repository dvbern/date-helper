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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Converts a Time String that is formatted as an Iso-Zoned-Date-Time (preferably UTC) and converts it to the
 * LocalDefaultTimezone
 *
 * Also provides the reverse Functionality wherby the LocalDate (without Timezone information) is
 * interpreted as a Time at the current systemDefault Timezone and then converted into the same
 * time in the UTC Timezone and returned as a String representing that time in the UTC TimeZone
 */
@XmlJavaTypeAdapter(value = LocalDateTimeUTCConverter.class, type = ZonedDateTime.class)
public class LocalDateTimeUTCConverter extends XmlAdapter<String, LocalDateTime> {

	private final ZoneId localZoneId;

	public LocalDateTimeUTCConverter() {
		this.localZoneId = ZoneId.systemDefault();
	}

	public LocalDateTimeUTCConverter(ZoneId localZoneId) {
		this.localZoneId = localZoneId;
	}

	@Nullable
	@Override
	public LocalDateTime unmarshal(String v) {
		if (isEmpty(v)) {
			return null;
		}
		final ZonedDateTime timeUtc = ZonedDateTime.parse(v, DateTimeFormatter.ISO_ZONED_DATE_TIME);

		final ZonedDateTime localTime = timeUtc.withZoneSameInstant(localZoneId);
		return localTime.toLocalDateTime();

	}

	private boolean isEmpty(String v) {
		return (v == null || v.isEmpty());
	}

	@Nullable
	@Override
	public String marshal(LocalDateTime v) {
		if (v == null) {
			return null;
		}
		ZonedDateTime zonedDateTime = ZonedDateTime.of(v, localZoneId); // add local zone id to timestamp
		final ZonedDateTime passedTimeInUTC = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")); //transfrom to utc
		return passedTimeInUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
	}
}

