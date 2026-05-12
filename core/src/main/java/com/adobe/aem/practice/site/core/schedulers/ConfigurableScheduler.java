package com.adobe.aem.practice.site.core.schedulers;

import com.adobe.aem.practice.site.core.config.SchedulerConfiguration;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd=SchedulerConfiguration.class)
public class ConfigurableScheduler implements Runnable{
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurableScheduler.class);

    @Reference
    Scheduler scheduler;
    private String schedulerName;
    private String cronExpression;
    private boolean enabled;


    @Activate
    @Modified
    protected void activate(SchedulerConfiguration config) {
        schedulerName= config.schedulerName();
        cronExpression= config.cronExpression();
        enabled= config.enabled();

        if(enabled){
            LOG.debug("Configurable Scheduler is Enabled");
            LOG.debug("Starting scheduler: {}", schedulerName);
            ScheduleOptions scheduleOptions=scheduler.EXPR(cronExpression);
            scheduleOptions.name(schedulerName);
            scheduleOptions.canRunConcurrently(false);

            scheduler.schedule(this, scheduleOptions);
        }else{
            LOG.debug("Scheduler is disabled");
        }
    }

    @Deactivate
    protected void deactivate() {
        scheduler.unschedule(schedulerName);
        LOG.info("Scheduler stopped");
    }
    @Override
    public void run() {
        LOG.debug("Configurable Scheduler Run Method");
        LOG.debug("Scheduler running at {}", System.currentTimeMillis());
    }
}
