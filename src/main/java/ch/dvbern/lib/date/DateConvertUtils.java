/*
 * Copyright 2017. DV Bern AG
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

package ch.dvbern.lib.date;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utilities for conversion between the old and new JDK date types
 * (between {@code java.util.Date} and {@code java.time.*}).
 *
 * <p>
 * All methods are null-safe.
 */
public class DateConvertUtils {

	/**
	 * Calls {@link #asLocalDate(Date, ZoneId)} with the system default time zone.
	 */
	@Nullable
	public static LocalDate asLocalDate(@Nullable Date date) {
		return asLocalDate(date, ZoneId.systemDefault());
	}

	/**
	 * Creates {@link LocalDate} from {@code java.util.Date} or it's subclasses. Null-safe.
	 */
	@Nullable
	public static LocalDate asLocalDate(@Nullable Date date, @Nonnull ZoneId zone) {
		if (date == null) {
			return null;
		}

		if (date instanceof java.sql.Date) {
			return ((java.sql.Date) date).toLocalDate();
		} else {
			return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
		}
	}

	/**
	 * Calls {@link #asLocalDateTime(Date, ZoneId)} with the system default time zone.
	 */
	@Nullable
	public static LocalDateTime asLocalDateTime(@Nullable Date date) {
		return asLocalDateTime(date, ZoneId.systemDefault());
	}

	/**
	 * Creates {@link LocalDateTime} from {@code java.util.Date} or it's subclasses. Null-safe.
	 */
	@Nullable
	public static LocalDateTime asLocalDateTime(@Nullable Date date, @Nonnull ZoneId zone) {
		if (date == null) {
			return null;
		}

		if (date instanceof Timestamp) {
			return ((Timestamp) date).toLocalDateTime();
		} else {
			return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDateTime();
		}
	}

	/**
	 * Calls {@link #asUtilDate(Object, ZoneId)} with the system default time zone.
	 */
	@Nullable
	public static Date asUtilDate(@Nullable Object date) {
		return asUtilDate(date, ZoneId.systemDefault());
	}

	/**
	 * Creates a {@link Date} from various date objects. Is null-safe. Currently supports:<ul>
	 * <li>{@link Date}
	 * <li>{@link java.sql.Date}
	 * <li>{@link Timestamp}
	 * <li>{@link LocalDate}
	 * <li>{@link LocalDateTime}
	 * <li>{@link ZonedDateTime}
	 * <li>{@link Instant}
	 * </ul>
	 *
	 * @param zone Time zone, used only if the input object is LocalDate or LocalDateTime.
	 * @return {@link Date} (exactly this class, not a subclass, such as java.sql.Date)
	 */
	@Nullable
	public static Date asUtilDate(@Nullable Object date, @Nonnull ZoneId zone) {
		if (date == null) {
			return null;
		}

		if (date instanceof java.sql.Date || date instanceof Timestamp) {
			return new Date(((Date) date).getTime());
		}
		if (date instanceof Date) {
			return (Date) date;
		}
		if (date instanceof LocalDate) {
			return Date.from(((LocalDate) date).atStartOfDay(zone).toInstant());
		}
		if (date instanceof LocalDateTime) {
			return Date.from(((LocalDateTime) date).atZone(zone).toInstant());
		}
		if (date instanceof ZonedDateTime) {
			return Date.from(((ZonedDateTime) date).toInstant());
		}
		if (date instanceof Instant) {
			return Date.from((Instant) date);
		}

		throw new UnsupportedOperationException("Don't know hot to convert " + date.getClass().getName() + " to java.util.Date");
	}

	/**
	 * Creates an {@link Instant} from {@code java.util.Date} or it's subclasses. Null-safe.
	 */
	@Nullable
	public static Instant asInstant(@Nullable Date date) {
		if (date == null) {
			return null;
		} else {
			return Instant.ofEpochMilli(date.getTime());
		}
	}

	/**
	 * Calls {@link #asZonedDateTime(Date, ZoneId)} with the system default time zone.
	 */
	@Nullable
	public static ZonedDateTime asZonedDateTime(@Nullable Date date) {
		return asZonedDateTime(date, ZoneId.systemDefault());
	}

	/**
	 * Creates {@link ZonedDateTime} from {@code java.util.Date} or it's subclasses. Null-safe.
	 */
	@Nullable
	public static ZonedDateTime asZonedDateTime(@Nullable Date date, ZoneId zone) {
		if (date == null) {
			return null;
		} else {
			return asInstant(date).atZone(zone);
		}
	}

}
