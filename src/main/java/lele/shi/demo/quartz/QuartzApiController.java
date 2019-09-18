package lele.shi.demo.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

/**
 * 这里并没有采用restful风格 只是简单封装了一下api
 *
 * @author yvan
 *
 */
@RestController
@RequestMapping("/quartz")
public class QuartzApiController {
    @Autowired
    private QuartzScheduler quartzScheduler;

    @RequestMapping("/start")
    public void startQuartzJob() {
        try {
            quartzScheduler.startJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/info")
    public String getQuartzJob(String name) {
        String info = null;
        try {
            info = quartzScheduler.getJobInfo(name);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return info;
    }

    @RequestMapping("/infoAll")
    public List<Map<String, Object>> getQuartzJobAll() {
        try {
            return quartzScheduler.getJobInfoAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping("/modify")
    public boolean modifyQuartzJob(String name, String time) {
        boolean flag = true;
        try {
            flag = quartzScheduler.modifyJob(name, time);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @RequestMapping(value = "/pause")
    public void pauseQuartzJob(String name) {
        try {
            quartzScheduler.pauseJob(name);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/pauseAll")
    public void pauseAllQuartzJob() {
        try {
            quartzScheduler.pauseAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/resumeJob")
    public void resumeJob(String name) {
        try {
            quartzScheduler.resumeJob(name);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/resumeAllJob")
    public void resumeAllJob() {
        try {
            quartzScheduler.resumeAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/delete")
    public void deleteJob(String name) {
        try {
            quartzScheduler.deleteJob(name);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        double d = 1.03;
        double a = 0.45;
        System.out.println(d-a);
    }

}

