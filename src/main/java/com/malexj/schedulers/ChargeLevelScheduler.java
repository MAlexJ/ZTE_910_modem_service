package com.malexj.schedulers;

import com.malexj.models.requests.MessageRequest;
import com.malexj.models.responses.BatteryResponse;
import com.malexj.services.ZteRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.malexj.controllers.ZteRestController.BATTERY_CHARGING_REQUEST;

@Log
@Component
@RequiredArgsConstructor
public class ChargeLevelScheduler {
    @Value("${scheduled.task.phone}")
    private String phoneNumber;
    @Value("${scheduled.task.low.level}")
    private int lowChargeLevel;

    private boolean isNotified;

    private final ZteRestService restService;

    @Scheduled(cron = "${scheduled.task.job.cron}")
    public void checkChargeLevel() {
        BatteryResponse info = restService.getInfo(BATTERY_CHARGING_REQUEST);
        int percent = parsePercent(info.getBatteryVolPercent());
        log.info("battery percent: " + percent);

        if (percent < lowChargeLevel && !isNotified) {
            String message = buildLowBatteryMessage(info);
            log.warning(message);
            restService.sendMessage(new MessageRequest(phoneNumber, message));
            isNotified = true;
            return;
        }

        if (percent > lowChargeLevel && isNotified) {
            String message = buildChargeRestoredMessage(info);
            log.info(message);
            restService.sendMessage(new MessageRequest(phoneNumber, buildChargeRestoredMessage(info)));
            isNotified = false;
        }
    }

    private String buildLowBatteryMessage(BatteryResponse info) {
        StringBuilder sb = new StringBuilder("Critically low battery level - ") //
                .append(info.getBatteryVolPercent()) //
                .append("charge your GSM modem!");
        return new String(sb);
    }

    private String buildChargeRestoredMessage(BatteryResponse info) {
        StringBuilder sb = new StringBuilder("Battery charge restored") //
                .append(", ")//
                .append("percent-")//
                .append(info.getBatteryVolPercent())//
                .append(", charging-")//
                .append(info.getBatteryCharging());
        return new String(sb);
    }

    private int parsePercent(String percent) {
        try {
            return Integer.parseInt(percent);
        } catch (NumberFormatException e) {
            log.warning("Can't parse battery percent!");
            return 0;
        }
    }

}