package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {


    public List<TimeEntry> timeEntries= new ArrayList<>();
    public long nextId=1;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        TimeEntry entry=new TimeEntry(nextId++,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
        timeEntries.add(entry);
        return entry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return timeEntries.stream()
                .filter(id -> id.getId() == timeEntryId)
                .findAny()
                .orElse(null);
    }

    @Override
    public List<TimeEntry> list() {
        return timeEntries;
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {

//        TimeEntry entry=timeEntries.stream()
//                .filter(id -> id.getId() == timeEntryId)
//                .findAny().orElse(null);
        for (TimeEntry entry : timeEntries) {
            if(entry.getId()==timeEntryId){
                entry.setProjectId(timeEntry.getProjectId());
                entry.setDate(timeEntry.getDate());
                entry.setHours(timeEntry.getHours());
                entry.setUserId(timeEntry.getUserId());
                return entry;
            }

        }


        return null;
    }

    @Override
    public void delete(long timeEntryId) {
        timeEntries.remove(timeEntries.stream()
                .filter(id -> id.getId() == timeEntryId)
                .findAny().orElse(null));
    }
}
