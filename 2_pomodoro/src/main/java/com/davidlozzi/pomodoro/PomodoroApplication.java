package com.davidlozzi.pomodoro;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Service
public class PomodoroApplication {
  // this is a tough one, I'm not sure I'd put a timer job like this in an API
  // can't push or ping a user timer is up, that would be the responsibility of
  // a consuming app to ping the /status API to know when it's done
  // of course, the FE could handle the entire count down, an API for a timer is
  // overkill but I want to learn more about Java, so here we go!

  public static void main(String[] args) {
    SpringApplication.run(PomodoroApplication.class, args);
  }

  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/start")
  @ResponseBody
  public ResponseEntity start() {
    try {
      long timerId = counter.incrementAndGet();
      Timer newTimer = new Timer(timerId, LocalDateTime.now().toString(), 25);
      JsonUtil.addTimer(Long.toString(timerId), newTimer);
      newTimer.add(linkTo(methodOn(PomodoroApplication.class).start()).withSelfRel());
      newTimer
          .add(linkTo(methodOn(PomodoroApplication.class).status(Long.toString(newTimer.getId()))).withRel("status"));
      return new ResponseEntity<>(newTimer, HttpStatus.OK);
    } catch (Exception ex) {
      System.out.print(ex);
      RuntimeException en = new RuntimeException(ex.toString());
      return new ResponseEntity<>(en, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/status")
  @ResponseBody
  public ResponseEntity status(@RequestParam(value = "id", defaultValue = "") String id) {
    try {
      if (!id.equals(null) && !id.equals("")) {
        long timerId = Long.parseLong(id);
        Timer timer = JsonUtil.getTimer(timerId);
        timer.add(linkTo(methodOn(PomodoroApplication.class).status(Long.toString(timer.getId()))).withSelfRel());
        return new ResponseEntity<>(timer, HttpStatus.OK);
      } else {
        throw new Exception("No id provided");
      }
    } catch (Exception ex) {
      System.out.print(ex);
      RuntimeException en = new RuntimeException(ex.toString());
      return new ResponseEntity<>(en, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
