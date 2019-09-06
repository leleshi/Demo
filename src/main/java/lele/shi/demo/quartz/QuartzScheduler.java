package lele.shi.demo.quartz;

import java.util.*;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 任务调度处理
 * @author yvan
 *
 */
@Configuration
public class QuartzScheduler {
    // 任务调度
    @Autowired
    private Scheduler scheduler;

    /**
     * 开始执行所有任务
     *
     * @throws SchedulerException
     */
    public void startJob() throws SchedulerException {
        startJob1(scheduler);
        startJob2(scheduler);
        scheduler.start();
    }

    /**
     * 获取某个Job信息
     *
     * @param name
     * @return
     * @throws SchedulerException
     */
    public String getJobInfo(String name) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 获取所有job信息
     * @return
     * @throws SchedulerException
     */
    public List<Map<String, Object>> getJobInfoAll() throws SchedulerException {
        QuartzEnum[] quartzEnums = QuartzEnum.values();
        List<Map<String, Object>> maps = new ArrayList<>();
        for (QuartzEnum quartzEnum : quartzEnums) {
            String index = quartzEnum.getIndex();
            String name = quartzEnum.getName();
            TriggerKey triggerKey = new TriggerKey(index);
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            Map<String, Object> map = new HashMap<>();
            map.put("index", index);
            map.put("name", name);
            map.put("time", cronTrigger.getCronExpression());
            map.put("state", scheduler.getTriggerState(triggerKey).name());
            maps.add(map);
        }
        return maps;
    }

    /**
     * 修改某个任务的执行时间
     *
     * @param name
     * @param time
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(String name, String time) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(time)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name)
                    .withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @throws SchedulerException
     */
    public void pauseJob(String name) throws SchedulerException {
        JobKey jobKey = new JobKey(name);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param name
     * @throws SchedulerException
     */
    public void resumeJob(String name) throws SchedulerException {
        JobKey jobKey = new JobKey(name);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null){
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @throws SchedulerException
     */
    public void deleteJob(String name) throws SchedulerException {
        JobKey jobKey = new JobKey(name);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null){
            return;
        }
        scheduler.deleteJob(jobKey);
    }

    private void startJob1(Scheduler scheduler) throws SchedulerException {
        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        // JobDetail 是具体Job实例
        JobDetail jobDetail = JobBuilder.newJob(SchedulerQuartzJob1.class).withIdentity("job1").build();
        // 基于表达式构建触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        // CronTrigger表达式触发器 继承于Trigger
        // TriggerBuilder 用于构建触发器实例
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job1")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    private void startJob2(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SchedulerQuartzJob2.class).withIdentity("job2").build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job2")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}
