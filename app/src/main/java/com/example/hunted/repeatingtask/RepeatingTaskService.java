package com.example.hunted.repeatingtask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class RepeatingTaskService extends Service {
    private final String URL = "https://apihuntedsos.herokuapp.com/";
    private final long DELAY = 200;

    private RequestQueue queue;
    private ArrayList<RepeatingTask> repeatingTasks;

    private AtomicBoolean working = new AtomicBoolean(true);

    private final Runnable runnable = () -> {
        while(working.get()) {
            long now = System.currentTimeMillis();
            for(RepeatingTask repeatingTask : repeatingTasks){
                if(repeatingTask.getNextRepeatMillis() <= now){
                    // Start repeating task
                    this.startRepeatingTask(repeatingTask);
                    // Update next repeat
                    repeatingTask.setNextRepeatMillis(now);
                }
            }
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void addRepeatingTask(RepeatingTask repeatingTask){
        this.repeatingTasks.add(repeatingTask);
    }

    public void removeRepeatingTask(RepeatingTask repeatingTask){
        for(RepeatingTask repeatingTaskItem : repeatingTasks){
            if(repeatingTaskItem.getTask() == repeatingTask.getTask()){
                repeatingTasks.remove(repeatingTaskItem);
            }
        }
    }

    // Switch statement
    private void startRepeatingTask(RepeatingTask task){
        switch(task.getTask()){
            case CHECK_ARRESTED:
                this.checkArrested(task);
        }
    }

    // REPEATING TASK METHODS

    private void checkArrested(RepeatingTask task) {
        /*TODO API Call doesn't exist yet.
           - should be 'URL + "player/" + id' */
        final String getArrestedUrl = URL + "player";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getArrestedUrl,
                response -> {
                    //Observable
                    task.notifyObservers(response.length()  + " (count)");
                }, error -> {
            //Bad request :(
            task.notifyObservers("Big oof.");
        }
        );
        queue.add(stringRequest);
    }

    @Override
    public void onCreate() {
        repeatingTasks = new ArrayList<>();
        new Thread(runnable).start();
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public void onDestroy() {
        working.set(false);
    }

    public class LocalBinder extends Binder {
        public RepeatingTaskService getService() {
            return RepeatingTaskService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients. See RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();
}