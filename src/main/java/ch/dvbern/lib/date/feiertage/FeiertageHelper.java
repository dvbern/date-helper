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

package ch.dvbern.lib.date.feiertage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ch.dvbern.lib.date.DateHelper;

/**
 * Helper für Schweizer Feiertage. Es sind Samstage, Sonntage, Karfreitag,
 * Ostermontag, Auffahrt, Pfingstmontag, 1. August, 25. Dezember, 26. Dezember,
 * 1. Januar und 2. Januar
 * 
 * @author PECH
 * 
 */
public final class FeiertageHelper {

	private static Map<Integer, Feiertage_CH> feiertageMap = new HashMap<Integer, Feiertage_CH>();

	/**
	 * Wird nicht instanziert.
	 */
	private FeiertageHelper() {
		super();
	}

	/**
	 * Antwortet ein neues Datum welches den nächsten Arbeitstag nach dem Anzahl
	 * Tagen <code> dayCount </code> ist. <br>
	 * Dabei werden die offiziellen Feiertage Schweiz berücksichtigt.
	 * 
	 * @param date
	 *            Basis für die Berechnung.
	 * @param dayCount
	 *            anzahl Tage. Darf NICHT negativ sein. Mit 0 (zero) wird auch
	 *            den nächsten Arbeitstag gesucht.
	 * @return a new Date
	 */
	public static Date getNextWorkingDate(Date date, int dayCount) {

		if (date == null) {
			throw new IllegalArgumentException("date is null");
		}
		if (dayCount < 0) {
			throw new IllegalArgumentException("negative dayCount not allowed");
		}

		Date result = addDays(date, dayCount);
		while (isFeiertag_CH(result)) {
			result = addDays(result, 1);
		}
		return result;
	}

	/**
	 * Antwortet ein neues Datum welches den nächsten Arbeitstag nach dem Anzahl
	 * Tagen <code> dayCount </code> ist. <br>
	 * Dabei werden die offiziellen Feiertage Schweiz und die
	 * <code>sperrTagen</code> berücksichtigt.
	 * 
	 * @param date
	 *            Basis für die Berechnung.
	 * @param dayCount
	 *            anzahl Tage. Darf NICHT negativ sein. Mit 0 (zero) wird auch
	 *            den nächsten Arbeitstag gesucht.
	 * @param sperrTagen
	 *            Applikations-Spezifiche Tagen, die wie Feiertagen behandelt
	 *            werden.
	 * @return a new Date
	 */
	public static Date getNextWorkingDate(Date date, int dayCount,
			List<? extends Date> sperrTagen) {

		if (date == null) {
			throw new IllegalArgumentException("date is null");
		}
		if (dayCount < 0) {
			throw new IllegalArgumentException("negative dayCount not allowed");
		}

		List<Date> sperrTagenClean = cleanList(sperrTagen);

		Date result = addDays(date, dayCount);
		while (isFeiertag_CH(result) || isSperrTag(result, sperrTagenClean)) {
			result = addDays(result, 1);
		}
		return result;
	}

	/**
	 * Zählt zum per parameter <code>date</code> gegebenen Datum die als
	 * <code>dayCount</code> gegebene Anzahl Arbeitstage hinzu. Feiertage werden
	 * übersprungen.
	 * 
	 * <p>
	 * Beispiel: Der 1.4.2010 ist ein Donnerstag vor Karfreitag. Wird diesem
	 * Datum 3 Arbeitstage dazugezählt wird Karfreitag, Ostersamstag, Ostern und
	 * Ostermontag übersprungen. Der Resultierende Arbeitstag ist Donnerstag,
	 * 8.4.2010.
	 * <p>
	 * Diese Methode ist dassselbe wie der Aufruf von
	 * {@link #addWorkingDays(Date, int, List)} mit einer leeren Liste als 3.
	 * Argument.
	 * 
	 * @see #addWorkingDays(Date, int, List)
	 * @param date
	 *            Basis für die Berechnung. Wenn <code>date</code> selbst ein
	 *            Feiertag ist wird die Basis auf den nächsten Arbeitstag
	 *            gesetzt.
	 * @param dayCount
	 *            anzahl Tage. Darf NICHT negativ sein. Mit 0 (zero) wird
	 *            <code>date</code> zurückgegeben, wenn es sich dabei um einen
	 *            Arbeitstag handelt, sonst der nächste verfügbare Feiertag..
	 * @return der Arbeitstag nach Ablauf von <code>dayCount</code>
	 *         Arbeitstagen.
	 */
	public static Date addWorkingDays(Date date, int dayCount) {
		return addWorkingDays(date, dayCount, new ArrayList<Date>());
	}

	/**
	 * Zählt zum per parameter <code>date</code> gegebenen Datum die als
	 * <code>dayCount</code> gegebene Anzahl Arbeitstage hinzu. Feiertage werden
	 * übersprungen.
	 * 
	 * <p>
	 * Beispiel: Der 1.4.2010 ist ein Donnerstag vor Karfreitag. Wird diesem
	 * Datum 3 Arbeitstage dazugezählt wird Karfreitag, Ostersamstag, Ostern und
	 * Ostermontag übersprungen. Der Resultierende Arbeitstag ist Donnerstag,
	 * 8.4.2010.
	 * <p>
	 * Diese Methode ist dassselbe wie der Aufruf von
	 * {@link #addWorkingDays(Date, boolean, int, List)} mit einer leeren Liste
	 * als 3. Argument.
	 * 
	 * @see #addWorkingDays(Date, boolean, int, List)
	 * @param date
	 *            Basis für die Berechnung.
	 * @param jumpToWorkingDay
	 *            wenn <code>true</code> wird der Parameter <code>date</code>
	 *            ignoriert wenn es sich dabei selbst um einen Feiertag handelt.
	 *            Stattdessen wird von <code>date</code> aus auf den nächsten
	 *            Werktag gesprungen
	 * @param dayCount
	 *            anzahl Tage. Darf NICHT negativ sein. Mit 0 (zero) wird
	 *            <code>date</code> zurückgegeben, wenn es sich dabei um einen
	 *            Arbeitstag handelt, sonst der nächste verfügbare Feiertag..
	 * @return der Arbeitstag nach Ablauf von <code>dayCount</code>
	 *         Arbeitstagen.
	 */
	public static Date addWorkingDays(Date date, boolean jumpToWorkingDay,
			int dayCount) {
		return addWorkingDays(date, jumpToWorkingDay, dayCount,
				new ArrayList<Date>());
	}

	/**
	 * Zählt zum per parameter <code>date</code> gegebenen Datum die als
	 * <code>dayCount</code> gegebene Anzahl Arbeitstage hinzu. Feiertage werden
	 * übersprungen.
	 * <p>
	 * Die Liste <code>sperrTage</code> ist eine Liste von Tagen, welche
	 * applikationsspezifische Feiertage beinhalten.
	 * 
	 * <p>
	 * Beispiel: Der 1.4.2010 ist ein Donnerstag vor Karfreitag. Wird diesem
	 * Datum 3 Arbeitstage dazugezählt wird Karfreitag, Ostersamstag, Ostern und
	 * Ostermontag übersprungen. Der Resultierende Arbeitstag ist Donnerstag,
	 * 8.4.2010.
	 * <p>
	 * Diese Methode ist dassselbe wie der Aufruf von
	 * {@link #addWorkingDays(Date, boolean, int, List)} mit <code>true</code>
	 * als 2. Argument.
	 * 
	 * 
	 * @see #addWorkingDays(Date, boolean, int, List)
	 * @param date
	 *            Basis für die Berechnung. Wenn <code>date</code> selbst ein
	 *            Feiertag ist wird die Basis auf den nächsten Arbeitstag
	 *            gesetzt.
	 * @param dayCount
	 *            anzahl Tage. Darf NICHT negativ sein. Mit 0 (zero) wird
	 *            <code>date</code> zurückgegeben, wenn es sich dabei um einen
	 *            Arbeitstag handelt, sonst der nächste verfügbare Feiertag..
	 * @return der Arbeitstag nach Ablauf von <code>dayCount</code>
	 *         Arbeitstagen.
	 */
	public static Date addWorkingDays(Date date, int dayCount,
			List<? extends Date> sperrTage) {
		return addWorkingDays(date, true, dayCount, sperrTage);
	}

	/**
	 * Zählt zum per parameter <code>date</code> gegebenen Datum die als
	 * <code>dayCount</code> gegebene Anzahl Arbeitstage hinzu. Feiertage werden
	 * übersprungen.
	 * <p>
	 * Die Liste <code>sperrTage</code> ist eine Liste von Tagen, welche
	 * applikationsspezifische Feiertage beinhalten.
	 * 
	 * <p>
	 * Beispiel: Der 1.4.2010 ist ein Donnerstag vor Karfreitag. Wird diesem
	 * Datum 3 Arbeitstage dazugezählt wird Karfreitag, Ostersamstag, Ostern und
	 * Ostermontag übersprungen. Der Resultierende Arbeitstag ist Donnerstag,
	 * 8.4.2010.
	 * 
	 * 
	 * 
	 * @param date
	 *            Basis für die Berechnung. Wenn <code>date</code> selbst ein
	 *            Feiertag ist wird die Basis auf den nächsten Arbeitstag
	 *            gesetzt.
	 * @param jumpToWorkingDay
	 *            wenn <code>true</code> wird der Parameter <code>date</code>
	 *            ignoriert wenn es sich dabei selbst um einen Feiertag handelt.
	 *            Stattdessen wird von <code>date</code> aus auf den nächsten
	 *            Werktag gesprungen
	 * @param dayCount
	 *            anzahl Tage. Darf NICHT negativ sein. Mit 0 (zero) wird
	 *            <code>date</code> zurückgegeben, wenn es sich dabei um einen
	 *            Arbeitstag handelt, sonst der nächste verfügbare Feiertag..
	 * @return der Arbeitstag nach Ablauf von <code>dayCount</code>
	 *         Arbeitstagen.
	 */
	public static Date addWorkingDays(Date date, boolean jumpToWorkingDay,
			int dayCount, List<? extends Date> sperrTage) {
		if (date == null) {
			throw new IllegalArgumentException("date is null");
		}
		if (dayCount < 0) {
			throw new IllegalArgumentException("negative dayCount not allowed");
		}
		if (sperrTage == null) {
			throw new IllegalArgumentException("sperrTage is null");
		}
		Date result = date;
		if (jumpToWorkingDay) {
			// Falls result selbst ein Feiertag ist: auf den nächsten Arbeitstag
			// springen.
			result = getNextWorkingDate(result, 0, sperrTage);
		}
		int count = 0;
		while (count++ < dayCount) {
			result = getNextWorkingDate(result, 1, sperrTage);
		}
		return result;
	}

	private static boolean isSperrTag(Date date, List<Date> sperrTagen) {

		Date dateClean = cleanDate(date);

		for (Date tag : sperrTagen) {
			if (tag.getTime() == dateClean.getTime()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Antwortet ein neues Datum welches den vorigen Arbeitstag vor dem Anzahl
	 * Tagen <code> dayCount </code> ist. <br>
	 * Dabei werden die offiziellen Feiertage Schweiz berücksichtigt.
	 * 
	 * @param date
	 *            Basis für die Berechnung.
	 * @param dayCount
	 *            anzahl Tage. Darf NICHT negativ sein. Mit 0 (zero) wird auch
	 *            den vorigen Arbeitstag gesucht.
	 * @return a new Date
	 */
	public static Date getPreviousWorkingDate(Date date, int dayCount) {

		if (date == null)
			throw new IllegalArgumentException("date is null");
		if (dayCount < 0)
			throw new IllegalArgumentException("negative dayCount not allowed");

		Date result = addDays(date, dayCount * -1);
		while (isFeiertag_CH(result)) {
			result = addDays(result, -1);
		}
		return result;
	}

	/**
	 * Antwortet ein neues Datum welches den vorigen Arbeitstag vor dem Anzahl
	 * Tagen <code> dayCount </code> ist. <br>
	 * Dabei werden die offiziellen Feiertage Schweiz und die
	 * <code>sperrTagen</code> berücksichtigt.
	 * 
	 * @param date
	 *            Basis für die Berechnung.
	 * @param dayCount
	 *            anzahl Tage. Darf NICHT negativ sein. Mit 0 (zero) wird auch
	 *            den vorigen Arbeitstag gesucht.
	 * @param sperrTagen
	 *            Applikations-Spezifiche Tagen, die wie Feiertagen behandelt
	 *            werden.
	 * @return a new Date
	 */
	public static Date getPreviousWorkingDate(Date date, int dayCount,
			List<Date> sperrTagen) {

		if (date == null)
			throw new IllegalArgumentException("date is null");
		if (dayCount < 0)
			throw new IllegalArgumentException("negative dayCount not allowed");

		List<Date> sperrTagenClean = cleanList(sperrTagen);

		Date result = addDays(date, dayCount * -1);
		while (isFeiertag_CH(result) || isSperrTag(result, sperrTagenClean)) {
			result = addDays(result, -1);
		}
		return result;
	}

	/**
	 * Anzahl Arbeitstagen zwischen <code>datumVon</code> und
	 * <code>datumBis</code>. <br>
	 * D.h. Montag bis Freitag mit berücksichtigung der offiziellen Feiertage
	 * Schweiz.
	 * 
	 * @param datumVon
	 *            Start Berechnung (mitberechnet)
	 * @param datumBis
	 *            Ende berechnung (mitberechnet)
	 * @return Anzahl Arbeitstagen
	 */
	public static int getAnzahlArbeitstage(Date datumVon, Date datumBis) {

		if (datumVon == null)
			throw new IllegalArgumentException("datumVon ist null");
		if (datumBis == null)
			throw new IllegalArgumentException("datumBis ist null");
		if (DateHelper.isDateLess(datumBis, datumVon))
			throw new IllegalArgumentException(
					"datumBis ist kleiner als datumVon");

		int count = 0;
		Date current = DateHelper.newDateFromDate(datumVon);
		while (DateHelper.isDateLessOrEqual(current, datumBis)) {
			if (!isFeiertag_CH(current)) {
				count++;
			}
			current = DateHelper.addDays(current, 1);
		}

		return count;
	}

	/**
	 * Abfrage Feiertag Schweiz mit Tag, Monat und Jahr.
	 * 
	 * @param dayOfMonth
	 *            Tag im Monat. Werte: 1-31
	 * @param month
	 *            Monat im Jahr. Gem. gesunden Menschenverstand (1=Januar,
	 *            2=Februar, usw. ) Werte: 1-12
	 * @param year
	 *            Jahr. Werte: 1500-9999
	 * @return true wenn das Datum ein Samstag, Sonntag oder eidg. Feiertag ist.
	 */
	public static boolean isFeiertag_CH(int dayOfMonth, int month, int year) {

		if (dayOfMonth < 1 || dayOfMonth > 31)
			throw new IllegalArgumentException(
					"dateOfMonth not between 1 and 31");
		if (month < 1 || month > 13)
			throw new IllegalArgumentException("month not between 1 and 12");
		if (year < 1500 || year > 9999)
			throw new IllegalArgumentException("year not between 1500 and 9999");

		GregorianCalendar cal = getCalendar();
		cal.set(year, month - 1, dayOfMonth);

		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			return true;
		}
		return getFeiertage(year).isFeiertag(cal);
	}

	/**
	 * Abfrage Feiertag Schweiz mit Datum.
	 * 
	 * @param date
	 *            Datum
	 * @return true wenn das Datum ein Samstag, Sonntag oder eine offiziellen
	 *         Feiertag Schweiz ist.
	 */
	public static boolean isFeiertag_CH(Date date) {

		if (date == null)
			throw new IllegalArgumentException("date is null");

		GregorianCalendar cal = getCalendar();
		cal.setTime(date);
		return isFeiertag_CH(cal.get(Calendar.DAY_OF_MONTH), cal
				.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
	}

	/**
	 * Liste der Feiertagen Schweiz pro Jahr.
	 * 
	 * @param year
	 * @return
	 */
	public static List<Feiertag_CH> getFeiertage_CH(int year) {

		if (year < 1500 || year > 9999)
			throw new IllegalArgumentException("year not between 1500 and 9999");
		return getFeiertage(year).getFeiertage();
	}

	/**
	 * gibt einen Feiertag Schweiz pro Jahr und Name.
	 * 
	 * @param year
	 * @param feiertag
	 *            .
	 * @return Feiertag_CH oder null.
	 */
	public static Feiertag_CH getFeiertag_CH(int year, FeiertagSchweiz feiertag) {

		if (year < 1500 || year > 9999)
			throw new IllegalArgumentException("year not between 1500 and 9999");
		if (feiertag == null)
			throw new IllegalArgumentException("feiertag is null");

		return getFeiertage(year).getFeiertag(feiertag);
	}

	private static Date addDays(Date date, int dayCount) {

		Calendar calendar = getCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, dayCount);
		return calendar.getTime();
	}

	private static Feiertage_CH getFeiertage(int year) {

		Feiertage_CH f = feiertageMap.get(year);
		if (f == null) {
			feiertageMap.put(year, new Feiertage_CH(year));
		}
		return feiertageMap.get(year);
	}

	/**
	 * neues Calendar mit Datum und Zeit undefiniert.
	 * 
	 * @return
	 */
	private static GregorianCalendar getCalendar() {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setLenient(false);
		cal.clear();
		return cal;
	}

	/**
	 * Setzt Zeit auf 0 für alle Sperrtagen. Doppelte Einträge werden
	 * eliminiert.
	 */
	private static List<Date> cleanList(List<? extends Date> sperrTagen) {

		if (sperrTagen == null) {
			return Collections.emptyList();
		}

		HashSet<Date> sperrTagenClean = new HashSet<Date>();
		for (Date tag : sperrTagen) {
			sperrTagenClean.add(cleanDate(tag));
		}

		return new ArrayList<Date>(sperrTagenClean);
	}

	/**
	 * @param tag
	 * @return ein neues Datum mit Zeit auf 0
	 */
	private static Date cleanDate(Date date) {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
