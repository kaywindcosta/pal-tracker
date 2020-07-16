package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    private final DistributionSummary timeEntrySummary;

    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository,
                               MeterRegistry meterRegistry) {
        this.timeEntryRepository=timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }
    @PostMapping("/time-entries")
    public ResponseEntity <TimeEntry>create(@RequestBody TimeEntry timeEntryToCreate) {
        return new ResponseEntity<TimeEntry>(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry result =timeEntryRepository.find(timeEntryId);
        if(null!= result) {
            return new ResponseEntity<TimeEntry>(result, HttpStatus.MULTI_STATUS.OK);
        }else{
            return new ResponseEntity<TimeEntry>( HttpStatus.MULTI_STATUS.NOT_FOUND);
        }
    }
    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> result=timeEntryRepository.list();
        return new ResponseEntity<List<TimeEntry>>(result, HttpStatus.MULTI_STATUS.OK);
    }
    @PutMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry result =timeEntryRepository.update(id, timeEntry);
        if(null!= result) {
            return new ResponseEntity<TimeEntry>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<TimeEntry>( HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
}
