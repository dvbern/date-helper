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

package ch.dvbern.lib.date.feiertage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.dvbern.lib.date.DateHelper;
import junit.framework.TestCase;

public class FeiertageHelperTest extends TestCase {

	// Wochentage
	public static final String MONTAG = "30.03.2009";
	public static final String DIENSTAG = "31.03.2009";
	public static final String MITTWOCH = "01.04.2009";
	public static final String DONNERSTAG = "02.04.2009";
	public static final String FREITAG = "03.04.2009";
	public static final String SAMSTAG = "04.04.2009";
	public static final String SONNTAG = "05.04.2009";
	public static final String MONTAG_NEXT = "06.04.2009";
	public static final String SEPT_11 = "11.09.2001";

	// Fixe Feiertage
	public static final String NEUJAHR = "01.01.2009";
	public static final String NEUJAHR_MIN = "01.01.1900";
	public static final String NEUJAHR_MAX = "01.01.9999";
	public static final String BECHTOLDSTAG = "02.01.2009";
	public static final String NATIONALTAG = "01.08.2009";
	public static final String WEIHNACHTEN = "25.12.2009";
	public static final String STEPHANSTAG = "26.12.2009";

	// Bewegliche Feiertage
	public static final String KARFREITAG_2009 = "10.04.2009";
	public static final String OSTERN_2009 = "12.04.2009";
	public static final String OSTERMONTAG_2009 = "13.04.2009";
	public static final String AUFFAHRT_2009 = "21.05.2009";
	public static final String PFINGSTEN_2009 = "31.05.2009";
	public static final String PFINGSTMONTAG_2009 = "01.06.2009";

	public static final String KARFREITAG_2014 = "18.04.2014";
	public static final String OSTERN_2014 = "20.04.2014";
	public static final String OSTERMONTAG_2014 = "21.04.2014";
	public static final String AUFFAHRT_2014 = "29.05.2014";
	public static final String PFINGSTEN_2014 = "08.06.2014";
	public static final String PFINGSTMONTAG_2014 = "09.06.2014";

	public static final String KARFREITAG_2100 = "26.03.2100";
	public static final String OSTERN_2100 = "28.03.2100";
	public static final String OSTERMONTAG_2100 = "29.03.2100";
	public static final String AUFFAHRT_2100 = "06.05.2100";
	public static final String PFINGSTEN_2100 = "16.05.2100";
	public static final String PFINGSTMONTAG_2100 = "17.05.2100";
	public static final int TEST_YEAR_1 = 2009;

	private static String getFormattedString(Date date) {

		if (date != null) {
			return (new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss mmmmmm"))
				.format(date);
		} else {
			return "";
		}

	}

	public void testFeiertag_CH() {

		// Fixe Feiertagen
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.NEUJAHRSTAG), parseDate(NEUJAHR));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.BECHTOLDSTAG), parseDate(BECHTOLDSTAG));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.NATIONALFEIERTAG), parseDate(NATIONALTAG));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.WEIHNACHTEN), parseDate(WEIHNACHTEN));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.STEPHANSTAG), parseDate(STEPHANSTAG));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.NATIONALFEIERTAG), parseDate(NATIONALTAG));

		// Bewegliche Feiertage
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.KARFREITAG), parseDate(KARFREITAG_2009));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.OSTERN), parseDate(OSTERN_2009));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.OSTERMONTAG), parseDate(OSTERMONTAG_2009));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.AUFFAHRT), parseDate(AUFFAHRT_2009));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.PFINGSTEN), parseDate(PFINGSTEN_2009));
		assertEquals(FeiertageHelper.getFeiertag_CH(TEST_YEAR_1,
			FeiertagSchweiz.PFINGSTMONTAG), parseDate(PFINGSTMONTAG_2009));

		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.getFeiertag_CH(TEST_YEAR_1, null));
		} catch (IllegalArgumentException e) {
		}
	}

	public void testFeiertage_CH() {

		assertFalse("Montag " + MONTAG + " ist kein Feiertag", FeiertageHelper
			.isFeiertag_CH(parseDate(MONTAG)));
		assertFalse("Dienstag " + DIENSTAG + " ist kein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(DIENSTAG)));
		assertFalse("Mittwoch " + MITTWOCH + " ist kein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(MITTWOCH)));
		assertFalse("Donnerstag " + DONNERSTAG + " ist kein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(DONNERSTAG)));
		assertFalse("Freitag " + FREITAG + " ist kein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(FREITAG)));
		assertTrue("Samstag " + SAMSTAG + " ist ein Feiertag", FeiertageHelper
			.isFeiertag_CH(parseDate(SAMSTAG)));
		assertTrue("Sonntag " + SONNTAG + " ist ein Feiertag", FeiertageHelper
			.isFeiertag_CH(parseDate(SONNTAG)));
		assertFalse("9/11 " + SEPT_11 + " ist kein Feiertag", FeiertageHelper
			.isFeiertag_CH(parseDate(SEPT_11)));

		assertTrue("NEUJAHR " + NEUJAHR + " ist ein Feiertag", FeiertageHelper
			.isFeiertag_CH(parseDate(NEUJAHR)));
		assertTrue("NEUJAHR_MIN " + NEUJAHR_MIN + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(NEUJAHR_MIN)));
		assertTrue("NEUJAHR_MAX " + NEUJAHR_MAX + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(NEUJAHR_MAX)));
		assertTrue("BECHTOLDSTAG " + BECHTOLDSTAG + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(BECHTOLDSTAG)));
		assertTrue("NATIONALTAG " + NATIONALTAG + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(NATIONALTAG)));
		assertTrue("WEIHNACHTEN " + WEIHNACHTEN + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(WEIHNACHTEN)));
		assertTrue("STEPHANTAG " + STEPHANSTAG + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(STEPHANSTAG)));

		assertTrue("KARLFREITAG_2009 " + KARFREITAG_2009 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(KARFREITAG_2009)));
		assertTrue("OSTERN_2009 " + OSTERN_2009 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(OSTERN_2009)));
		assertTrue(
			"OSTERMONTAG_2009 " + OSTERMONTAG_2009 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(OSTERMONTAG_2009)));
		assertTrue("AUFFAHRT_2009 " + AUFFAHRT_2009 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(AUFFAHRT_2009)));
		assertTrue("PFINGSTEN_2009 " + PFINGSTEN_2009 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(PFINGSTEN_2009)));
		assertTrue("PFINGSTMONTAG_2009 " + PFINGSTMONTAG_2009
			+ " ist ein Feiertag", FeiertageHelper
			.isFeiertag_CH(parseDate(PFINGSTMONTAG_2009)));

		assertTrue("KARLFREITAG_2014 " + KARFREITAG_2014 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(KARFREITAG_2014)));
		assertTrue("OSTERN_2014 " + OSTERN_2014 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(OSTERN_2014)));
		assertTrue(
			"OSTERMONTAG_2014 " + OSTERMONTAG_2014 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(OSTERMONTAG_2014)));
		assertTrue("AUFFAHRT_2014 " + AUFFAHRT_2014 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(AUFFAHRT_2014)));
		assertTrue("PFINGSTEN_2014 " + PFINGSTEN_2014 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(PFINGSTEN_2014)));
		assertTrue("PFINGSTMONTAG_2014 " + PFINGSTMONTAG_2014
			+ " ist ein Feiertag", FeiertageHelper
			.isFeiertag_CH(parseDate(PFINGSTMONTAG_2014)));

		assertTrue("KARLFREITAG_2100 " + KARFREITAG_2100 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(KARFREITAG_2100)));
		assertTrue("OSTERN_2100 " + OSTERN_2100 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(OSTERN_2100)));
		assertTrue(
			"OSTERMONTAG_2100 " + OSTERMONTAG_2100 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(OSTERMONTAG_2100)));
		assertTrue("AUFFAHRT_2100 " + AUFFAHRT_2100 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(AUFFAHRT_2100)));
		assertTrue("PFINGSTEN_2100 " + PFINGSTEN_2100 + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(parseDate(PFINGSTEN_2100)));
		assertTrue("PFINGSTMONTAG_2100 " + PFINGSTMONTAG_2100
			+ " ist ein Feiertag", FeiertageHelper
			.isFeiertag_CH(parseDate(PFINGSTMONTAG_2100)));

		// Alternativ method
		assertTrue("NEUJAHR " + "01.01.1969" + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(01, 01, 1969));

		// Datum mit Time gesetzt
		Calendar cal = Calendar.getInstance();
		cal.set(TEST_YEAR_1, 0, 1);
		assertTrue("NEUJAHR " + "01.01.2009" + " ist ein Feiertag",
			FeiertageHelper.isFeiertag_CH(cal.getTime()));

		// IllegalArgumentException
		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.isFeiertag_CH(null));
		} catch (IllegalArgumentException e) {
		}

		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.isFeiertag_CH(0, 12, TEST_YEAR_1));
		} catch (IllegalArgumentException e) {
		}

		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.isFeiertag_CH(1, 0, TEST_YEAR_1));
		} catch (IllegalArgumentException e) {
		}

		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.isFeiertag_CH(1, 12, 1499));
		} catch (IllegalArgumentException e) {
		}

		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.isFeiertag_CH(-1, -1, -TEST_YEAR_1));
		} catch (IllegalArgumentException e) {
		}
	}

	public void testNextWorkingDay() {

		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 1),
			parseDate(DIENSTAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 2),
			parseDate(MITTWOCH));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 3),
			parseDate(DONNERSTAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 4),
			parseDate(FREITAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 5),
			parseDate(MONTAG_NEXT));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(FREITAG), 1),
			parseDate(MONTAG_NEXT));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(SAMSTAG), 1),
			parseDate(MONTAG_NEXT));

		assertEquals(FeiertageHelper.getNextWorkingDate(
			parseDate("30.12.2008"), 1), parseDate("31.12.2008"));
		assertEquals(FeiertageHelper.getNextWorkingDate(
			parseDate("30.12.2008"), 2), parseDate("05.01.2009"));
		assertEquals(FeiertageHelper.getNextWorkingDate(
			parseDate("01.04.2009"), 20), parseDate("21.04.2009")); // keine
		// Feiertage
		assertEquals(FeiertageHelper.getNextWorkingDate(
			parseDate("01.04.2009"), 10), parseDate("14.04.2009")); // �ber
		// Ostern

		// Zero-tests
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 0),
			parseDate(MONTAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(SAMSTAG), 0),
			parseDate(MONTAG_NEXT));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(SONNTAG), 0),
			parseDate(MONTAG_NEXT));
		assertEquals(FeiertageHelper.getNextWorkingDate(
			parseDate(KARFREITAG_2009), 0), parseDate("14.04.2009"));

		// IllegalArgumentException
		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.getNextWorkingDate(null, 0));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}
		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.getNextWorkingDate(new Date(), -1));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}
	}

	public void testNextWorkingDayWithSperrTagen() {

		List<Date> sperrTagen = new ArrayList<Date>();
		sperrTagen.add(parseDate(DIENSTAG));
		sperrTagen.add(parseDate(MITTWOCH));
		sperrTagen.add(parseDate(DONNERSTAG));

		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 1,
			sperrTagen), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 2,
			sperrTagen), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 3,
			sperrTagen), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 4,
			sperrTagen), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 5,
			sperrTagen), parseDate(MONTAG_NEXT));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(FREITAG), 1,
			sperrTagen), parseDate(MONTAG_NEXT));

		// Zero-tests
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 0,
			sperrTagen), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(DIENSTAG), 0,
			sperrTagen), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(FREITAG), 0,
			sperrTagen), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(SAMSTAG), 0,
			sperrTagen), parseDate(MONTAG_NEXT));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(SONNTAG), 0,
			sperrTagen), parseDate(MONTAG_NEXT));

		// Null-tests
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 0,
			null), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(MONTAG), 1,
			null), parseDate(DIENSTAG));
		assertEquals(FeiertageHelper.getNextWorkingDate(parseDate(FREITAG), 1,
			null), parseDate(MONTAG_NEXT));

		// IllegalArgumentException
		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.getNextWorkingDate(null, 0, sperrTagen));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}
		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.getNextWorkingDate(new Date(), -1, sperrTagen));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}
	}

	public void testPreviousWorkingDay() {

		List<Date> sperrTagen = new ArrayList<Date>();
		sperrTagen.add(parseDate(DIENSTAG));
		sperrTagen.add(parseDate(MITTWOCH));
		sperrTagen.add(parseDate(DONNERSTAG));

		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 1, sperrTagen), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 2, sperrTagen), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 3, sperrTagen), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 4, sperrTagen), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 5, sperrTagen), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 6, sperrTagen), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 7, sperrTagen), parseDate(MONTAG));

		// Zero-tests
		assertEquals(FeiertageHelper.getPreviousWorkingDate(parseDate(MONTAG),
			0, sperrTagen), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(DIENSTAG), 0, sperrTagen), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MITTWOCH), 0, sperrTagen), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(DONNERSTAG), 0, sperrTagen), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(parseDate(FREITAG),
			0, sperrTagen), parseDate(FREITAG));

		// Null-tests
		assertEquals(FeiertageHelper.getPreviousWorkingDate(parseDate(MONTAG),
			0, null), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(DIENSTAG), 1, null), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(parseDate(FREITAG),
			1, null), parseDate(DONNERSTAG));

		// IllegalArgumentException
		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.getPreviousWorkingDate(null, 0));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}
		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.getPreviousWorkingDate(new Date(), -1));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}
	}

	public void testAnzahlArbeitstage() {

		// Normale Wochen
		assertEquals(1, FeiertageHelper.getAnzahlArbeitstage(parseDate(MONTAG),
			parseDate(MONTAG)));
		assertEquals(2, FeiertageHelper.getAnzahlArbeitstage(parseDate(MONTAG),
			parseDate(DIENSTAG)));
		assertEquals(5, FeiertageHelper.getAnzahlArbeitstage(parseDate(MONTAG),
			parseDate(FREITAG)));
		assertEquals(5, FeiertageHelper.getAnzahlArbeitstage(parseDate(MONTAG),
			parseDate(SAMSTAG)));
		assertEquals(5, FeiertageHelper.getAnzahlArbeitstage(parseDate(MONTAG),
			parseDate(SONNTAG)));
		assertEquals(6, FeiertageHelper.getAnzahlArbeitstage(parseDate(MONTAG),
			parseDate(MONTAG_NEXT)));
		assertEquals(0, FeiertageHelper.getAnzahlArbeitstage(
			parseDate(SAMSTAG), parseDate(SAMSTAG)));
		assertEquals(0, FeiertageHelper.getAnzahlArbeitstage(
			parseDate(SAMSTAG), parseDate(SONNTAG)));
		assertEquals(1, FeiertageHelper.getAnzahlArbeitstage(
			parseDate(SAMSTAG), parseDate(MONTAG_NEXT)));
		assertEquals(11, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("01.12.2008"), parseDate("15.12.2008")));
		assertEquals(3, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("17.12.2008"), parseDate("20.12.2008")));

		// Datum mit Zeit (Zeit ignorieren)
		assertEquals(1, FeiertageHelper.getAnzahlArbeitstage(DateHelper
			.newDate(55, 50, 23, 3, 3, 2010), DateHelper.newDate(00, 00,
			00, 3, 3, 2010)));

		// Weihnachten 2011
		assertEquals(21, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("01.12.2011"), parseDate("31.12.2011")));
		assertEquals(0, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("25.12.2011"), parseDate("26.12.2011")));
		assertEquals(1, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("25.12.2011"), parseDate("27.12.2011")));

		// Neujahr 2010
		assertEquals(0, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("01.01.2010"), parseDate("01.01.2010")));
		assertEquals(0, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("01.01.2010"), parseDate("02.01.2010")));
		assertEquals(0, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("01.01.2010"), parseDate("03.01.2010")));
		assertEquals(1, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("01.01.2010"), parseDate("04.01.2010")));

		// Karfreitag/Ostern 2009
		assertEquals(4, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("06.04.2009"), parseDate("10.4.2009")));
		assertEquals(8, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("06.04.2009"), parseDate("17.4.2009")));
		assertEquals(9, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("06.04.2009"), parseDate("20.4.2009")));
		assertEquals(4, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("13.04.2009"), parseDate("17.4.2009")));
		assertEquals(5, FeiertageHelper.getAnzahlArbeitstage(
			parseDate("13.04.2009"), parseDate("20.4.2009")));

		// IllegalArgumentException
		try {
			assertNull(
				"DatumVon grösser DatumBis. IllegalArgumentException wanted",
				FeiertageHelper.getAnzahlArbeitstage(parseDate(DIENSTAG),
					parseDate(MONTAG)));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}

		try {
			assertNull("null-argument. IllegalArgumentException wanted",
				FeiertageHelper.getAnzahlArbeitstage(null, null));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}

		try {
			assertNull("null-argument. IllegalArgumentException wanted",
				FeiertageHelper.getAnzahlArbeitstage(null, new Date()));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}

		try {
			assertNull("null-argument. IllegalArgumentException wanted",
				FeiertageHelper.getAnzahlArbeitstage(new Date(), null));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}

	}

	public void testPreviousWorkingDayWithSperrTagen() {

		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 1), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 2), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 3), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 4), parseDate(DONNERSTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 5), parseDate(MITTWOCH));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 6), parseDate(DIENSTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(MONTAG_NEXT), 7), parseDate(MONTAG));

		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate("21.04.2009"), 20), parseDate("01.04.2009")); // keine
		// Feiertage
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate("23.04.2009"), 10), parseDate("09.04.2009")); // �ber
		// Ostern

		// Zero-tests
		assertEquals(FeiertageHelper.getPreviousWorkingDate(parseDate(MONTAG),
			0), parseDate(MONTAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(parseDate(FREITAG),
			0), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(parseDate(SAMSTAG),
			0), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(parseDate(SONNTAG),
			0), parseDate(FREITAG));
		assertEquals(FeiertageHelper.getPreviousWorkingDate(
			parseDate(OSTERMONTAG_2009), 0), parseDate("09.04.2009"));

		// IllegalArgumentException
		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.getPreviousWorkingDate(null, 0));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}
		try {
			assertNull("IllegalArgumentException wanted", FeiertageHelper
				.getPreviousWorkingDate(new Date(), -1));
		} catch (IllegalArgumentException e) {
			// do nothing. Exception Wanted.
		}
	}

	public void testPrintOutFeiertage() {

		assertTrue("Keine Feiertage vorhanden", !FeiertageHelper
			.getFeiertage_CH(TEST_YEAR_1).isEmpty());
		for (Feiertag_CH f : FeiertageHelper.getFeiertage_CH(TEST_YEAR_1)) {
			assertNotNull(f);
			System.out.println(f.getFeiertag() + ": " + getFormattedString(f));
		}
	}

	public void testAddWorkingDays() {
		Date d = parseDate("1.4.2010");
		assertEquals(parseDate("8.4.2010"), FeiertageHelper
			.addWorkingDays(d, 3));
		assertEquals(parseDate("1.4.2010"), FeiertageHelper
			.addWorkingDays(d, 0));
		d = parseDate("2.4.2010");
		assertEquals(parseDate("6.4.2010"), FeiertageHelper
			.addWorkingDays(d, 0));
		assertEquals(parseDate("7.4.2010"), FeiertageHelper
			.addWorkingDays(d, 1));

		assertEquals(FeiertageHelper.addWorkingDays(parseDate("2.4.2010"), 0),
			FeiertageHelper.addWorkingDays(parseDate("1.4.2010"), 1));

		assertEquals(parseDate("6.4.2010"), FeiertageHelper.addWorkingDays(
			parseDate("2.4.2010"), false, 1));
		assertEquals(parseDate("7.4.2010"), FeiertageHelper.addWorkingDays(
			parseDate("2.4.2010"), true, 1));

		try {
			FeiertageHelper.addWorkingDays(null, 0);
		} catch (IllegalArgumentException x) {
			// expected.
		}
		try {
			FeiertageHelper.addWorkingDays(d, -1);
		} catch (IllegalArgumentException x) {
			// expected.
		}
	}

	public void testAddWorkingDaysWithSperrliste() {
		List<Date> l = new ArrayList<Date>();
		l.add(parseDate("8.4.2010"));
		Date d = parseDate("1.4.2010");
		assertEquals(parseDate("9.4.2010"), FeiertageHelper.addWorkingDays(d,
			3, l));
		assertEquals(parseDate("1.4.2010"), FeiertageHelper.addWorkingDays(d,
			0, l));
		try {
			FeiertageHelper.addWorkingDays(null, 0, l);
		} catch (IllegalArgumentException x) {
			// expected.
		}
		try {
			FeiertageHelper.addWorkingDays(d, -1, l);
		} catch (IllegalArgumentException x) {
			// expected.
		}
		try {
			FeiertageHelper.addWorkingDays(d, 0, null);
		} catch (IllegalArgumentException x) {
			// expected.
		}
	}

	public void testPerformance() {
		Date start = new Date();
		for (int i = 1500; i < 10000; i++) {
			FeiertageHelper.getFeiertage_CH(i);
		}
		Date end = new Date();
		System.out
			.println("getFeiertage(): millis to run from year 1500 to 9999: "
				+ (end.getTime() - start.getTime()));

		start = new Date();
		Date von = parseDate("01.01.2010");
		Date bis = parseDate("31.12.2012");
		FeiertageHelper.getAnzahlArbeitstage(von, bis);
		end = new Date();
		System.out.println("getAnzahlArbeitstage(): millis to run from  "
			+ DateHelper.getFormattedString(von) + " to "
			+ DateHelper.getFormattedString(bis) + ": "
			+ (end.getTime() - start.getTime()));
	}

	private Date parseDate(String aDateString) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		formatter.setLenient(false);
		try {
			return formatter.parse(aDateString);
		} catch (ParseException e) {
			fail(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
