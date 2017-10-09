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
