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

import java.util.Date;

/**
 * Klasse zum Speichern von Kalenderinformationen zu Feiertagen
 */
public class Feiertag_CH extends Date {

	private static final long serialVersionUID = -4802375675317099968L;

	private final FeiertagSchweiz feiertag;

	/**
	 * Constructor
	 */
	Feiertag_CH(FeiertagSchweiz feiertagSchweiz, long timeInMillis) {
		super(timeInMillis);
		this.feiertag = feiertagSchweiz;
	}

	/**
	 * @return FeiertagSchweiz
	 */
	public FeiertagSchweiz getFeiertag() {
		return feiertag;
	}

}
