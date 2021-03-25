package com.sos.thread;

import java.util.Observable;

public class RepeatingTask extends Observable {
    private RepeatingTaskName task;
    private long repeatMillisInterval;
    private long nextRepeatMillis;

    public void setNextRepeatMillis(long newRepeatMillis) {
        this.nextRepeatMillis = newRepeatMillis + repeatMillisInterval;
    }

    public RepeatingTaskName getTask() {
        return task;
    }

    public long getNextRepeatMillis() {
        return nextRepeatMillis;
    }

    public RepeatingTask(RepeatingTaskName task, long repeatMillisInterval) {
        this.task = task;
        this.repeatMillisInterval = repeatMillisInterval;
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}
