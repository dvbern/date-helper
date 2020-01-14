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
package ch.dvbern.lib.date.converters;

import java.time.LocalTime;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Konvertiert ein LocalTime Java 8 Objekt in einen String fuer JSON
 */
@XmlJavaTypeAdapter(value = LocalTimeXMLConverter.class, type = LocalTime.class)
public class LocalTimeXMLConverter extends XmlAdapter<String, LocalTime>  {

	@Nullable
	@Override
	public LocalTime unmarshal(String v) {
		return isEmpty(v) ? null : LocalTime.parse(v);
	}

	@Nullable
	@Override
	public String marshal(LocalTime v) {
		return v == null ? null : v.toString();
	}

	private boolean isEmpty(String v) {
		return (v == null || v.isEmpty());
	}
}
