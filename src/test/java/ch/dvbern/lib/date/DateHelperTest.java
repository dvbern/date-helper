/*
 * Copyright 2017 DV Bern AG
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

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author MUSE
 * 
 */
public class DateHelperTest extends TestCase {

    public void testIsBetween() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = DateHelper.addDays(date2, 1);

	assertEquals("Davor", false, DateHelper.isBetween(date1, date2, date3));
	assertEquals("Danach", false, DateHelper.isBetween(date3, date1, date2));
	assertEquals("Innerhalb", true, DateHelper.isBetween(date2, date1, date3));
	assertEquals("Am Anfang", true, DateHelper.isBetween(date1, date1, date3));
	assertEquals("Am Ende", true, DateHelper.isBetween(date3, date1, date3));
	assertEquals("Gleicher Tag", true, DateHelper.isBetween(date3, date3, date3));
    }

    public void testIsDateBetween() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = DateHelper.addDays(date2, 1);
	Date date4 = new Date(date1.getTime() + 322);
	Date date5 = new Date(date3.getTime() + 221);

	assertEquals("Davor", false, DateHelper.isDateBetween(date1, date2, date3));
	assertEquals("Danach", false, DateHelper.isDateBetween(date3, date1, date2));
	assertEquals("Innerhalb", true, DateHelper.isDateBetween(date2, date1, date3));
	assertEquals("Am Anfang", true, DateHelper.isDateBetween(date1, date1, date3));
	assertEquals("Am Anfang", true, DateHelper.isDateBetween(date1, date4, date3));
	assertEquals("Am Ende", true, DateHelper.isDateBetween(date3, date1, date3));
	assertEquals("Am Ende", true, DateHelper.isDateBetween(date3, date1, date5));
	assertEquals("Gleicher Tag", true, DateHelper.isDateBetween(date3, date3, date3));
	assertEquals("Gleicher Tag", true, DateHelper.isDateBetween(date3, date3, date5));
    }

    public void testIsLessOrEqual() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = new Date(date1.getTime() + 1);

	assertEquals("Davor", true, DateHelper.isLessOrEqual(date1, date2));
	assertEquals("Davor", true, DateHelper.isLessOrEqual(date1, date3));

	assertEquals("Danach", false, DateHelper.isLessOrEqual(date2, date1));
	assertEquals("Danach", false, DateHelper.isLessOrEqual(date3, date1));

	assertEquals("Gleich", true, DateHelper.isLessOrEqual(date1, date1));
    }

    @Test
    public void testIsDateLessOrEqual() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = new Date(date1.getTime() + 1);

	assertEquals("Davor", true, DateHelper.isDateLessOrEqual(date1, date2));

	assertEquals("Danach", false, DateHelper.isDateLessOrEqual(date2, date1));

	assertEquals("Gleich", true, DateHelper.isDateLessOrEqual(date1, date1));
	assertEquals("Gleich", true, DateHelper.isDateLessOrEqual(date1, date3));
    }

    @Test
    public void isDateEqualWithTime() {
	Date date1 = new Date();
	Date date2 = date1;
	Date date3 = new Date(date1.getTime() + 1);

	assertEquals("Danach", false, DateHelper.isDateLessOrEqual(date1, date3));

	assertEquals("Gleich", true, DateHelper.isDateEqualWithTime(date1, date2));

    }

    @Test
    public void testEqual() {

	Date basis = DateHelper.newDate(24, 9, 2010);
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 10, 2010), DateHelper.addMonths(basis, 1)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 8, 2010), DateHelper.addMonths(basis, -1)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 9, 2011), DateHelper.addMonths(basis, 12)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 9, 2009), DateHelper.addMonths(basis, -12)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(24, 10, 2011), DateHelper.addMonths(basis, 13)));
	assertFalse(DateHelper.isDateEqual(DateHelper.newDate(31, 12, 9999), null));
	assertFalse(DateHelper.isDateEqual(null, DateHelper.newDate(31, 12, 9999)));
	assertTrue(DateHelper.isDateEqual(null, null));
    }

    public void testIsMoreOrEqual() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = new Date(date1.getTime() + 1);

	assertEquals("Davor", false, DateHelper.isMoreOrEqual(date1, date2));
	assertEquals("Davor", false, DateHelper.isMoreOrEqual(date1, date3));

	assertEquals("Danach", true, DateHelper.isMoreOrEqual(date2, date1));
	assertEquals("Danach", true, DateHelper.isMoreOrEqual(date3, date1));

	assertEquals("Gleich", true, DateHelper.isMoreOrEqual(date1, date1));
    }

    public void testIsDateMoreOrEqual() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = new Date(date1.getTime() + 1);

	assertEquals("Davor", false, DateHelper.isDateMoreOrEqual(date1, date2));

	assertEquals("Danach", true, DateHelper.isDateMoreOrEqual(date2, date1));

	assertEquals("Gleich", true, DateHelper.isDateMoreOrEqual(date1, date1));
	assertEquals("Gleich", true, DateHelper.isDateMoreOrEqual(date1, date3));
    }

    public void testIsLess() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = new Date(date1.getTime() + 1);

	assertEquals("Davor", true, DateHelper.isLess(date1, date2));
	assertEquals("Davor", true, DateHelper.isLess(date1, date3));

	assertEquals("Danach", false, DateHelper.isLess(date2, date1));
	assertEquals("Danach", false, DateHelper.isLess(date3, date1));

	assertEquals("Gleich", false, DateHelper.isLess(date1, date1));
    }

    public void testIsDateLess() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = new Date(date1.getTime() + 1);

	assertEquals("Davor", true, DateHelper.isDateLess(date1, date2));

	assertEquals("Danach", false, DateHelper.isDateLess(date2, date1));

	assertEquals("Gleich", false, DateHelper.isDateLess(date1, date1));
	assertEquals("Gleich", false, DateHelper.isDateLess(date1, date3));
    }

    @Test
    public void testIsMore() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = new Date(date1.getTime() + 1);

	assertEquals("Davor", false, DateHelper.isMore(date1, date2));
	assertEquals("Davor", false, DateHelper.isMore(date1, date3));

	assertEquals("Danach", true, DateHelper.isMore(date2, date1));
	assertEquals("Danach", true, DateHelper.isMore(date3, date1));

	assertEquals("Gleich", false, DateHelper.isMore(date1, date1));
    }

    @Test
    public void testIsDateMore() {

	Date date1 = new Date();
	Date date2 = DateHelper.addDays(date1, 1);
	Date date3 = new Date(date1.getTime() + 1);

	assertEquals("Davor", false, DateHelper.isDateMore(date1, date2));

	assertEquals("Danach", true, DateHelper.isDateMore(date2, date1));

	assertEquals("Gleich", false, DateHelper.isDateMore(date1, date1));
	assertEquals("Gleich", false, DateHelper.isDateMore(date1, date3));
    }

    @Test
    public void testCurrentDate() {

	Date date = DateHelper.currentDate();
	Calendar calendar = Calendar.getInstance();
	calendar.setLenient(false);
	calendar.clear();
	calendar.setTime(date);

	assertEquals("Hour", 0, calendar.get(Calendar.HOUR));
	assertEquals("Minute", 0, calendar.get(Calendar.MINUTE));
	assertEquals("Second", 0, calendar.get(Calendar.SECOND));
	assertEquals("Millisecond", 0, calendar.get(Calendar.MILLISECOND));
    }

    @Test
    public void testGetMax() {

	Date date1 = DateHelper.currentDate();
	Date date2 = DateHelper.addDays(date1, 1);

	assertEquals("Falsches Datum", date2, DateHelper.getMax(date1, date2));
	assertEquals("Falsches Datum", date1, DateHelper.getMax(date1, date1));
    }

    @Test
    public void testGetWeekOfYear() {

	Date date = DateHelper.newDate(1, 1, 2005);
	assertEquals("Falsche Woche", 53, DateHelper.getWeekOfYear(date));

	date = DateHelper.newDate(1, 1, 2004);
	assertEquals("Falsche Woche", 1, DateHelper.getWeekOfYear(date));

	date = DateHelper.newDate(31, 12, 2004);
	assertEquals("Falsche Woche", 53, DateHelper.getWeekOfYear(date));

	date = DateHelper.newDate(31, 12, 2003);
	assertEquals("Falsche Woche", 1, DateHelper.getWeekOfYear(date));
    }

    @Test
    public void testGetYearOfWeek() {

	Date date = DateHelper.newDate(1, 1, 2005);
	assertEquals("Falsches Jahr", 2004, DateHelper.getYearOfWeek(date));

	date = DateHelper.newDate(1, 1, 2004);
	assertEquals("Falsches Jahr", 2004, DateHelper.getYearOfWeek(date));

	date = DateHelper.newDate(31, 12, 2004);
	assertEquals("Falsches Jahr", 2004, DateHelper.getYearOfWeek(date));

	date = DateHelper.newDate(31, 12, 2003);
	assertEquals("Falsches Jahr", 2004, DateHelper.getYearOfWeek(date));
    }

    @Test
    public void testGetFirstDayOfWeek() {

	Date input = DateHelper.newDate(7, 2, 2005);
	Date soll = DateHelper.newDate(7, 2, 2005);
	assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfWeek(input));

	input = DateHelper.newDate(8, 2, 2005);
	assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfWeek(input));

	input = DateHelper.newDate(13, 2, 2005);
	assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfWeek(input));

	input = DateHelper.newDate(6, 2, 2005);
	soll = DateHelper.newDate(31, 1, 2005);
	assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfWeek(input));
    }

    @Test
    public void testGetLastDayOfWeek() {

	Date input = DateHelper.newDate(13, 2, 2005);
	Date soll = DateHelper.newDate(13, 2, 2005);
	assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfWeek(input));

	input = DateHelper.newDate(12, 2, 2005);
	assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfWeek(input));

	input = DateHelper.newDate(7, 2, 2005);
	assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfWeek(input));

	input = DateHelper.newDate(14, 2, 2005);
	soll = DateHelper.newDate(20, 2, 2005);
	assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfWeek(input));
    }

    @Test
    public void testGetFirstDayOfMonth() {

	Date input = DateHelper.newDate(1, 2, 2008);
	Date soll = DateHelper.newDate(1, 2, 2008);
	assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfMonth(input));

	input = DateHelper.newDate(8, 2, 2008);
	assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfMonth(input));

	input = DateHelper.newDate(29, 2, 2008);
	assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfMonth(input));

	input = DateHelper.newDate(6, 12, 2010);
	soll = DateHelper.newDate(1, 12, 2010);
	assertEquals("Falsches Datum", soll, DateHelper.getFirstDayOfMonth(input));
    }

    @Test
    public void testGetLastDayOfMonth() {

	Date input = DateHelper.newDate(7, 2, 2008);
	Date soll = DateHelper.newDate(29, 2, 2008);
	assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfMonth(input));

	input = DateHelper.newDate(1, 2, 2008);
	assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfMonth(input));

	input = DateHelper.newDate(10, 2, 2008);
	assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfMonth(input));

	input = DateHelper.newDate(6, 12, 2010);
	soll = DateHelper.newDate(31, 12, 2010);
	assertEquals("Falsches Datum", soll, DateHelper.getLastDayOfMonth(input));
    }

    @Test
    public void testGetAnzahlTage() {

	Date von = DateHelper.newDate(1, 1, 2005);
	Date bis = DateHelper.newDate(31, 12, 2005);
	assertEquals("Anzahl Tage", 365, DateHelper.getAnzahlTage(von, bis, true));

	von = DateHelper.newDate(1, 11, 2005);
	bis = DateHelper.newDate(31, 10, 2006);
	assertEquals("Anzahl Tage", 365, DateHelper.getAnzahlTage(von, bis, true));

	von = DateHelper.newDate(1, 1, 2004);
	bis = DateHelper.newDate(31, 12, 2004);
	assertEquals("Anzahl Tage", 366, DateHelper.getAnzahlTage(von, bis, true));

	von = DateHelper.newDate(1, 11, 2003);
	bis = DateHelper.newDate(31, 10, 2004);
	assertEquals("Anzahl Tage", 366, DateHelper.getAnzahlTage(von, bis, true));

	von = DateHelper.newDate(1, 5, 2005);
	bis = DateHelper.newDate(31, 5, 2005);
	assertEquals("Anzahl Tage", 31, DateHelper.getAnzahlTage(von, bis, true));

	von = DateHelper.newDate(20, 4, 2005);
	bis = DateHelper.newDate(5, 5, 2005);
	assertEquals("Anzahl Tage", 16, DateHelper.getAnzahlTage(von, bis, true));

	von = DateHelper.newDate(1, 4, 2005);
	bis = DateHelper.newDate(10, 4, 2005);
	assertEquals("Anzahl Tage", 10, DateHelper.getAnzahlTage(von, bis, true));
	assertEquals("Anzahl Tage", 9, DateHelper.getAnzahlTage(von, bis, false));
    }

    @Test
    public void testIsSchalttagBetween() {

	Date von = DateHelper.newDate(1, 1, 2005);
	Date bis = DateHelper.newDate(31, 12, 2005);
	assertEquals("Schalttag", false, DateHelper.isSchalttagBetween(von, bis));

	von = DateHelper.newDate(1, 1, 2004);
	bis = DateHelper.newDate(31, 12, 2004);
	assertEquals("Schalttag", true, DateHelper.isSchalttagBetween(von, bis));

	von = DateHelper.newDate(1, 3, 2004);
	bis = DateHelper.newDate(31, 12, 2005);
	assertEquals("Schalttag", false, DateHelper.isSchalttagBetween(von, bis));

	von = DateHelper.newDate(20, 2, 2004);
	bis = DateHelper.newDate(1, 3, 2004);
	assertEquals("Schalttag", true, DateHelper.isSchalttagBetween(von, bis));
    }

    @Test
    public void testAlterAm() {

	assertEquals(0, DateHelper.getAlterAm(new Date(), new Date()));

	Date gebdat = DateHelper.newDate(15, 8, 2011);
	assertEquals(-1, DateHelper.getAlterAm(gebdat, DateHelper.newDate(14, 8, 2011)));
	assertEquals(0, DateHelper.getAlterAm(gebdat, DateHelper.newDate(15, 8, 2011)));
	assertEquals(0, DateHelper.getAlterAm(gebdat, DateHelper.newDate(14, 8, 2012)));
	assertEquals(1, DateHelper.getAlterAm(gebdat, DateHelper.newDate(15, 8, 2012)));

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
	assertEquals("Keine Kücke", 0, luecken.size());

	zeitraeume = new ArrayList();
	zeitraeume.add(new Zeitraum(DateHelper.newDate(1, 1, 2002), DateHelper.newDate(31, 1, 2002)));
	zeitraeume.add(new Zeitraum(DateHelper.newDate(1, 3, 2002), DateHelper.newDate(31, 3, 2002)));
	zeitraeume.add(new Zeitraum(DateHelper.newDate(1, 5, 2002), DateHelper.newDate(31, 5, 2002)));
	luecken = DateHelper.getZeitraumLuecken(zeitraeume);
	assertEquals("Lücken vorhanden", 2, luecken.size());

	Zeitraum luecke1 = (Zeitraum) luecken.get(0);
	assertEquals("Lücke von", DateHelper.newDate(1, 2, 2002), luecke1.getVon());
	assertEquals("Lücke bis", DateHelper.newDate(28, 2, 2002), luecke1.getBis());

	Zeitraum luecke2 = (Zeitraum) luecken.get(1);
	assertEquals("Lücke von", DateHelper.newDate(1, 4, 2002), luecke2.getVon());
	assertEquals("Lücke bis", DateHelper.newDate(30, 4, 2002), luecke2.getBis());
    }

    @Test
    public void testAddMonth() {

	Date basis = DateHelper.newDate(27, 2, 2011);
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 3, 2011), DateHelper.addMonths(basis, 1)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 1, 2011), DateHelper.addMonths(basis, -1)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 4, 2011), DateHelper.addMonths(basis, 2)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 12, 2010), DateHelper.addMonths(basis, -2)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 2, 2012), DateHelper.addMonths(basis, 12)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 2, 2010), DateHelper.addMonths(basis, -12)));

	basis = DateHelper.newDate(30, 4, 2011); // Ende Monat!
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 5, 2011), DateHelper.addMonths(basis, 1))); // bad
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 3, 2011), DateHelper.addMonths(basis, -1))); // bad
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 6, 2011), DateHelper.addMonths(basis, 2)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -2)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2012), DateHelper.addMonths(basis, 12)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2010), DateHelper.addMonths(basis, -12)));

	basis = DateHelper.newDate(31, 3, 2011); // Ende Monat!
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2011), DateHelper.addMonths(basis, 1)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -1)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 5, 2011), DateHelper.addMonths(basis, 2)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 1, 2011), DateHelper.addMonths(basis, -2)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2012), DateHelper.addMonths(basis, 12)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2010), DateHelper.addMonths(basis, -12)));

	basis = DateHelper.newDate(28, 2, 2011); // Ende Monat! --> addMonth
						 // standard
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 3, 2011), DateHelper.addMonths(basis, 1))); // bad
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 1, 2011), DateHelper.addMonths(basis, -1))); // bad
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 4, 2011), DateHelper.addMonths(basis, 2))); // bad
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 12, 2010), DateHelper.addMonths(basis, -2))); // bad
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2012), DateHelper.addMonths(basis, 12))); // bad
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2010), DateHelper.addMonths(basis, -12)));

	basis = DateHelper.newDate(28, 2, 2011); // Ende Monat! --> addMonth
						 // withEndMonthcheck
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2011), DateHelper.addMonths(basis, 1, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 1, 2011), DateHelper.addMonths(basis, -1, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2011), DateHelper.addMonths(basis, 2, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 12, 2010), DateHelper.addMonths(basis, -2, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(29, 2, 2012), DateHelper.addMonths(basis, 12, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2010), DateHelper.addMonths(basis, -12, true)));

	basis = DateHelper.newDate(30, 4, 2011); // Ende Monat! --> addMonth
						 // withEndMonthcheck
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 5, 2011), DateHelper.addMonths(basis, 1, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2011), DateHelper.addMonths(basis, -1, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 6, 2011), DateHelper.addMonths(basis, 2, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -2, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2012), DateHelper.addMonths(basis, 12, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2010), DateHelper.addMonths(basis, -12, true)));

	basis = DateHelper.newDate(31, 3, 2011); // Ende Monat! --> addMonth
						 // withEndMonthcheck
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2011), DateHelper.addMonths(basis, 1, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -1, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 5, 2011), DateHelper.addMonths(basis, 2, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 1, 2011), DateHelper.addMonths(basis, -2, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2012), DateHelper.addMonths(basis, 12, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2010), DateHelper.addMonths(basis, -12, true)));

	basis = DateHelper.newDate(31, 3, 2011); // Ende Monat! --> addMonth
						 // withEndMonthcheck=false
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(30, 4, 2011), DateHelper.addMonths(basis, 1, false)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(28, 2, 2011), DateHelper.addMonths(basis, -1, false)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 5, 2011), DateHelper.addMonths(basis, 2, false)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 1, 2011), DateHelper.addMonths(basis, -2, false)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2012), DateHelper.addMonths(basis, 12, false)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(31, 3, 2010), DateHelper.addMonths(basis, -12, false)));

	basis = DateHelper.newDate(27, 2, 2011);
	assertTrue(DateHelper.isDateEqual(basis, DateHelper.addMonths(basis, 0)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 3, 2011), DateHelper.addMonths(basis, 1, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 1, 2011), DateHelper.addMonths(basis, -1, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 4, 2011), DateHelper.addMonths(basis, 2, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 12, 2010), DateHelper.addMonths(basis, -2, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 2, 2012), DateHelper.addMonths(basis, 12, true)));
	assertTrue(DateHelper.isDateEqual(DateHelper.newDate(27, 2, 2010), DateHelper.addMonths(basis, -12, true)));
    }

    @Test
    public void testDatumVonFolgequartal() {

	assertEquals(DateHelper.newDate(1, 4, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(1, 1, 2011)));
	assertEquals(DateHelper.newDate(1, 4, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(2, 2, 2011)));
	assertEquals(DateHelper.newDate(1, 4, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(3, 3, 2011)));
	assertEquals(DateHelper.newDate(1, 7, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(4, 4, 2011)));
	assertEquals(DateHelper.newDate(1, 7, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(5, 5, 2011)));
	assertEquals(DateHelper.newDate(1, 7, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(6, 6, 2011)));
	assertEquals(DateHelper.newDate(1, 10, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(7, 7, 2011)));
	assertEquals(DateHelper.newDate(1, 10, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(8, 8, 2011)));
	assertEquals(DateHelper.newDate(1, 10, 2011), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(9, 9, 2011)));
	assertEquals(DateHelper.newDate(1, 1, 2012), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(10, 10, 2011)));
	assertEquals(DateHelper.newDate(1, 1, 2012), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(11, 11, 2011)));
	assertEquals(DateHelper.newDate(1, 1, 2012), DateHelper.getDatumVonFolgequartal(DateHelper.newDate(12, 12, 2011)));
    }

    @Test
    public void testDatumBisFolgequartal() {

	assertEquals(DateHelper.newDate(30, 6, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(1, 1, 2011)));
	assertEquals(DateHelper.newDate(30, 6, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(2, 2, 2011)));
	assertEquals(DateHelper.newDate(30, 6, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(3, 3, 2011)));
	assertEquals(DateHelper.newDate(30, 9, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(4, 4, 2011)));
	assertEquals(DateHelper.newDate(30, 9, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(5, 5, 2011)));
	assertEquals(DateHelper.newDate(30, 9, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(6, 6, 2011)));
	assertEquals(DateHelper.newDate(31, 12, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(7, 7, 2011)));
	assertEquals(DateHelper.newDate(31, 12, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(8, 8, 2011)));
	assertEquals(DateHelper.newDate(31, 12, 2011), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(9, 9, 2011)));
	assertEquals(DateHelper.newDate(31, 3, 2012), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(10, 10, 2011)));
	assertEquals(DateHelper.newDate(31, 3, 2012), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(11, 11, 2011)));
	assertEquals(DateHelper.newDate(31, 3, 2012), DateHelper.getDatumBisFolgequartal(DateHelper.newDate(12, 12, 2011)));
    }

    @Test
    public void testNewDateFromExactTime() {

	Calendar calendar = Calendar.getInstance(new Locale("de", "CH"));
	calendar.setLenient(false);
	calendar.clear();

	Date min = DateHelper.newDateForExactTime(DateHelper.newDate(1, 1, 1900), 0, 0, 0, 0);
	calendar.set(1900, 0, 1, 0, 0, 0);
	calendar.set(Calendar.MILLISECOND, 0);
	assertEquals(calendar.getTime(), min);

	Date max = DateHelper.newDateForExactTime(DateHelper.newDate(31, 12, 9999), 23, 59, 59, 999);
	calendar.clear();
	calendar.set(9999, 11, 31, 23, 59, 59);
	calendar.set(Calendar.MILLISECOND, 999);
	assertEquals(calendar.getTime(), max);

	Date now = new Date();
	Date current = DateHelper.newDateForExactTime(now, DateHelper.getHour(now), DateHelper.getMinute(now), DateHelper.getSecond(now), DateHelper.getMillisecond(now));
	assertEquals(now, current);
    }

}
