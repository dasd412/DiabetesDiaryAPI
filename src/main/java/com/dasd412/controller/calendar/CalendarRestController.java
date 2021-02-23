package com.dasd412.controller.calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dasd412.controller.ApiResult;
import com.dasd412.utils.CalendarQuickstart;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalendarRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/api/diabetes/calendar/eventList")
    /*
    --JSON RequestBody Format--

    {
  "kind": "calendar#event",
  "calendarId":"yjhk0001@gmail.com"
     }

     -- Result --
     calendarId (구글 계정)의 저장된 모든 일정(Event 클래스)을 리턴함. 시작 시간 순으로 정렬됨. findAll()과 같음.
     */
    public ApiResult<List<Event>> calendarEventList(@RequestBody CalendarDTO dto) {
        logger.info("Calendar Event List : " + dto.toString());

        List<Event> items = new ArrayList<>();

        try {
            Calendar service = CalendarQuickstart.getCalendarService();
            Events events = service.events().list(dto.getCalendarId()).setOrderBy("startTime").setSingleEvents(true).execute();
            items = events.getItems();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return ApiResult.OK(items);
    }

    @GetMapping("/api/diabetes/calendar/event")
    /*
    --JSON RequestBody Format--

    {
  "kind": "calendar#event",
  "calendarId":"yjhk0001@gmail.com",
  "eventId":"6bn477to563bg3l8cqgrmln31l"
    }

     -- Result --
       calendarId (구글 계정)의 저장된 모든 일정(Event 클래스)들 중,
       EventId가 같은 일정만 리턴한다. 없으면 에러. findById()와 같음.

    --Reference--

    아래 eid
    "NmJuNDc3dG81NjNiZzNsOGNxZ3JtbG4zMWwgeWpoazAwMDFAbQ"를
    base-64 디코딩하면 "6bn477to563bg3l8cqgrmln31l"이 나온다.

     */

    public ApiResult<?>findCalendarEvent(@RequestBody CalendarDTO dto){
        logger.info("Calendar event for dto id :"+dto.toString());

        List<Event> items = new ArrayList<>();

        try {
            Calendar service = CalendarQuickstart.getCalendarService();
            Events events = service.events().list(dto.getCalendarId()).setOrderBy("startTime").setSingleEvents(true).execute();
            items = events.getItems();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        for(Event event:items){
            if(event.getId().equals(dto.getEventId())){
                return ApiResult.OK(event);
            }
        }

        return ApiResult.ERROR("event not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/diabetes/calendar/post")
    /*
        --JSON RequestBody Format--
     {
  "kind": "calendar#event",
  "calendarId":"yjhk0001@gmail.com",
   "summary":"postTest",
   "description":"mehtod test",
   "startDate":"2021-02-23",
   "startTime":"12:00",
   "endDate":"2021-02-23",
   "endTime":"14:00"
     }
     */
    public ApiResult<Map<String,Boolean>>postCalendar(@RequestBody CalendarDTO dto){
        logger.info("post calendar : "+dto.toString());

        boolean isCreated=false;
        try{
            Calendar service=CalendarQuickstart.getCalendarService();
            Event event=new Event().setSummary(dto.getSummary()).setDescription(dto.getDescription());

            DateTime startDateTime=new DateTime(dto.getStartDateTime());
            EventDateTime start=new EventDateTime().setDateTime(startDateTime).setTimeZone("Asia/Seoul");
            event.setStart(start);

            DateTime endDateTime=new DateTime(dto.getEndDateTime());
            EventDateTime end =new EventDateTime().setDateTime(endDateTime).setTimeZone("Asia/Seoul");
            event.setEnd(end);

            service.events().insert(dto.getCalendarId(),event).execute();

            isCreated=true;
        } catch (GeneralSecurityException | IOException | ParseException e) {
            e.printStackTrace();
        }
        Map<String, Boolean>map=new HashMap<>();
        map.put("isCreated",isCreated);
        return ApiResult.OK(map);
    }

    @DeleteMapping("/api/diabetes/calendar")
      /*
        --JSON RequestBody Format--
     {
  "kind": "calendar#event",
  "calendarId":"yjhk0001@gmail.com",
   "eventId":"13cg09jvch2mtondq07g3u8vks"
     }

     --Reference--
     base 64 디코딩
     MTNjZzA5anZjaDJtdG9uZHEwN2czdTh2a3MgeWpoazAwMDFAbQ ->13cg09jvch2mtondq07g3u8vks

     */
    public ApiResult<Map<String,Boolean>>deleteCalendarEvent(@RequestBody CalendarDTO dto){
        logger.info("delete calendar event : "+dto.toString());

        boolean isDeleted=false;

        try{
            Calendar service=CalendarQuickstart.getCalendarService();
            service.events().delete(dto.getCalendarId(),dto.getEventId()).execute();

            isDeleted=true;
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        Map<String,Boolean>map=new HashMap<>();
        map.put("isDeleted",isDeleted);

        return ApiResult.OK(map);
    }

    @PutMapping("/api/diabetes/calendar")
          /*
        --JSON RequestBody Format--
    {
   "kind": "calendar#event",
  "calendarId":"yjhk0001@gmail.com",
   "eventId":"2encm0gl0jkimsf80t6a1vji5s",
   "summary":"update test",
   "description":"test for update!"
     }

     --Reference--
     base 64 디코딩
     MmVuY20wZ2wwamtpbXNmODB0NmExdmppNXMgeWpoazAwMDFAbQ ->2encm0gl0jkimsf80t6a1vji5s

     */
    public ApiResult<Map<String,Boolean>>updateCalendar(@RequestBody CalendarDTO dto){
        logger.info("update calendar : "+dto.toString());

        boolean isUpdated=false;

        try{
            Calendar service=CalendarQuickstart.getCalendarService();
            Event event=service.events().get(dto.getCalendarId(),dto.getEventId()).execute();
            event.setSummary(dto.getSummary()).setDescription(dto.getDescription());
            service.events().update(dto.getCalendarId(),event.getId(),event).execute();

            isUpdated=true;

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        Map<String,Boolean>map=new HashMap<>();
        map.put("isUpdated",isUpdated);
        return ApiResult.OK(map);
    }


}
