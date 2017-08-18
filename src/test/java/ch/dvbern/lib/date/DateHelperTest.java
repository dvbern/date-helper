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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author MUSE
 */
public class DateHelperTest {

	@Test
	public void testIsBetween() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = DateHelper.addDays(date2, 1);

		Assert.assertFalse("Davor", DateHelper.isBetween(date1, date2, date3));
		Assert.assertFalse("Danach", DateHelper.isBetween(date3, date1, date2));
		Assert.assertTrue("Innerhalb", DateHelper.isBetween(date2, date1, date3));
		Assert.assertTrue("Am Anfang", DateHelper.isBetween(date1, date1, date3));
		Assert.assertTrue("Am Ende", DateHelper.isBetween(date3, date1, date3));
		Assert.assertTrue("Gleicher Tag", DateHelper.isBetween(date3, date3, date3));
	}

	@Test
	public void testIsDateBetween() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = DateHelper.addDays(date2, 1);
		Date date4 = new Date(date1.getTime() + 322);
		Date date5 = new Date(date3.getTime() + 221);

		Assert.assertFalse("Davor", DateHelper.isDateBetween(date1, date2, date3));
		Assert.assertFalse("Danach", DateHelper.isDateBetween(date3, date1, date2));
		Assert.assertTrue("Innerhalb", DateHelper.isDateBetween(date2, date1, date3));
		Assert.assertTrue("Am Anfang", DateHelper.isDateBetween(date1, date1, date3));
		Assert.assertTrue("Am Anfang", DateHelper.isDateBetween(date1, date4, date3));
		Assert.assertTrue("Am Ende", DateHelper.isDateBetween(date3, date1, date3));
		Assert.assertTrue("Am Ende", DateHelper.isDateBetween(date3, date1, date5));
		Assert.assertTrue("Gleicher Tag", DateHelper.isDateBetween(date3, date3, date3));
		Assert.assertTrue("Gleicher Tag", DateHelper.isDateBetween(date3, date3, date5));
	}

	@Test
	public void testIsLessOrEqual() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = new Date(date1.getTime() + 1);

		Assert.assertTrue("Davor", DateHelper.isLessOrEqual(date1, date2));
		Assert.assertTrue("Davor", DateHelper.isLessOrEqual(date1, date3));

		Assert.assertFalse("Danach", DateHelper.isLessOrEqual(date2, date1));
		Assert.assertFalse("Danach", DateHelper.isLessOrEqual(date3, date1));

		Assert.assertTrue("Gleich", DateHelper.isLessOrEqual(date1, date1));
	}

	@Test
	public void testIsDateLessOrEqual() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = new Date(date1.getTime() + 1);

		Assert.assertTrue("Davor", DateHelper.isDateLessOrEqual(date1, date2));

		Assert.assertFalse("Danach", DateHelper.isDateLessOrEqual(date2, date1));

		Assert.assertTrue("Gleich", DateHelper.isDateLessOrEqual(date1, date1));
		Assert.assertTrue("Gleich", DateHelper.isDateLessOrEqual(date1, date3));
	}

	@Test
	public void isDateEqualWithTime() {
		Date date1 = new Date();
		Date date2 = date1;
		Date date3 = new Date(date1.getTime() + 1);

		Assert.assertTrue("Danach", DateHelper.isDateLessOrEqual(date1, date3));
		Assert.assertFalse("Danach", DateHelper.isDateEqualWithTime(date1, date3));
		Assert.assertTrue("Gleich", DateHelper.isDateEqualWithTime(date1, date2));

	}

	@Test
	public void testEqual() {

		Date basis = DateHelper.newDate(24, 9, 2010);
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 10, 2010), DateHelper.addMonths(basis, 1)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 8, 2010), DateHelper.addMonths(basis, -1)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 9, 2011), DateHelper.addMonths(basis, 12)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 9, 2009), DateHelper.addMonths(basis, -12)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 10, 2011), DateHelper.addMonths(basis, 13)));
		Assert.assertFalse(DateHelper.isDateEqual(DateHelper.newDate(31, 12, 9999), null));
		Assert.assertFalse(DateHelper.isDateEqual(null, DateHelper.newDate(31, 12, 9999)));
		Assert.assertTrue(DateHelper.isDateEqual(null, null));
	}

	@Test
	public void testIsMoreOrEqual() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = new Date(date1.getTime() + 1);

		Assert.assertEquals("Davor", false, DateHelper.isMoreOrEqual(date1, date2));
		Assert.assertEquals("Davor", false, DateHelper.isMoreOrEqual(date1, date3));

		Assert.assertEquals("Danach", true, DateHelper.isMoreOrEqual(date2, date1));
		Assert.assertEquals("Danach", true, DateHelper.isMoreOrEqual(date3, date1));

		Assert.assertEquals("Gleich", true, DateHelper.isMoreOrEqual(date1, date1));
	}

	@Test
	public void testIsDateMoreOrEqual() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = new Date(date1.getTime() + 1);

		Assert.assertEquals("Davor", false, DateHelper.isDateMoreOrEqual(date1, date2));

		Assert.assertEquals("Danach", true, DateHelper.isDateMoreOrEqual(date2, date1));

		Assert.assertEquals("Gleich", true, DateHelper.isDateMoreOrEqual(date1, date1));
		Assert.assertEquals("Gleich", true, DateHelper.isDateMoreOrEqual(date1, date3));
	}

	@Test
	public void testIsLess() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = new Date(date1.getTime() + 1);

		Assert.assertEquals("Davor", true, DateHelper.isLess(date1, date2));
		Assert.assertEquals("Davor", true, DateHelper.isLess(date1, date3));

		Assert.assertEquals("Danach", false, DateHelper.isLess(date2, date1));
		Assert.assertEquals("Danach", false, DateHelper.isLess(date3, date1));

		Assert.assertEquals("Gleich", false, DateHelper.isLess(date1, date1));
	}

	@Test
	public void testIsDateLess() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = new Date(date1.getTime() + 1);

		Assert.assertEquals("Davor", true, DateHelper.isDateLess(date1, date2));

		Assert.assertEquals("Danach", false, DateHelper.isDateLess(date2, date1));

		Assert.assertEquals("Gleich", false, DateHelper.isDateLess(date1, date1));
		Assert.assertEquals("Gleich", false, DateHelper.isDateLess(date1, date3));
	}

	@Test
	public void testIsMore() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = new Date(date1.getTime() + 1);

		Assert.assertEquals("Davor", false, DateHelper.isMore(date1, date2));
		Assert.assertEquals("Davor", false, DateHelper.isMore(date1, date3));

		Assert.assertEquals("Danach", true, DateHelper.isMore(date2, date1));
		Assert.assertEquals("Danach", true, DateHelper.isMore(date3, date1));

		Assert.assertEquals("Gleich", false, DateHelper.isMore(date1, date1));
	}

	@Test
	public void testIsDateMore() {

		Date date1 = new Date();
		Date date2 = DateHelper.addDays(date1, 1);
		Date date3 = new Date(date1.getTime() + 1);

		Assert.assertEquals("Davor", false, DateHelper.isDateMore(date1, date2));

		Assert.assertEquals("Danach", true, DateHelper.isDateMore(date2, date1));

		Assert.assertEquals("Gleich", false, DateHelper.isDateMore(date1, date1));
		Assert.assertEquals("Gleich", false, DateHelper.isDateMore(date1, date3));
	}

	@Test
	public void testCurrentDate() {

		Date date = DateHelper.currentDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.clear();
		calendar.setTime(date);

		Assert.assertEquals("Hour", 0, calendar.get(Calendar.HOUR));
		Assert.assertEquals("Minute", 0, calendar.get(Calendar.MINUTE));
		Assert.assertEquals("Second", 0, calendar.get(Calendar.SECOND));
		Assert.assertEquals("Millisecond", 0, calendar.get(Calendar.MILLISECOND));
	}

	@Test
	public void testGetMax() {

		Date date1 = DateHelper.currentDate();
		Date date2 = DateHelper.addDays(date1, 1);

		Assert.assertEquals("Falsches Datum", date2, DateHelper.getMax(date1, date2));
		Assert.assertEquals("Falsches Datum", date1, DateHelper.getMax(date1, date1));
	}

	@Test
	public void testGetWeekOfYear() {

		Date date = DateHelper.newDate(1, 1, 2005);
		Assert.assertEquals("Falsche Woche", 53, DateHelper.getWeekOfYear(date));

		date = DateHelper.newDate(1, 1, 2004);
		Assert.assertEquals("Falsche Woche", 1, DateHelper.getWeekOfYear(date));

		date = DateHelper.newDate(31, 12, 2004);
		Assert.assertEquals("Falsche Woche", 53, DateHelper.getWeekOfYear(date));

		date = DateHelper.newDate(31, 12, 2003);
		Assert.assertEquals("Falsche Woche", 1, DateHelper.getWeekOfYear(date));
	}

	@Test
	public void testGetYearOfWeek() {

		Date date = DateHelper.newDate(1, 1, 2005);
		Assert.assertEquals("Falsches Jahr", 2004, DateHelper.getYearOfWeek(date));

		date = DateHelper.newDate(1, 1, 2004);
		Assert.assertEquals("Falsches Jahr", 2004, DateHelper.getYearOfWeek(date));

		date = DateHelper.newDate(31, 12, 2004);
		Assert.assertEquals("Falsches Jahr", 2004, DateHelper.getYearOfWeek(date));

		date = DateHelper.newDate(31, 12, 2003);
		Assert.assertEquals("Falsches Jahr", 2004, DateHelper.getYearOfWeek(date));
	}

	@Test
	public void testGetFirstDayOfWeek() {

		Date input = DateHelper.newDate(7, 2, 2005);
		Date soll = DateHelper.newDate(7, 2, 2005);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfWeek(input));

		input = DateHelper.newDate(8, 2, 2005);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfWeek(input));

		input = DateHelper.newDate(13, 2, 2005);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfWeek(input));

		input = DateHelper.newDate(6, 2, 2005);
		soll = DateHelper.newDate(31, 1, 2005);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfWeek(input));
	}

	@Test
	public void testGetLastDayOfWeek() {

		Date input = DateHelper.newDate(13, 2, 2005);
		Date soll = DateHelper.newDate(13, 2, 2005);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfWeek(input));

		input = DateHelper.newDate(12, 2, 2005);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfWeek(input));

		input = DateHelper.newDate(7, 2, 2005);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfWeek(input));

		input = DateHelper.newDate(14, 2, 2005);
		soll = DateHelper.newDate(20, 2, 2005);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfWeek(input));
	}

	@Test
	public void testGetFirstDayOfMonth() {

		Date input = DateHelper.newDate(1, 2, 2008);
		Date soll = DateHelper.newDate(1, 2, 2008);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfMonth(input));

		input = DateHelper.newDate(8, 2, 2008);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfMonth(input));

		input = DateHelper.newDate(29, 2, 2008);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfMonth(input));

		input = DateHelper.newDate(6, 12, 2010);
		soll = DateHelper.newDate(1, 12, 2010);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfMonth(input));
	}

	@Test
	public void testGetLastDayOfMonth() {

		Date input = DateHelper.newDate(7, 2, 2008);
		Date soll = DateHelper.newDate(29, 2, 2008);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfMonth(input));

		input = DateHelper.newDate(1, 2, 2008);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfMonth(input));

		input = DateHelper.newDate(10, 2, 2008);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfMonth(input));

		input = DateHelper.newDate(6, 12, 2010);
		soll = DateHelper.newDate(31, 12, 2010);
		Assert.assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfMonth(input));
	}

	@Test
	public void testGetAnzahlTage() {

		Date von = DateHelper.newDate(1, 1, 2005);
		Date bis = DateHelper.newDate(31, 12, 2005);
		Assert.assertEquals("Anzahl Tage", 365, DateHelper.getAnzahlTage(von, bis, true));

		von = DateHelper.newDate(1, 11, 2005);
		bis = DateHelper.newDate(31, 10, 2006);
		Assert.assertEquals("Anzahl Tage", 365, DateHelper.getAnzahlTage(von, bis, true));

		von = DateHelper.newDate(1, 1, 2004);
		bis = DateHelper.newDate(31, 12, 2004);
		Assert.assertEquals("Anzahl Tage", 366, DateHelper.getAnzahlTage(von, bis, true));

		von = DateHelper.newDate(1, 11, 2003);
		bis = DateHelper.newDate(31, 10, 2004);
		Assert.assertEquals("Anzahl Tage", 366, DateHelper.getAnzahlTage(von, bis, true));

		von = DateHelper.newDate(1, 5, 2005);
		bis = DateHelper.newDate(31, 5, 2005);
		Assert.assertEquals("Anzahl Tage", 31, DateHelper.getAnzahlTage(von, bis, true));

		von = DateHelper.newDate(20, 4, 2005);
		bis = DateHelper.newDate(5, 5, 2005);
		Assert.assertEquals("Anzahl Tage", 16, DateHelper.getAnzahlTage(von, bis, true));

		von = DateHelper.newDate(1, 4, 2005);
		bis = DateHelper.newDate(10, 4, 2005);
		Assert.assertEquals("Anzahl Tage", 10, DateHelper.getAnzahlTage(von, bis, true));
		Assert.assertEquals("Anzahl Tage", 9, DateHelper.getAnzahlTage(von, bis, false));
	}

	@Test
	public void testIsSchalttagBetween() {

		Date von = DateHelper.newDate(1, 1, 2005);
		Date bis = DateHelper.newDate(31, 12, 2005);
		Assert.assertEquals("Schalttag", false, DateHelper.isSchalttagBetween(von, bis));

		von = DateHelper.newDate(1, 1, 2004);
		bis = DateHelper.newDate(31, 12, 2004);
		Assert.assertEquals("Schalttag", true, DateHelper.isSchalttagBetween(von, bis));

		von = DateHelper.newDate(1, 3, 2004);
		bis = DateHelper.newDate(31, 12, 2005);
		Assert.assertEquals("Schalttag", false, DateHelper.isSchalttagBetween(von, bis));

		von = DateHelper.newDate(20, 2, 2004);
		bis = DateHelper.newDate(1, 3, 2004);
		Assert.assertEquals("Schalttag", true, DateHelper.isSchalttagBetween(von, bis));
	}

	@Test
	public void testAlterAm() {

		Assert.assertEquals(0, DateHelper.getAlterAm(new Date(), new Date()));

		Date gebdat = DateHelper.newDate(15, 8, 2011);
		Assert.assertEquals(-1, DateHelper.getAlterAm(gebdat, DateHelper.newDate(14, 8, 2011)));
		Assert.assertEquals(0, DateHelper.getAlterAm(gebdat, DateHelper.newDate(15, 8, 2011)));
		Assert.assertEquals(0, DateHelper.getAlterAm(gebdat, DateHelper.newDate(14, 8, 2012)));
		Assert.assertEquals(1, DateHelper.getAlterAm(gebdat, DateHelper.newDate(15, 8, 2012)));

	}

	@Test
	public void testZeitraumLuecken() {

		List zeitraeume;
		List luecken;

		zeitraeume = new ArrayList();
		zeitraeume.add(new Zeitraum(DateHelper.newDate(1, 1, 2002), DateHelper.newDate(31, 1, 2002)));
		zeitraeume.add(new Zeitraum(DateHelper.newDate(1, 2, 2002), DateHelper.newDate(28, 2, 2002)));
		zeitraeume.add(new Zeitraum(DateHelper.newDate(1, 3, 2002), DateHelper.newDate(31, 3, 2002)));
		luecken = DateHelper.getZeitraumLuecken(zeitraeume);
		Assert.assertEquals("Keine Kücke", 0, luecken.size());

		zeitraeume = new ArrayList();
		zeitraeume.add(new Zeitraum(DateHelper.newDate(1, 1, 2002), DateHelper.newDate(31, 1, 2002)));
		zeitraeume.add(new Zeitraum(DateHelper.newDate(1, 3, 2002), DateHelper.newDate(31, 3, 2002)));
		zeitraeume.add(new Zeitraum(DateHelper.newDate(1, 5, 2002), DateHelper.newDate(31, 5, 2002)));
		luecken = DateHelper.getZeitraumLuecken(zeitraeume);
		Assert.assertEquals("Lücken vorhanden", 2, luecken.size());

		Zeitraum luecke1 = (Zeitraum) luecken.get(0);
		Assert.assertEquals("Lücke von", DateHelper.newDate(1, 2, 2002), luecke1.getVon());
		Assert.assertEquals("Lücke bis", DateHelper.newDate(28, 2, 2002), luecke1.getBis());

		Zeitraum luecke2 = (Zeitraum) luecken.get(1);
		Assert.assertEquals("Lücke von", DateHelper.newDate(1, 4, 2002), luecke2.getVon());
		Assert.assertEquals("Lücke bis", DateHelper.newDate(30, 4, 2002), luecke2.getBis());
	}

	@Test
	public void testAddMonth() {

		Date basis = DateHelper.newDate(27, 2, 2011);
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 3, 2011), DateHelper.addMonths(basis, 1)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 1, 2011), DateHelper.addMonths(basis, -1)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 4, 2011), DateHelper.addMonths(basis, 2)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 12, 2010), DateHelper.addMonths(basis, -2)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 2, 2012), DateHelper.addMonths(basis, 12)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 2, 2010), DateHelper.addMonths(basis, -12)));

		basis = DateHelper.newDate(30, 4, 2011); // Ende Monat!
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 5, 2011), DateHelper.addMonths(basis, 1))); // bad
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 3, 2011), DateHelper.addMonths(basis, -1))); // bad
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 6, 2011), DateHelper.addMonths(basis, 2)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -2)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2012), DateHelper.addMonths(basis, 12)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2010), DateHelper.addMonths(basis, -12)));

		basis = DateHelper.newDate(31, 3, 2011); // Ende Monat!
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2011), DateHelper.addMonths(basis, 1)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -1)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 5, 2011), DateHelper.addMonths(basis, 2)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 1, 2011), DateHelper.addMonths(basis, -2)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2012), DateHelper.addMonths(basis, 12)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2010), DateHelper.addMonths(basis, -12)));

		basis = DateHelper.newDate(28, 2, 2011); // Ende Monat! --> addMonth
		// standard
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 3, 2011), DateHelper.addMonths(basis, 1))); // bad
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 1, 2011), DateHelper.addMonths(basis, -1))); // bad
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 4, 2011), DateHelper.addMonths(basis, 2))); // bad
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 12, 2010), DateHelper.addMonths(basis, -2))); // bad
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2012), DateHelper.addMonths(basis, 12))); // bad
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2010), DateHelper.addMonths(basis, -12)));

		basis = DateHelper.newDate(28, 2, 2011); // Ende Monat! --> addMonth
		// withEndMonthcheck
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2011), DateHelper.addMonths(basis, 1, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 1, 2011), DateHelper.addMonths(basis, -1, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2011), DateHelper.addMonths(basis, 2, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 12, 2010), DateHelper.addMonths(basis, -2, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(29, 2, 2012), DateHelper.addMonths(basis, 12, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2010), DateHelper.addMonths(basis, -12, true)));

		basis = DateHelper.newDate(30, 4, 2011); // Ende Monat! --> addMonth
		// withEndMonthcheck
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 5, 2011), DateHelper.addMonths(basis, 1, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2011), DateHelper.addMonths(basis, -1, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 6, 2011), DateHelper.addMonths(basis, 2, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -2, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2012), DateHelper.addMonths(basis, 12, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2010), DateHelper.addMonths(basis, -12, true)));

		basis = DateHelper.newDate(31, 3, 2011); // Ende Monat! --> addMonth
		// withEndMonthcheck
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2011), DateHelper.addMonths(basis, 1, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -1, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 5, 2011), DateHelper.addMonths(basis, 2, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 1, 2011), DateHelper.addMonths(basis, -2, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2012), DateHelper.addMonths(basis, 12, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2010), DateHelper.addMonths(basis, -12, true)));

		basis = DateHelper.newDate(31, 3, 2011); // Ende Monat! --> addMonth
		// withEndMonthcheck=false
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2011), DateHelper.addMonths(basis, 1, false)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -1, false)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 5, 2011), DateHelper.addMonths(basis, 2, false)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 1, 2011), DateHelper.addMonths(basis, -2, false)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2012), DateHelper.addMonths(basis, 12, false)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2010), DateHelper.addMonths(basis, -12, false)));

		basis = DateHelper.newDate(27, 2, 2011);
		Assert.assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 3, 2011), DateHelper.addMonths(basis, 1, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 1, 2011), DateHelper.addMonths(basis, -1, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 4, 2011), DateHelper.addMonths(basis, 2, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 12, 2010), DateHelper.addMonths(basis, -2, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 2, 2012), DateHelper.addMonths(basis, 12, true)));
		Assert.assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 2, 2010), DateHelper.addMonths(basis, -12, true)));
	}

	@Test
	public void testDatumVonFolgequartal() {

		Assert.assertEquals(DateHelper.newDate(1, 4, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(1, 1, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 4, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(2, 2, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 4, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(3, 3, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 7, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(4, 4, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 7, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(5, 5, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 7, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(6, 6, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 10, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(7, 7, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 10, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(8, 8, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 10, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(9, 9, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 1, 2012), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(10, 10, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 1, 2012), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(11, 11, 2011)));
		Assert.assertEquals(DateHelper.newDate(1, 1, 2012), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(12, 12, 2011)));
	}

	@Test
	public void testDatumBisFolgequartal() {

		Assert.assertEquals(DateHelper.newDate(30, 6, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(1, 1, 2011)));
		Assert.assertEquals(DateHelper.newDate(30, 6, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(2, 2, 2011)));
		Assert.assertEquals(DateHelper.newDate(30, 6, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(3, 3, 2011)));
		Assert.assertEquals(DateHelper.newDate(30, 9, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(4, 4, 2011)));
		Assert.assertEquals(DateHelper.newDate(30, 9, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(5, 5, 2011)));
		Assert.assertEquals(DateHelper.newDate(30, 9, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(6, 6, 2011)));
		Assert.assertEquals(DateHelper.newDate(31, 12, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(7, 7, 2011)));
		Assert.assertEquals(DateHelper.newDate(31, 12, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(8, 8, 2011)));
		Assert.assertEquals(DateHelper.newDate(31, 12, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(9, 9, 2011)));
		Assert.assertEquals(DateHelper.newDate(31, 3, 2012), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(10, 10, 2011)));
		Assert.assertEquals(DateHelper.newDate(31, 3, 2012), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(11, 11, 2011)));
		Assert.assertEquals(DateHelper.newDate(31, 3, 2012), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(12, 12, 2011)));
	}

	@Test
	public void testNewDateFromExactTime() {

		Calendar calendar = Calendar.getInstance(new Locale("de", "CH"));
		calendar.setLenient(false);
		calendar.clear();

		Date min = DateHelper.newDateForExactTime(DateHelper.newDate(1, 1, 1900), 0, 0, 0, 0);
		calendar.set(1900, 0, 1, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Assert.assertEquals(calendar.getTime(), min);

		Date max = DateHelper.newDateForExactTime(DateHelper.newDate(31, 12, 9999), 23, 59, 59, 999);
		calendar.clear();
		calendar.set(9999, 11, 31, 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Assert.assertEquals(calendar.getTime(), max);

		Date now = new Date();
		Date current = DateHelper.newDateForExactTime(now, DateHelper.getHour(now), DateHelper.getMinute(now), DateHelper.getSecond(now),
			DateHelper.getMillisecond(now));
		Assert.assertEquals(now, current);
	}

}
