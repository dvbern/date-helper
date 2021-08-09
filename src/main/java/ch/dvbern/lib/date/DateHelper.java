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

/*
 * Created on 10.07.2003
 *
 */
package ch.dvbern.lib.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author muse
 */
public final class DateHelper {

	private static final Locale LOCALE_DE_CH = new Locale("de", "CH");

	/**
	 * Wird nicht instanziert.
	 */
	protected DateHelper() {
		//empty
	}

	/**
	 * Antwortet ein neues Datum welches ein Integer dayCount Tage nach dem
	 * übergebenen Datum ist.
	 *
	 * @param date Datum an dem die dayCount Tage hinzu addiert werden
	 * @param dayCount Anzahl Tage hinzuaddieren.
	 * @return java.util.Date
	 */
	public static Date addDays(Date date, int dayCount) {

		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.DATE, dayCount);
		return calendar.getTime();
	}

	/**
	 * Antwortet ein neues Datum welches ein Integer anzahl Monate nach dem
	 * übergebenen Datum ist.
	 *
	 * @param date Datum an dem die anzahl Monate hinzu addiert werden
	 * @param anzahl Anzahl monate hinzuaddieren (kann auch negaativ sein
	 * @return java.util.Date
	 */
	public static Date addMonths(Date date, int anzahl) {

		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.MONTH, anzahl);
		return calendar.getTime();
	}

	/**
	 * Antwortet ein neues Datum nach addieren Anzahl Monate monthCount.
	 *
	 * @param date Datum an dem die Monate addiert werden
	 * @param monthCount Anzahl Monate hinzuaddieren. Kann auch negativ sein
	 * @param withEndMonthCheck Letzter Tag des Monats wird berücksichtigt
	 * @return java.util.Date
	 */
	public static Date addMonths(Date date, int monthCount, boolean withEndMonthCheck) {

		Date result = addMonths(date, monthCount);

		if (withEndMonthCheck) {
			int day = getDay(date);
			int month = getMonth(date);
			int year = getYear(date);
			if (getDaysInMonth(month, year) == day) {
				day = getDay(result);
				month = getMonth(result);
				year = getYear(result);
				result = newDate(getDaysInMonth(month, year), month, year);
			}
		}
		return result;
	}

	/**
	 * Antwortet ein neues Datum welches ein Integer anzahl Jahre nach dem
	 * übergebenen Datum ist.
	 *
	 * @param date Datum an dem die anzahl Jahre hinzu addiert werden
	 * @param anzahl Anzahl Jahre hinzuaddieren (kann auch negaativ sein)
	 * @return java.util.Date
	 */
	public static Date addYears(Date date, int anzahl) {

		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.YEAR, anzahl);
		return calendar.getTime();
	}

	/**
	 * Returns the day of the week represented by the date. The returned value
	 * (1 = Sunday, 2 = Monday, 3 = Tuesday, 4 = Wednesday, 5 = Thursday, 6 =
	 * Friday, 7 = Saturday) represents the day of the week that contains or
	 * begins with the instant in time represented by this Date object, as
	 * interpreted in the local time zone.
	 *
	 * @return int the day of the week represented by this date
	 */
	public static int dayOfWeek(Date date) {

		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Antwortet ein neues Datum mit dem Datum {@code date}
	 * und der Zeit {@code time}.
	 *
	 * @param date Datum von welchem das Datum verwendet wird
	 * @param time Datum von welchem die Zeit verwendet wird
	 * @return java.util.Date
	 */
	public static Date getDate(Date date, Date time) {

		Calendar timeCalender = getCalendar(time);

		Calendar calendar = getCalendar(date);

		calendar.set(Calendar.MILLISECOND, timeCalender.get(Calendar.MILLISECOND));
		calendar.set(Calendar.SECOND, timeCalender.get(Calendar.SECOND));
		calendar.set(Calendar.MINUTE, timeCalender.get(Calendar.MINUTE));
		calendar.set(Calendar.HOUR_OF_DAY, timeCalender.get(Calendar.HOUR_OF_DAY));
		return calendar.getTime();
	}

	/**
	 * Antworted ein Datum
	 * {@code date} welches aus dem String erstellt wurde {@code aDateString}
	 *
	 * @return date Date
	 */
	public static Date getDateFromString(String aDateString) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		formatter.setLenient(false);
		return formatter.parse(aDateString);
	}

	/**
	 * Antwortet den Tag des Datums {@code date}.
	 * Der erste Tag des Monats hat den Wert 1.
	 *
	 * @return int Tages Index
	 */
	public static int getDay(Date date) {

		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * Antwortet die Kalenderwoche, in welcher das Datum {@code date} liegt.
	 * Die erste Woche ist die Woche 1
	 *
	 * @return int
	 */
	public static int getWeekOfYear(Date date) {

		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Antwortet das Jahr, zu welchem die Kalenderwoche des Datums
	 * {@code date} gehört.
	 * Beispiel: 1.1.2005 hat die Kalenderwoche 53 des Jahres 2004, es wird 2004 zurückgegeben
	 *
	 * @return int
	 */
	public static int getYearOfWeek(Date date) {

		int year = getYear(date);
		int week = getWeekOfYear(date);
		int month = getMonth(date);

		if (week > 50 && month == 1) {
			// Woche im Januar geh�rt noch zum Vorjahr
			// 50 k�nnte auch z.B 49, 48, etc. sein, einfach gr�sser als die max
			// Woche im Januar
			return year - 1;
		} else if (week < 10 && month == 12) {
			// Woche im Dezember geh�rt schon zum nächsten Jahr
			// 10 könnte auch z.B 9, 8, etc. sein, einfach kleiner als die min
			// Woche im Dezember
			return year + 1;
		} else {
			return year;
		}
	}

	/**
	 * Antwortet ein Integer von 1-31 welcher die anzahl Tage des Monats
	 * entspricht. Das Jahr muss wegen dem Schaltjahr bekannt sein.
	 *
	 * @param month Index des Monats
	 * @param year Jahr in dem die anzahl Tage des Monats ermittelt werden
	 * sollen.
	 * @return int Anzahl Tage des Monats
	 */
	public static int getDaysInMonth(int month, int year) {

		Calendar calendar = getCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Antwortet die Stunde des Datums {@code date}.
	 * Der erste Stunde des Tages hat den Wert 0.
	 *
	 * @return int Stunde des Tages
	 */
	public static int getHour(Date date) {

		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Antwortet die Minuten des Datums {@code date}.
	 * Der erste Minute der Stunde hat den Wert 0.
	 *
	 * @return int Minute des Stunde
	 */
	public static int getMinute(Date date) {

		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * Antwortet den Monat des Datums {@code date}.
	 * Der erste Monat des Jahres hat den Wert 1.
	 *
	 * @return int Monats Index
	 */
	public static int getMonth(Date date) {

		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * Antwortet die Sekunden des Datums {@code date}.
	 * Der erste Sekunde der Minute hat den Wert 0.
	 *
	 * @return int Sekunde der Minuten
	 */
	public static int getSecond(Date date) {

		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * Antwortet die Millisekunden des Datums {@code date}.
	 *
	 * @return int millisekunden der sekunde (0-999)
	 */
	public static int getMillisecond(Date date) {

		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MILLISECOND);
	}

	/**
	 * Antwortet das Jahr des Datums {@code date}.
	 *
	 * @return int Jahreszahl
	 */
	public static int getYear(Date date) {

		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Antwortet {@code true}, wenn date1 kleiner (älter) oder gleich wie
	 * date2 ist.
	 *
	 * @return boolean
	 */
	public static boolean isLessOrEqual(Date date1, Date date2) {

		return date1.compareTo(date2) <= 0;
	}

	/**
	 * Antwortet {@code true}, wenn date1 kleiner (älter) als date2 ist.
	 *
	 * @return boolean
	 */
	public static boolean isLess(Date date1, Date date2) {

		return date1.compareTo(date2) < 0;
	}

	/**
	 * Antwortet {@code true}, wenn date1 grösser (neuer) oder gleich wie
	 * date2 ist.
	 *
	 * @return boolean
	 */
	public static boolean isMoreOrEqual(Date date1, Date date2) {

		return date1.compareTo(date2) >= 0;
	}

	/**
	 * Antwortet {@code true}, wenn date1 grösser (neuer) als date2 ist.
	 *
	 * @return boolean
	 */
	public static boolean isMore(Date date1, Date date2) {

		return date1.compareTo(date2) > 0;
	}

	/**
	 * Antwortet {@code true}, wenn date1 kleiner (älter) oder gleich wie
	 * date2 ist. Die Zeit wird auf 0 gesetzt (beim Vergleich nicht
	 * berücksichtigt)
	 */
	public static boolean isDateEqual(Date date1, Date date2) {

		if (date1 == null && date2 == null) {
			return true;
		} else if (date1 == null || date2 == null) {
			return false;
		} else {
			return newDateFromDate(date1).equals(newDateFromDate(date2));
		}
	}

	/**
	 * Vergleicht die Inhalte zweiter Datumswerte. Die Zeit wird dabei ebenfalls
	 * berücksichtigt
	 *
	 * @return date1.getTime() == date2.getTime()
	 */
	public static boolean isDateEqualWithTime(Date date1, Date date2) {

		if (date1 == null && date2 == null) {
			return true;
		}
		if (date1 == null || date2 == null) {
			return false;
		}

		return date1.getTime() == date2.getTime();
	}

	/**
	 * Antwortet {@code true}, wenn date1 kleiner (älter) oder gleich wie
	 * date2 ist. Die Zeit wird auf 0 gesetzt (beim Vergleich nicht
	 * berücksichtigt)
	 *
	 * @return boolean
	 */
	public static boolean isDateLessOrEqual(Date date1, Date date2) {

		return isLessOrEqual(newDateFromDate(date1), newDateFromDate(date2));
	}

	/**
	 * Antwortet {@code true}, wenn date1 kleiner (älter) als date2 ist.
	 * Die Zeit wird auf 0 gesetzt (beim Vergleich nicht berücksichtigt)
	 *
	 * @return boolean
	 */
	public static boolean isDateLess(Date date1, Date date2) {

		return isLess(newDateFromDate(date1), newDateFromDate(date2));
	}

	/**
	 * Antwortet {@code true}, wenn date1 grösser (neuer) oder gleich wie
	 * date2 ist. Die Zeit wird auf 0 gesetzt (beim Vergleich nicht
	 * berücksichtigt)
	 *
	 * @return boolean
	 */
	public static boolean isDateMoreOrEqual(Date date1, Date date2) {

		return isMoreOrEqual(newDateFromDate(date1), newDateFromDate(date2));
	}

	/**
	 * Antwortet {@code true}, wenn date1 grösser (neuer) als date2 ist.
	 * Die Zeit wird auf 0 gesetzt (beim Vergleich nicht berücksichtigt)
	 *
	 * @return boolean
	 */
	public static boolean isDateMore(Date date1, Date date2) {

		return isMore(newDateFromDate(date1), newDateFromDate(date2));
	}

	/**
	 * Instanziert ein neues Datum, die Zeit wird auf 0 gesetzt
	 *
	 * @param day (1-31)
	 * @param month (1-12)
	 * @param year ist das jahr 0, wird es auf 1 gesetzt (0 ist nicht zulässig)
	 * @return java.util.Date
	 */
	public static Date newDate(int day, int month, int year) {

		Calendar calendar = getCalendar();
		if (year == 0) {
			calendar.set(1, month - 1, day);
		} else {
			calendar.set(year, month - 1, day);
		}
		return calendar.getTime();
	}

	/**
	 * Instanziert ein neues Datum mit aDate, die Zeit wird auf 0 gesetzt
	 *
	 * @return java.util.Date
	 */
	public static Date newDateFromDate(Date aDate) {

		if (aDate != null) {
			return newDate(getDay(aDate), getMonth(aDate), getYear(aDate));
		} else {
			return null;
		}
	}

	/**
	 * Instanziert ein aktuelles Datum mit der Zeit 0
	 *
	 * @return java.util.Date
	 */
	public static Date currentDate() {

		return newDateFromDate(new Date());
	}

	/**
	 * Instanziert ein neues Datum, die Zeit wird auf 0 gesetzt
	 *
	 * @param second (0-59)
	 * @param minute (0-59)
	 * @param hour (0-23)
	 * @param day (1-31)
	 * @param month (1-12)
	 * @return java.util.Date
	 */
	public static Date newDate(int second, int minute, int hour, int day, int month, int year) {

		Calendar calendar = getCalendar();
		calendar.set(year, month - 1, day, hour, minute, second);
		return calendar.getTime();
	}

	/**
	 * Prüft, ob {@code date} im Zeitbereich von <code>dateFrom</code> und
	 * {@code dateTo} liegt. In diesem Fall wird <code>true</code>
	 * geantwortet, sonst {@code false}.
	 * <p>
	 * Einfacher ausgedrückt:
	 * {@code return (dateFrom <= date) & (dateTo >= date)}
	 * <p>
	 *
	 * @param date java.util.Date
	 * @param dateFrom java.util.Date
	 * @param dateTo java.util.Date
	 * @return boolean
	 */
	public static boolean isBetween(Date date, Date dateFrom, Date dateTo) {

		if (date == null) {
			return false;
		}

		boolean vonOK = true;
		if (dateFrom != null) {
			vonOK = !date.before(dateFrom);
		}

		boolean bisOK = true;
		if (dateTo != null) {
			bisOK = !date.after(dateTo);
		}

		return vonOK && bisOK;
	}

	/**
	 * Prüft, ob {@code date} im Datumbsbereich von <code>dateFrom</code>
	 * und {@code dateTo} liegt. In diesem Fall wird <code>true</code>
	 * geantwortet, sonst {@code false}. Die Zeit wird auf 0 gesetzt (beim
	 * Vergleich nicht beruechsichtigt)
	 * <p>
	 * Einfacher ausgedrückt:
	 * {@code return (dateFrom <= date) & (dateTo >= date)}
	 * <p>
	 *
	 * @param date java.util.Date
	 * @param dateFrom java.util.Date
	 * @param dateTo java.util.Date
	 * @return boolean
	 */
	public static boolean isDateBetween(Date date, Date dateFrom, Date dateTo) {

		return isBetween(newDateFromDate(date), newDateFromDate(dateFrom), newDateFromDate(dateTo));
	}

	/**
	 * Antwortet das grössere der beiden Daten. Wenn beide gleich gross sind,
	 * wird date1 zurückgegeben.
	 *
	 * @return Date
	 */
	public static Date getMax(Date date1, Date date2) {

		if (isMoreOrEqual(date1, date2)) {
			return date1;
		} else {
			return date2;
		}
	}

	/**
	 * Antwortet das kleinere der beiden Daten. Wenn beide gleich gross sind,
	 * wird date1 zurückgegeben.
	 *
	 * @return Date
	 */
	public static Date getMin(Date date1, Date date2) {

		if (isLessOrEqual(date1, date2)) {
			return date1;
		} else {
			return date2;
		}
	}

	/**
	 * Antwortet das Datum des ersten Tags der Woche (Montag), in der
	 * {@code date} liegt
	 *
	 * @return Date
	 */
	public static Date getFirstDayOfWeek(Date date) {

		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * Antwortet das Datum des letzten Tags der Woche (Sonntag), in der
	 * {@code date} liegt
	 *
	 * @return Date
	 */
	public static Date getLastDayOfWeek(Date date) {

		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	/**
	 * Antwortet das Datum des ersten Tags des Monats, in der {@code date}
	 * liegt
	 *
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(Date date) {

		Calendar calendar = getCalendar(date);
		int firstDate = calendar.getActualMinimum(Calendar.DATE);
		calendar.set(Calendar.DATE, firstDate);
		return calendar.getTime();
	}

	/**
	 * Antwortet das Datum des letzten Tags des Monats, in der {@code date}
	 * liegt
	 *
	 * @return Date
	 */
	public static Date getLastDayOfMonth(Date date) {

		Calendar calendar = getCalendar(date);
		int lastDate = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, lastDate);
		return calendar.getTime();
	}

	/**
	 * @return aString formatted "dd.MM.yyyy"
	 */
	public static String getFormattedString(Date date) {

		if (date != null) {
			return (new SimpleDateFormat("dd.MM.yyyy")).format(date);
		} else {
			return "";
		}

	}

	/**
	 * @param inklusiveDatumVon angabe, ob das vonDatum auch mitgezählt werden soll
	 * @return Anzahl Tage zwischen von und bis
	 */
	public static int getAnzahlTage(Date von, Date bis, boolean inklusiveDatumVon) {

		if (von == null || bis == null) {
			return 0;
		}

		double tage = (double) (newDateFromDate(bis).getTime() - newDateFromDate(von).getTime()) / 86400000;
		long anzahlTage = Math.round(tage); // Anzahl Millisecons pro Tag

		if (inklusiveDatumVon) {
			anzahlTage++;
		}

		return (int) anzahlTage;
	}

	/**
	 * Gibt {@code true} zurück wenn das Jahr des gegebenen Datums ein
	 * Schaltjahr ist, sonst {@code false}.
	 *
	 * @param d Datm zum Prüfen ob Schaltjahr
	 * @return {@code true} wenn Schaltjahr, <code>false</code> sonst.
	 */
	public static boolean isSchaltjahr(Date d) {

		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int jahr = c.get(Calendar.YEAR);
		return ((jahr % 4 == 0 && jahr % 100 != 0) || jahr % 400 == 0);
	}

	/**
	 * @return flag, ob zwischen den beiden Daten ein Schalttag vorhanden ist
	 */
	public static boolean isSchalttagBetween(Date von, Date bis) {

		if (von == null || bis == null) {
			return false;
		}
		if (isDateLess(bis, von)) {
			return false;
		}

		Date datum = von;

		while (isDateLessOrEqual(datum, bis)) {
			if (getMonth(datum) == 2 && getDay(datum) == 29) {
				return true;
			}
			datum = addDays(datum, 1);
		}

		return false;
	}

	/**
	 * Instanziert ein neues Datum mit aDate und die Zeit-Parameters
	 */
	public static Date newDateForExactTime(Date aDate, int hour, int minute, int second, int millisecond) {

		Calendar cal = getCalendar(aDate);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);

		return cal.getTime();
	}

	/**
	 * @return Ein Calendar mit Local CH_DE, Datum und Zeit sind undefiniert.
	 */

	protected static Calendar getCalendar() {

		Calendar calendar = Calendar.getInstance(LOCALE_DE_CH);
		calendar.setLenient(false);
		calendar.clear();
		return calendar;
	}

	/**
	 * Initiialisiert einen {@link Calendar} mit bestimmtem Wert
	 *
	 * @param initialValue Initialer Datums-Wert des Kalenders
	 * @return a non-lenient calendar with the given Date as default value.
	 */
	protected static Calendar getCalendar(Date initialValue) {

		Calendar calendar = getCalendar();
		calendar.setTime(initialValue);
		return calendar;
	}

	public static String getFormattedZeitraum(Zeitraum zeitraum) {

		return getFormattedString(zeitraum.getVon()) + " - " + getFormattedString(zeitraum.getBis());
	}

	/**
	 * Antwortet eine List von Zeitraum-Lücken welche in der angegebenen
	 * Zeitraum-Liste vorkommen.<br>
	 * Lücke heisst, dass der nächste Zeitraum mehr als ein Tag nach dem
	 * vorderen beginnt.
	 *
	 * @return Liste von Lücken / kann auch leer sein
	 */
	public static List getZeitraumLuecken(List zeitraeume) {

		Collections.sort(zeitraeume);

		List luecken = new ArrayList();

		Date previous = null;
		for (Iterator iterator = zeitraeume.iterator(); iterator.hasNext(); ) {
			Zeitraum zeitraum = (Zeitraum) iterator.next();

			if (previous != null) {
				Date begin = DateHelper.addDays(previous, 1);
				if (DateHelper.isDateMore(zeitraum.getVon(), begin)) {
					luecken.add(new Zeitraum(begin, DateHelper.addDays(zeitraum.getVon(), -1)));
				}
			}

			previous = zeitraum.getBis();
		}

		return luecken;
	}

	/**
	 * gibt die Timestamp zurück
	 *
	 * @return Timestamp (Timestamp zurück)
	 */
	public static Timestamp currentTimestamp() {

		return new Timestamp(System.currentTimeMillis());

	}

	/**
	 * Lierfert das heutige Datum minus 'anzahleMonaten'
	 *
	 * @return Datum
	 */
	public static Date currentDateMinusMonaten(int anzahleMonaten) {

		GregorianCalendar myCalendar1 = new GregorianCalendar();
		myCalendar1.add(Calendar.MONTH, (-anzahleMonaten));
		return new Date(myCalendar1.getTime().getTime());
	}

	/**
	 * Konvertiert die Millisekunden in einen SQL Server kompatiblen Bereich.
	 *
	 * @return den Timestamp mit SQL Server Millisekunden
	 */
	public static Date withSqlServerMilliseconds(Date d) {

		if (d == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int sqlSrvMilliseconds = cal.get(Calendar.MILLISECOND);
		int part100 = (sqlSrvMilliseconds / 10) * 10;
		int part1000 = sqlSrvMilliseconds % 10;
		if (part1000 < 3) {
			part1000 = 0;
		} else if (part1000 < 7) {
			part1000 = 3;
		} else if (part1000 < 10) {
			part1000 = 7;
		}
		sqlSrvMilliseconds = part100 + part1000;
		cal.set(Calendar.MILLISECOND, sqlSrvMilliseconds);
		return cal.getTime();
	}

	/**
	 * Liest das gegebene Feld aus dem gegebenen {@link Date} Objekt und gibt
	 * dieses zurück.
	 *
	 * @param field das zu lesende Feld. Muss eines von {@link Calendar#YEAR},
	 * {@link Calendar#MONTH}, {@link Calendar#DAY_OF_YEAR},
	 * {@link Calendar#DAY_OF_MONTH}, {@link Calendar#DAY_OF_WEEK},
	 * {@link Calendar#DAY_OF_WEEK_IN_MONTH}, {@link Calendar#HOUR},
	 * {@link Calendar#HOUR_OF_DAY}, {@link Calendar#MINUTE},
	 * {@link Calendar#SECOND}, {@link Calendar#MILLISECOND} sein.
	 * @param date das Datum von welchem gelesen werden soll.
	 * @return das gewünschte Feld
	 */
	public static int get(int field, Date date) {

		Calendar cal = getCalendar(date);
		int retVal = cal.get(field);
		if (Calendar.MONTH == field) {
			// Calendar.MONTH geht von 0-11
			retVal++;
		}
		return retVal;
	}

	/**
	 * Gibt ein {@link Date} zurueck mit demselben Datum wie im Parameter, aber
	 * ohne Zeitangaben (0 Stunden, 0 Minuten, 0 Sekunden, 0 Milisekunden).
	 *
	 * @param date Datum zum zurueckgeben
	 * @return ein neues {@link Date}-Objekt ohne Zeitangaben.
	 */
	public static Date stripTime(final Date date) {

		return newDateFromDate(date);
	}

	/**
	 * Berechnet das Alter am Ã¼bergebenen Stichdatum.
	 *
	 * @param geburtsdatum Geburtsdatum
	 * @param stichdatum Datum, an welchem das Alter berechnet werden soll
	 * @return das Alter
	 */
	public static int getAlterAm(Date geburtsdatum, Date stichdatum) {
		// Tageszeit nicht beachten
		Date geburtsdatumClean = stripTime(geburtsdatum);
		Date stichdatumClean = stripTime(stichdatum);

		// Parameter ermitteln
		int yearDOB = get(Calendar.YEAR, geburtsdatumClean);
		int monthDOB = get(Calendar.MONTH, geburtsdatumClean);
		int dayDOB = get(Calendar.DAY_OF_MONTH, geburtsdatumClean);

		int yearStichdatum = get(Calendar.YEAR, stichdatumClean);
		int monthStichdatum = get(Calendar.MONTH, stichdatumClean);
		int dayStichdatum = get(Calendar.DAY_OF_MONTH, stichdatumClean);

		// Erste AnnÃ¤herung: Alter ist Differenz der Jahre
		int age = yearStichdatum - yearDOB;

		// Falls Monat des Stichdatums kleiner als Monat des Geburtsdatums,
		// dann muss das Alter um 1 reduziert werden (Geburtstag war noch nicht
		// dieses Jahr)
		if (monthStichdatum < monthDOB) {
			age = age - 1;
		}

		// Falls die Monate gleich sind: Dasselbe mit dem Tag
		if (monthStichdatum == monthDOB && dayStichdatum < dayDOB) {
			age = age - 1;
		}

		return age;
	}

	/**
	 * @return Letzte Tag des Folgequartals berechnet von Parameter date
	 */
	public static Date getDatumBisFolgequartal(final Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int currentQuartal = (cal.get(Calendar.MONTH) / 3) + 1;
		if (currentQuartal == 1) {
			return newDate(30, 6, cal.get(Calendar.YEAR));
		}
		if (currentQuartal == 2) {
			return newDate(30, 9, cal.get(Calendar.YEAR));
		}
		if (currentQuartal == 3) {
			return newDate(31, 12, cal.get(Calendar.YEAR));
		}
		return newDate(31, 3, cal.get(Calendar.YEAR) + 1);
	}

	/**
	 * @return Erste Tag des Folgequartals berechnet von Parameter date
	 */
	public static Date getDatumVonFolgequartal(final Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int currentQuartal = (cal.get(Calendar.MONTH) / 3) + 1;
		if (currentQuartal == 1) {
			return newDate(1, 4, cal.get(Calendar.YEAR));
		}
		if (currentQuartal == 2) {
			return newDate(1, 7, cal.get(Calendar.YEAR));
		}
		if (currentQuartal == 3) {
			return newDate(1, 10, cal.get(Calendar.YEAR));
		}
		return newDate(1, 1, cal.get(Calendar.YEAR) + 1);
	}
}
