package com.ydh.redsheep.seckill.controller;

import com.ydh.redsheep.seckill.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SecondKillController {

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Resource
    private StockService stockService;

    @GetMapping("second")
    public String second() {
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                stockService.deductingStock();
            });
        }
//        executorService.shutdown();
        return "秒杀";
    }

}
