/*
 * Copyright (c) 2015 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschuetzt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulaessig. Dies gilt
 * insbesondere fuer Vervielfaeltigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht uebergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 */
package ch.dvbern.lib.date.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Konvertiert ein LocalDateTime Java 8 Objekt in einen String fuer JSON
 */
@XmlJavaTypeAdapter(value = LocalDateTimeXMLConverter.class, type = LocalDateTime.class)
public class LocalDateTimeXMLConverter extends XmlAdapter<String, LocalDateTime> {

	@Nullable
	@Override
	public LocalDateTime unmarshal(String v) {
		return isEmpty(v) ? null : LocalDateTime.parse(v);
	}

	private boolean isEmpty(String v) {
		return (v == null || v.isEmpty());
	}

	@Nullable
	@Override
	public String marshal(LocalDateTime v) {
		return v == null ? null : v.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
	}
}
