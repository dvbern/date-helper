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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Feiertage Schweiz pro Jahr. Berechnet Feiertage eines Jahres und speichert
 * die gewonnenen Informationen in einer Vector-Collection von
 * Feiertag-Objekten.
 */
class FeiertageCH {

	private final List<FeiertagCH> feiertage = new ArrayList<>(10);

	/**
	 * constructor
	 */
	FeiertageCH(int year) {

		// Bewegliche Feiertagen
		// Ostern (sonntag) vorab berechnen
		GregorianCalendar eastern = getOudinEastern(year);
		GregorianCalendar tmp;

		tmp = (GregorianCalendar) eastern.clone();
		tmp.add(Calendar.DAY_OF_MONTH, -2);
		feiertage.add(new FeiertagCH(FeiertagSchweiz.KARFREITAG, tmp
			.getTimeInMillis()));

		tmp = (GregorianCalendar) eastern.clone();
		feiertage.add(new FeiertagCH(FeiertagSchweiz.OSTERN, tmp
			.getTimeInMillis()));
		tmp.add(Calendar.DAY_OF_MONTH, +1);
		feiertage.add(new FeiertagCH(FeiertagSchweiz.OSTERMONTAG, tmp
			.getTimeInMillis()));

		tmp = (GregorianCalendar) eastern.clone();
		tmp.add(Calendar.DAY_OF_MONTH, +39);
		feiertage.add(new FeiertagCH(FeiertagSchweiz.AUFFAHRT, tmp
			.getTimeInMillis()));

		tmp = (GregorianCalendar) eastern.clone();
		tmp.add(Calendar.DAY_OF_MONTH, +49);
		feiertage.add(new FeiertagCH(FeiertagSchweiz.PFINGSTEN, tmp
			.getTimeInMillis()));
		tmp.add(Calendar.DAY_OF_MONTH, +1);
		feiertage.add(new FeiertagCH(FeiertagSchweiz.PFINGSTMONTAG, tmp
			.getTimeInMillis()));

		// Fixe Feiertagen
		feiertage.add(new FeiertagCH(FeiertagSchweiz.NEUJAHRSTAG,
			(new GregorianCalendar(year, 0, 1)).getTimeInMillis()));
		feiertage.add(new FeiertagCH(FeiertagSchweiz.BECHTOLDSTAG,
			(new GregorianCalendar(year, 0, 2)).getTimeInMillis()));
		feiertage.add(new FeiertagCH(FeiertagSchweiz.NATIONALFEIERTAG,
			(new GregorianCalendar(year, 7, 1)).getTimeInMillis()));
		feiertage.add(new FeiertagCH(FeiertagSchweiz.WEIHNACHTEN,
			(new GregorianCalendar(year, 11, 25)).getTimeInMillis()));
		feiertage.add(new FeiertagCH(FeiertagSchweiz.STEPHANSTAG,
			(new GregorianCalendar(year, 11, 26)).getTimeInMillis()));
	}

	/**
	 * Berechnung Ostersonntag nach Oudin. Source: Das Java Codebook. Dirk
	 * Louis, Peter M�ller. Addison-Wesley
	 */
	private static GregorianCalendar getOudinEastern(int year) {

		int c = year / 100;
		int n = year - 19 * (year / 19);
		int k = (c - 17) / 25;
		int l1 = c - c / 4 - (c - k) / 3 + 19 * n + 15;
		int l2 = l1 - 30 * (l1 / 30);
		int l3 = l2 - (l2 / 28)
			* (1 - (l2 / 28) * (29 / (l2 + 1)) * ((21 - n) / 11));
		int a1 = year + year / 4 + l3 + 2 - c + c / 4;
		int a2 = a1 - 7 * (a1 / 7);
		int l = l3 - a2;
		int month = 3 + (l + 40) / 44;
		int day = l + 28 - 31 * (month / 4);
		return new GregorianCalendar(year, month - 1, day);
	}

	/**
	 * Liefert das Feiertag-Objekt zu einem Feiertag
	 *
	 * @return Feiertag oder null
	 */
	FeiertagCH getFeiertag(FeiertagSchweiz feiertag) {

		for (FeiertagCH tag : feiertage) {
			if (tag.getFeiertag().equals(feiertag)) {
				return tag;
			}
		}
		return null;
	}

	/**
	 * Liefert das Feiertag-Objekt oder null zu einem Datum.
	 *
	 * @param date . Zeit muss auf 0 sein.
	 */
	FeiertagCH getFeiertag(GregorianCalendar date) {

		long millis = date.getTimeInMillis();
		for (FeiertagCH tag : feiertage) {
			if (tag.getTime() == millis) {
				return tag;
			}
		}
		return null;
	}

	/**
	 * Stellt fest, ob das angegebene Datum auf einen gesetzlichen,
	 * schweizerischen nationalen Feiertag f�llt
	 *
	 * @param date . Zeit muss auf 0 sein.
	 */
	boolean isFeiertag(GregorianCalendar date) {

		long millis = date.getTimeInMillis();
		for (FeiertagCH tag : feiertage) {
			if (tag.getTime() == millis) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	List<FeiertagCH> getFeiertage() {

		return  new ArrayList<>(feiertage);
	}
}
