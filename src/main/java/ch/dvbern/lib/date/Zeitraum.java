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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Zeitraum implements Comparable<Zeitraum>, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6911730704492703726L;

	private final DateFormat LONG_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	private final DateFormat SHORT_FORMAT = new SimpleDateFormat("dd.MM.");

	private Date von;
	private Date bis;

	/**
	 * Erstellt einen neuen Zeitraum.
	 *
	 * @param von Datum von wann an der Zeitraum gilt (inklusive), ohne Zeitinformation
	 * @param bis Datum bis wann der Zeitraum gilt (inklusive), ohne Zeitinformation
	 */
	public Zeitraum(Date von, Date bis) {
		if (von == null) {
			throw new IllegalArgumentException("von ist null");
		}
		if (bis == null) {
			throw new IllegalArgumentException("bis ist null");
		}
		if (von.after(bis)) {
			throw new IllegalArgumentException("von (" + von + ") muss gleich oder vor bis (" + bis
					+ ") sein!");
		}
		this.von = von;
		this.bis = bis;
	}

	/**
	 * @return the von
	 */
	public Date getVon() {
		return von;
	}

	/**
	 * @return the bis
	 */
	public Date getBis() {
		return bis;
	}


	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Zeitraum zeitraum) {

		if (zeitraum == null) {
			return 1;
		}

		int value = von.compareTo(zeitraum.getVon());
		if (value == 0) {
			return bis.compareTo(zeitraum.getBis());
		} else {
			return value;
		}
	}

	/**
	 * Spaltet den {@link Zeitraum} in mehrere Unter-Zeiträume auf, welche je einen(ganzen oder angebrochenen) Monat
	 * beinhalten.
     * <p/>
     * Für Zeiträume welche genau einem Monat entsprechen oder unter einem Monat sind wird eine leere Liste zurückgegeben.
     *
	 *
	 * @return ein Array mit dem aufgesplitteten Zeitraum. Leere Liste wenn der Zeitraum kleiner oder gleich einem Monat ist.
	 */
	public Collection<Zeitraum> splitByMonth() {
		List<Date> ersteDesMonats = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(von);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		while (!cal.getTime().after(bis)) {
			ersteDesMonats.add(cal.getTime());
			cal.add(Calendar.MONTH, 1);
		}
		return split(ersteDesMonats.toArray(new Date[ersteDesMonats.size()]));
	}

	/**
	 * Spaltet den {@link Zeitraum} in mehrere Unter-Zeiträume auf, je nach übergebenen splitStartDates. Für jedes Datum
	 * in splitStartDate wird wie folgt vorgegangen:
	 * <p>
	 * <li>wenn das aktuell betrachtete Datum nicht innerhalb des Zeitraumes ist, wird es nicht verwendet.
	 * <li>wenn das aktuell betrachtete Datum innerhalb des Zeitraumes ist, wird der Zeitraum aufgesplittet in 2
	 * Zeiträume: 1.Zeitraum von Beginn ursprünglichem Zeitraum bis (aktuellesDatum -1 Tag), 2. Zeitraum aktueller Tag
	 * bis Ende ursprünglicher Zeitraum.
	 * <p>
	 * Beispiel: Gegeben:
	 * <li>Zeitraum z vom 1.1.2008 bis 31.3.2008.
	 * <li>Date d1 mit Wert "15.2.2008"
	 * <li>Date d2 mit Wert "27.3.2008"
	 *
	 * z.split(d1, d2) reultiert in 3 Zeiträume: 1.1.2008 -14.2.2008, 15.2.2008 - 26.3.2008, 27.3.2008 - 31.3.2008
	 *
	 * @param splitStartDates
	 * @return
	 */
	protected Collection<Zeitraum> split(Date... splitStartDates) {
		return split(false, splitStartDates);
	}


	/**
	 * Spaltet den {@link Zeitraum} in mehrere Unter-Zeiträume auf, je nach übergebenen splitStartDates. Für jedes Datum
	 * in splitStartDate wird wie folgt vorgegangen:
	 * <p>
	 * <li>wenn das aktuell betrachtete Datum nicht innerhalb des Zeitraumes ist, wird es nicht verwendet.
	 * <li>wenn das aktuell betrachtete Datum innerhalb des Zeitraumes ist, wird der Zeitraum aufgesplittet in 2
	 * Zeiträume: 1.Zeitraum von Beginn ursprünglichem Zeitraum bis (aktuellesDatum -1 Tag), 2. Zeitraum aktueller Tag
	 * bis Ende ursprünglicher Zeitraum.
	 * <p>
	 * Beispiel: Gegeben:
	 * <li>Zeitraum z vom 1.1.2008 bis 31.3.2008.
	 * <li>Date d1 mit Wert "15.2.2008"
	 * <li>Date d2 mit Wert "27.3.2008"
	 *
	 * z.split(d1, d2) reultiert in 3 Zeiträume: 1.1.2008 -14.2.2008, 15.2.2008 - 26.3.2008, 27.3.2008 - 31.3.2008
	 *
	 * @param ignoreBoundaryDates Wenn <code>false</code> werden auch Unterzeiträume für Daten erstellt die gleich dem
	 * Start oder Ende des aktuellen Zeitraumes sind
	 * @param splitStartDates
	 * @return
	 */
	protected Collection<Zeitraum> split(boolean ignoreBoundaryDates, Date... splitStartDates) {
		List<Date> splitStartDatesList = Arrays.asList(splitStartDates);
		Collections.sort(splitStartDatesList);
		List<Zeitraum> retVal = new ArrayList<Zeitraum>();
		Date startDate = this.von;
		for (Date splitStartDate : splitStartDatesList) {
			if (!contains(splitStartDate) || (ignoreBoundaryDates && (splitStartDate.equals(von) || splitStartDate.equals(bis)))) {
				continue;
			}
			Date datumBis = DateHelper.addDays(splitStartDate, -1);
			if (datumBis.before(startDate)) {
				datumBis = startDate;
			}
			retVal.add(new Zeitraum(startDate, datumBis));
			startDate = splitStartDate;
		}
		if (startDate.after(von) && (!startDate.after(bis))) {
			retVal.add(new Zeitraum(startDate, bis));
		}

		return retVal;
	}

	/**
	 * Gibt die Anzahl Tage zurück, welche dieser Zeitraum dauert. Enddatum ist inklusiv.
	 *
	 * @return
	 */
	public int getDuationInDays() {

		int duration = 0;
		int yearStart = DateHelper.getYear(von);
		int yearEnd = DateHelper.getYear(bis);
		for (int i = 0; i < yearEnd - yearStart + 1; i++) {
			Date from = (i == 0 ? von : DateHelper.newDate(1, 1, yearStart + i));
			Date to = (i == yearEnd - yearStart ? bis : DateHelper.newDate(31, 12, DateHelper.getYear(from)));
			duration += getDurationSameYear(from, to);
		}
		return duration;
	}

	private int getDurationSameYear(Date from, Date to) {

		return DateHelper.get(Calendar.DAY_OF_YEAR, to) - DateHelper.get(Calendar.DAY_OF_YEAR, from) + 1;
	}

	/**
	 * Gibt <code>true</code> zurück wenn mindestens 1 Tag des gegebenen Zeitraumes mit diesem Zeitraum überschneidet.
	 *
	 * @param other
	 * @return
	 */
	public boolean intersects(Zeitraum other) {

		return !(this.bis.before(other.getVon()) || this.von.after(other.getBis()));

	}


	/**
	 * Gibt an, ob das gegebene Datum innerhalb des Zeitraumes ist, d.h. date >= datumVon && date <= datumBis
	 *
	 * @param date
	 * @return
	 */
	public boolean contains(Date date) {

		return !date.before(von) && !date.after(bis);
	}


	/**
	 * Entfernt alle gegebenen (Unter-)Zeiträume aus diesem Zeitraum.
	 * <p>
	 * Beispiel:
	 * <p>
	 * <code><pre>
	 * Zeitraum z1; //z1 ist vom 1.1.2009 bis 31.1.2009
	 * Zeitraum februar2009; // 1.2.2009 bis 28.2.2009
	 * Zeitraum drittesQuartal2009; // 1.7.2009 bis 30.9.2009
	 * List&lt;Zeitraum&gt; subtracted = z1.subtract(februar2009, drittesQuartal2009);
	 * </pre></code> Zeitraum Im obigen Beispiel beinhaltet die Liste subtracted 3 Zeiträume: 1.1.2009 - 31.12.2009,
	 * 1.3.2009 - 30.6.2009 und 1.10.2009 - 31.12.2009.
	 *
	 *
	 * @param unterzeitraeume
	 * @return eine Liste von übriggebliebenen Zeiträumen.
	 */
	public List<Zeitraum> subtract(Zeitraum... unterzeitraeume) {

		Arrays.sort(unterzeitraeume);
		List<Zeitraum> retVal = new ArrayList<Zeitraum>();
		Zeitraum left = this;

		for (Zeitraum uz : unterzeitraeume) {
			if (uz.getVon().after(left.getBis()) || uz.getBis().before(left.getVon())) {
				continue;
			}
			if (uz.getVon().before(left.getVon()) && uz.getBis().after(left.getBis())) {
				return new ArrayList<Zeitraum>();
			}
			List<Zeitraum> splitted = (List<Zeitraum>) left.split(true, uz.getVon(), uz.getBis());

			if (!uz.getVon().after(left.getVon())) {
				if (splitted.size() > 0) {
					Zeitraum lastSplitted = splitted.get(splitted.size() - 1);
					left = new Zeitraum(DateHelper.addDays(lastSplitted.getVon(), 1), lastSplitted.getBis());
				} else if (splitted.isEmpty()) {
					return retVal;
				}
			} else if (!uz.getBis().before(left.getBis())) {
				retVal.add(new Zeitraum(left.getVon(), DateHelper.addDays(uz.getVon(), -1)));
				return retVal;
			} else {
				retVal.add(splitted.get(0));
				Date leftVon, leftBis = null;
				Zeitraum leftReferenceZeitraum = null;
				if (splitted.size() > 2) {
					leftReferenceZeitraum = splitted.get(2);
				} else {
					leftReferenceZeitraum = splitted.get(1);
				}
				leftVon = DateHelper.addDays(leftReferenceZeitraum.getVon(), 1);
				leftBis = leftReferenceZeitraum.getBis();
				left = new Zeitraum(leftVon, leftBis);
			}

		}
		retVal.add(left);
		return retVal;
	}


	/**
	 * Gibt die Differenz in Milisekunden zwischen Start oder Ende dieses Zeitraumes und dem gegebenen Datum wie folgt
	 * zurück:
	 * <ul>
	 * <li>Wenn <tt>date</tt> in diesem Zeitraum enthalten ist wird 0 zurückgegeben
	 * (siehe {@link #contains(java.util.Date)})</li>
	 * <li>Wenn <tt>date</tt> vor dem Beginn dieses Zeitraumes ist wird date.getTime() - von.getTime() zurückgegeben, der Rückgabewert wird also negativ.</li>
	 * <li>Wenn <tt>date</tt> nach dem Ende dieses Zeitraumes ist wird date.getTime() - bis.getTime() zurückgegeben, der Rückgabewert wird also positiv.</li>
	 * </ul>
	 *
	 * @param date das zu vergleichende Datum, darf nicht <code>null</code> sein.
	 * @return die Differenz in Milisekunden zum Beginn oder zum Ende des Zeitraumes, oder 0 wenn <tt>date</tt> in diesem
	 * Zeitaum enthalten ist.
	 */
	public long getDistanceFrom(Date date) {
		long returnValue = 0;
		if (date.before(von)) {
			returnValue = date.getTime() - von.getTime();
		} else if (date.after(bis)) {
			returnValue = date.getTime() - bis.getTime();
		}
		return returnValue;
	}


	/**
	 * Definiert wie ein Zeitraum als String ausgegeben wird:
	 *
	 * <ul>
	 *
	 * <li> Wenn der Zeitraum innerhalb eines Jahres ist, wird der Zeitraum im Format "dd.MM - dd.MM.yy" angegeben.
	 * <li>Wenn der Zeitraum über eine Jahresgrenze hinaus ist, wird der Zeitraum im Format "dd.MM.yyyy - dd.MM.yyyy" angegeben.
	 * </ul>
	 */
	@Override
	public String toString() {
		DateFormat toDateFormat = LONG_FORMAT;
		DateFormat fromDateFormat = null;
		if (DateHelper.getYear(von) != DateHelper.getYear(bis)) {
			fromDateFormat = LONG_FORMAT;
		} else {
			fromDateFormat = SHORT_FORMAT;
		}
		return MessageFormat.format("{0} - {1}", fromDateFormat.format(von), toDateFormat.format(bis));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + bis.hashCode();
		result = prime * result + von.hashCode();
		return result;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Zeitraum)) {
			return false;
		}
		final Zeitraum other = (Zeitraum) obj;
		return bis.equals(other.bis) && von.equals(other.von);
	}
}
