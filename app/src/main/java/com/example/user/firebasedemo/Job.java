package com.example.user.firebasedemo;

/**
 * Created by User on 8/18/2017.
 */

public class Job {
    String jobId;
    String jobName;
    public Job(){

    }

    public Job(String jobId, String jobName) {
        this.jobId = jobId;
        this.jobName = jobName;
    }

    public String getJobId() {
        return jobId;
    }

    public String getJobName() {
        return jobName;
    }
}
