package lele.shi.demo.quartz;

/**
 * 定时任务枚举
 */
public enum QuartzEnum {
    JOB1("job1","任务1"),
    JOB2("job2","任务2");

    private String index;
    private String name;

    QuartzEnum(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
