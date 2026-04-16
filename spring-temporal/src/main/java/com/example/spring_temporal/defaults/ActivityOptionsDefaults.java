package com.example.spring_temporal.defaults;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class ActivityOptionsDefaults {

  public RetryOptions retryOptions(
    Duration initialInterval,
    double backoffCoefficient,
    Duration maximumInterval,
    int maximumAttempts
  ) {
    return RetryOptions.newBuilder()
      .setInitialInterval(initialInterval)
      .setBackoffCoefficient(backoffCoefficient)
      .setMaximumInterval(maximumInterval)
      .setMaximumAttempts(maximumAttempts)
      .build();
  }

  public ActivityOptions activityOptions(
    Duration startToCloseTimeout,
    RetryOptions retryOptions
  ) {
    return ActivityOptions.newBuilder()
      .setStartToCloseTimeout(startToCloseTimeout)
      .setRetryOptions(retryOptions)
      .build();
  }

  public ActivityOptions defaultOptions() {
    var retryOptions = retryOptions(
      Duration.ofSeconds(1),
      2.0,
      Duration.ofSeconds(30),
      5
    );

    return activityOptions(
      Duration.ofSeconds(10),
      retryOptions
    );
  }
}
