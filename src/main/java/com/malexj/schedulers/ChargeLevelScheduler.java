package com.malexj.schedulers;

import com.malexj.models.responses.BatteryResponse;
import com.malexj.services.ZteRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.malexj.controllers.ZteRestController.BATTERY_CHARGING_REQUEST;

@Log
@Component
@RequiredArgsConstructor
public class ChargeLevelScheduler {

    private final ZteRestService restService;

    @Scheduled(cron = "${scheduled.task.job.cron}")
    public void checkChargeLevel() {
        BatteryResponse info = restService.getInfo(BATTERY_CHARGING_REQUEST);
        String batteryVolPercent = info.getBatteryVolPercent();
        log.info(" >>> " + batteryVolPercent);
    }

}