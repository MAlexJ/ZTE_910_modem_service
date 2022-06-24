package com.malexj.models.responses;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * Response model:
 * {
 * "battery_charging": "0",
 * "battery_vol_percent": "62",
 * "battery_pers": "3"
 * }
 */
@Data
public class BatteryResponse {
    @JsonAlias("battery_charging")
    private String batteryCharging;

    @JsonAlias("battery_vol_percent")
    private String batteryVolPercent;

    @JsonAlias("battery_pers")
    private String batteryPercent;

}
