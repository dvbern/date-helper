/*
 * Copyright 2015. DV Bern AG
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

import java.time.LocalDate;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Konvertiert ein LocalDate Java 8 Objekt in einen String fuer JSON
 */
@XmlJavaTypeAdapter(value = LocalDateXMLConverter.class, type = LocalDate.class)
public class LocalDateXMLConverter extends XmlAdapter<String, LocalDate>  {

	@Nullable
	@Override
	public LocalDate unmarshal(String v) {
		return isEmpty(v) ? null : LocalDate.parse(v);
	}

	private boolean isEmpty(String v) {
		return (v == null || v.isEmpty());
	}

	@Nullable
	@Override
	public String marshal(LocalDate v) {
		return v == null ? null : v.toString();
	}
}
