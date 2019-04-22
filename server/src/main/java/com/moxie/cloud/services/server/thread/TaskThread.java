package com.moxie.cloud.services.server.thread;

import com.moxie.cloud.services.server.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/3/26
 */


@Component
public class TaskThread implements Runnable {


    @Autowired
    WorkerService workerService;

    @Override
    public void run() {
        workerService.analyseRecycle();
    }
}
