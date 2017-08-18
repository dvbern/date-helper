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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test-Klasse für {@link Zeitraum}.
 */
public class ZeitraumTest {

	private final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

	@Test
	public void testMonthSplit() throws ParseException {

		Zeitraum zeitraum = new Zeitraum(df.parse("1.1.2008"), df.parse("31.12.2008"));
		Collection<Zeitraum> splittedByMonth = zeitraum.splitByMonth();
		assertEquals(12, splittedByMonth.size());
		zeitraum = new Zeitraum(df.parse("12.1.2008"), df.parse("12.12.2008"));
		splittedByMonth = zeitraum.splitByMonth();
		assertEquals(12, splittedByMonth.size());

		zeitraum = new Zeitraum(df.parse("7.8.2008"), df.parse("31.8.2009"));
		splittedByMonth = zeitraum.splitByMonth();
		assertFalse(splittedByMonth.isEmpty());

		zeitraum = new Zeitraum(df.parse("15.12.2008"), df.parse("31.12.2009"));
		splittedByMonth = zeitraum.splitByMonth();
		assertFalse(splittedByMonth.isEmpty());

		zeitraum = new Zeitraum(df.parse("15.8.2008"), df.parse("11.9.2008"));
		splittedByMonth = zeitraum.splitByMonth();
		assertFalse(splittedByMonth.isEmpty());
		assertEquals(2, splittedByMonth.size());

		zeitraum = new Zeitraum(df.parse("15.8.2008"), df.parse("11.12.2008"));
		splittedByMonth = zeitraum.splitByMonth();
		assertFalse(splittedByMonth.isEmpty());
		assertEquals(5, splittedByMonth.size());

		zeitraum = new Zeitraum(df.parse("15.12.2008"), df.parse("31.12.2010"));
		splittedByMonth = zeitraum.splitByMonth();
		assertFalse(splittedByMonth.isEmpty());
		assertEquals(25, splittedByMonth.size());

		zeitraum = new Zeitraum(df.parse("1.7.2010"), df.parse("1.9.2010"));
		splittedByMonth = zeitraum.splitByMonth();
		assertEquals(3, splittedByMonth.size());

		// Zeiträume <= 1 Monat: Leere Liste wird zurückgegeben.
		zeitraum = new Zeitraum(df.parse("1.7.2010"), df.parse("1.7.2010"));
		splittedByMonth = zeitraum.splitByMonth();
		assertTrue(splittedByMonth.isEmpty());
		zeitraum = new Zeitraum(df.parse("1.7.2010"), df.parse("31.7.2010"));
		splittedByMonth = zeitraum.splitByMonth();
		assertTrue(splittedByMonth.isEmpty());
		zeitraum = new Zeitraum(df.parse("15.7.2010"), df.parse("20.7.2010"));
		splittedByMonth = zeitraum.splitByMonth();
		assertTrue(splittedByMonth.isEmpty());
	}

	@Test
	public void testContains() throws ParseException {
		Zeitraum zeitraum = new Zeitraum(df.parse("1.7.2010"), df.parse("1.9.2010"));
		assertTrue(zeitraum.contains(df.parse("1.7.2010")));
		assertTrue(zeitraum.contains(df.parse("1.8.2010")));
		Date bis = zeitraum.getBis();
		bis = DateHelper.addDays(bis, 1);
		assertFalse(zeitraum.contains(bis));
	}

	/**
	 *
	 */
	@Test
	public void testSubtract() {

		Zeitraum zr = new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(31, 3, 2008));
		assertEquals(1, zr.subtract(

			new Zeitraum(DateHelper.newDate(5, 1, 2008), DateHelper.newDate(31, 1, 2008)),
			new Zeitraum(DateHelper.newDate(1, 2, 2008), DateHelper.newDate(29, 2, 2008)),
			new Zeitraum(DateHelper.newDate(1, 3, 2008), DateHelper.newDate(31, 3, 2008))

		).size());

		zr = new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(31, 12, 2008));
		assertEquals(0, zr.subtract(new Zeitraum(DateHelper.newDate(1, 1, 2007),
			DateHelper.newDate(31, 12, 2009))).size());
		List<Zeitraum> splitted = zr
			.subtract(new Zeitraum(DateHelper.newDate(1, 1, 2007), DateHelper.newDate(30, 6, 2008)));
		assertEquals(1, splitted.size());
		assertEquals(DateHelper.newDate(1, 7, 2008), splitted.get(0).getVon());
		assertEquals(DateHelper.newDate(31, 12, 2008), splitted.get(0).getBis());

		splitted = zr.subtract(new Zeitraum(DateHelper.newDate(1, 7, 2008), DateHelper.newDate(1, 7, 2009)));
		assertEquals(1, splitted.size());

		assertEquals(DateHelper.newDate(1, 1, 2008), splitted.get(0).getVon());
		assertEquals(DateHelper.newDate(30, 6, 2008), splitted.get(0).getBis());

		splitted = zr.subtract(new Zeitraum(DateHelper.newDate(1, 2, 2008), DateHelper.newDate(29, 2, 2008)), new Zeitraum(
			DateHelper.newDate(1, 7, 2008), DateHelper.newDate(1, 7, 2009)));
		assertEquals(2, splitted.size());

		assertEquals(DateHelper.newDate(1, 1, 2008), splitted.get(0).getVon());
		assertEquals(DateHelper.newDate(31, 1, 2008), splitted.get(0).getBis());
		assertEquals(DateHelper.newDate(1, 3, 2008), splitted.get(1).getVon());
		assertEquals(DateHelper.newDate(30, 6, 2008), splitted.get(1).getBis());

		splitted = zr.subtract(new Zeitraum(DateHelper.newDate(1, 2, 2007), DateHelper.newDate(29, 2, 2008)), new Zeitraum(
			DateHelper.newDate(1, 4, 2008), DateHelper.newDate(1, 7, 2009)));
		assertEquals(1, splitted.size());

		assertEquals(DateHelper.newDate(1, 3, 2008), splitted.get(0).getVon());
		assertEquals(DateHelper.newDate(31, 3, 2008), splitted.get(0).getBis());

		splitted = zr.subtract(new Zeitraum(DateHelper.newDate(1, 2, 2008), DateHelper.newDate(29, 2, 2008)), new Zeitraum(
			DateHelper.newDate(1, 4, 2008), DateHelper.newDate(1, 7, 2008)));
		assertEquals(3, splitted.size());
		assertEquals(DateHelper.newDate(1, 1, 2008), splitted.get(0).getVon());
		assertEquals(DateHelper.newDate(31, 1, 2008), splitted.get(0).getBis());

		assertEquals(DateHelper.newDate(1, 3, 2008), splitted.get(1).getVon());
		assertEquals(DateHelper.newDate(31, 3, 2008), splitted.get(1).getBis());

		assertEquals(DateHelper.newDate(2, 7, 2008), splitted.get(2).getVon());
		assertEquals(DateHelper.newDate(31, 12, 2008), splitted.get(2).getBis());

		zr = new Zeitraum(DateHelper.newDate(24, 6, 2008), DateHelper.newDate(19, 6, 2009));
		splitted = zr.subtract(new Zeitraum(DateHelper.newDate(1, 1, 2009), DateHelper.newDate(31, 3, 2009)), new Zeitraum(
			DateHelper.newDate(1, 4, 2009), DateHelper.newDate(30, 4, 2009)), new Zeitraum(
			DateHelper.newDate(1, 4, 2009), DateHelper.newDate(30, 4, 2009)), new Zeitraum(
			DateHelper.newDate(1, 5, 2009), DateHelper.newDate(31, 5, 2009)), new Zeitraum(
			DateHelper.newDate(1, 8, 2009), DateHelper.newDate(31, 8, 2009)));
		assertEquals(2, splitted.size());

		assertEquals(DateHelper.newDate(24, 6, 2008), splitted.get(0).getVon());
		assertEquals(DateHelper.newDate(31, 12, 2008), splitted.get(0).getBis());

		assertEquals(DateHelper.newDate(1, 6, 2009), splitted.get(1).getVon());
		assertEquals(DateHelper.newDate(19, 6, 2009), splitted.get(1).getBis());

		zr = new Zeitraum(DateHelper.newDate(1, 1, 2009), DateHelper.newDate(31, 12, 2010));
		splitted = zr.subtract(new Zeitraum(DateHelper.newDate(5, 8, 2009), DateHelper.newDate(5, 8, 2009)));
		assertEquals(2, splitted.size());

		assertEquals(DateHelper.newDate(1, 1, 2009), splitted.get(0).getVon());
		assertEquals(DateHelper.newDate(4, 8, 2009), splitted.get(0).getBis());

		assertEquals(DateHelper.newDate(6, 8, 2009), splitted.get(1).getVon());
		assertEquals(DateHelper.newDate(31, 12, 2010), splitted.get(1).getBis());

	}

	/**
	 * @throws ParseException
	 */
	@Test
	public void testDaysBetween() throws ParseException {

		Zeitraum zeitraum = new Zeitraum(df.parse("1.1.2008"), df.parse("2.1.2008"));
		assertThat(zeitraum.getDuationInDays(), is(2));
		zeitraum = new Zeitraum(df.parse("1.1.2008"), df.parse("31.1.2008"));
		assertThat(zeitraum.getDuationInDays(), is(31));
		zeitraum = new Zeitraum(df.parse("15.1.2008"), df.parse("21.1.2008"));
		assertThat(zeitraum.getDuationInDays(), is(7));
	}

	@Test
	public void testGetDurationInDays() throws Exception {

		// 368 Tage: 2008 ist Schaltjahr und hat 366 Tage, dazu kommen 1.1. und 2.1.2009.
		assertEquals(368, new Zeitraum(df.parse("1.1.2008"), df.parse("2.1.2009")).getDuationInDays());
		assertEquals(2, new Zeitraum(df.parse("1.1.2008"), df.parse("2.1.2008")).getDuationInDays());
		assertEquals(366, new Zeitraum(df.parse("1.1.2008"), df.parse("31.12.2008")).getDuationInDays());
		// 366 + 365 + 365 + 365 + 366 = 1827
		assertEquals(1827, new Zeitraum(df.parse("1.1.2008"), df.parse("31.12.2012")).getDuationInDays());
		assertEquals(24, new Zeitraum(df.parse("8.1.2010"), df.parse("31.1.2010")).getDuationInDays());
		assertEquals(31, new Zeitraum(df.parse("1.1.2010"), df.parse("31.1.2010")).getDuationInDays());
		assertEquals(1, new Zeitraum(df.parse("1.1.2010"), df.parse("1.1.2010")).getDuationInDays());
	}

	@Test
	public void testIntersects() {

		Zeitraum zr = new Zeitraum(DateHelper.newDate(1, 1, 2009), DateHelper.newDate(31, 1, 2009));
		assertTrue(zr.intersects(zr));

		Zeitraum zr2 = new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(31, 1, 2010));
		assertTrue(zr.intersects(zr2));
		assertTrue(zr2.intersects(zr));

		Zeitraum zr3 = new Zeitraum(DateHelper.newDate(31, 1, 2009), DateHelper.newDate(31, 1, 2010));
		assertTrue(zr.intersects(zr3));
		assertTrue(zr3.intersects(zr));

		Zeitraum zr4 = new Zeitraum(DateHelper.newDate(1, 2, 2009), DateHelper.newDate(31, 1, 2010));
		assertFalse(zr.intersects(zr4));
		assertFalse(zr4.intersects(zr));

		Zeitraum zr5 = new Zeitraum(DateHelper.newDate(1, 2, 2008), DateHelper.newDate(1, 1, 2009));
		assertTrue(zr.intersects(zr5));
		assertTrue(zr5.intersects(zr));

		Zeitraum zr6 = new Zeitraum(DateHelper.newDate(10, 1, 2009), DateHelper.newDate(15, 1, 2009));
		assertTrue(zr.intersects(zr6));
		assertTrue(zr6.intersects(zr));

		Zeitraum zr7 = new Zeitraum(DateHelper.newDate(10, 1, 2009), DateHelper.newDate(10, 1, 2009));
		assertTrue(zr.intersects(zr7));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalid() {
		new Zeitraum(DateHelper.currentDate(), DateHelper.currentDateMinusMonaten(1));
	}

	@Test
	public void testZeitraum0Duration() {
		Date d = new Date();
		Zeitraum z = new Zeitraum(d, d);
		assertEquals(1, z.getDuationInDays());
	}

	@Test
	public void testSplit() {

		Zeitraum z = new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(31, 3, 2008));
		List<Zeitraum> splittedZeitraeume = new ArrayList<Zeitraum>(z.split(DateHelper.newDate(1, 2, 2008), DateHelper
			.newDate(1, 3, 2008)));
		assertEquals(3, splittedZeitraeume.size());

		assertThat(splittedZeitraeume.get(0), equalTo(new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(31,
			1, 2008))));
		assertThat(splittedZeitraeume.get(1), equalTo(new Zeitraum(DateHelper.newDate(1, 2, 2008), DateHelper.newDate(29,
			2, 2008))));
		assertThat(splittedZeitraeume.get(2), equalTo(new Zeitraum(DateHelper.newDate(1, 3, 2008), DateHelper.newDate(31,
			3, 2008))));

		splittedZeitraeume = new ArrayList<Zeitraum>(z.split(DateHelper.newDate(1, 1, 2008),
			DateHelper.newDate(1, 3, 2007), DateHelper.newDate(1, 2, 2009), DateHelper.newDate(31, 3, 2008)));
		assertEquals(3, splittedZeitraeume.size());
		assertThat(splittedZeitraeume.get(0), equalTo(new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(1,
			1, 2008))));
		assertThat(splittedZeitraeume.get(1), equalTo(new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(30,
			3, 2008))));
		assertThat(splittedZeitraeume.get(2), equalTo(new Zeitraum(DateHelper.newDate(31, 3, 2008), DateHelper.newDate(31,
			3, 2008))));
	}

	@Test
	public void testEquals() {
		Zeitraum z1 = new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(31,
			1, 2008));
		Zeitraum z2 = z1;
		assertTrue(z1.equals(z2));
		z2 = new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(31,
			1, 2008));
		assertTrue(z1.equals(z2));
		z2 = new Zeitraum(DateHelper.newDate(2, 1, 2008), DateHelper.newDate(31,
			1, 2008));
		assertFalse(z1.equals(z2));
		assertFalse(z1.equals(null));
		assertFalse(z1.equals("Ich bin kein Zeitraum"));
	}

	@Test
	public void testToString() {
		Zeitraum sameYear = new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(31,
			1, 2008));
		assertEquals("01.01. - 31.01.2008", sameYear.toString());
		Zeitraum threeYears = new Zeitraum(DateHelper.newDate(1, 1, 2000), DateHelper.newDate(31, 12, 2002));
		assertEquals("01.01.2000 - 31.12.2002", threeYears.toString());
	}

	@Test
	public void testGetDistanceFrom() {
		Zeitraum z1 = new Zeitraum(DateHelper.newDate(1, 1, 2008), DateHelper.newDate(31,
			1, 2008));
		Date d = DateHelper.newDate(31, 12, 2007);
		assertEquals(-86400000, z1.getDistanceFrom(d));
		d = DateHelper.newDate(1, 2, 2008);
		assertEquals(86400000, z1.getDistanceFrom(d));
		d = z1.getVon();
		assertEquals(0, z1.getDistanceFrom(d));
		d = z1.getBis();
		assertEquals(0, z1.getDistanceFrom(d));
		d = DateHelper.newDate(15, 1, 2008);
		assertEquals(0, z1.getDistanceFrom(d));
	}

}
