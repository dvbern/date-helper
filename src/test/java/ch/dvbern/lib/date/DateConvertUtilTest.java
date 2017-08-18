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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author HOMA
 */
public class DateConvertUtilTest extends TestCase {


    private Date now = new Date();

    public void testConvertAsInstant(){
        Instant nowAsInstant = DateConvertUtils.asInstant(now);
        Date convertedBack = DateConvertUtils.asUtilDate(nowAsInstant);
        Assert.assertEquals(now, convertedBack);

    }

    public void testConvertAsLocalDate(){
        //Datum ohne Zeit
        LocalDate nowAsLocalDate = DateConvertUtils.asLocalDate(DateHelper.stripTime(now));
        Date convertedBack = DateConvertUtils.asUtilDate(nowAsLocalDate);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(now);
        Assert.assertNotNull(convertedBack);
        cal2.setTime(convertedBack);
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        Assert.assertTrue(sameDay);

    }


    public void testConvertAsLocalDateTime(){
        LocalDateTime nowAsLocalDateTime = DateConvertUtils.asLocalDateTime(now);
        Date convertedBack = DateConvertUtils.asUtilDate(nowAsLocalDateTime);
        Assert.assertEquals(now, convertedBack);

    }


    public void testConvertAsZonedDateTime(){
        ZonedDateTime zonedDateTime = DateConvertUtils.asZonedDateTime(now);
        Date convertedBack = DateConvertUtils.asUtilDate(zonedDateTime);
        Assert.assertEquals(now, convertedBack);

    }
}
