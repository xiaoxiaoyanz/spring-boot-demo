package com.xkcoding.mq.kafka;

import com.xkcoding.mq.kafka.constants.KafkaConsts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoMqKafkaApplicationTests {
  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  /**
   * 测试发送消息
   */
  @Test
  public void testSend() {
    kafkaTemplate.send(KafkaConsts.TOPIC_TEST, "hello,kafka...");
  }

  @Test
  public void sendMutily() {

    AtomicInteger count = new AtomicInteger();
    ExecutorService executorService = Executors.newFixedThreadPool(1000);
    for (int j = 0; j < 1000; j++) {
      executorService.submit(() -> {
        for (int i = 0; i < 100000; i++) {
          kafkaTemplate.send(KafkaConsts.TOPIC_TEST, "hello,kafka..." + i);
          count.getAndIncrement();
        }
      });
    }
    while (!executorService.isTerminated()) {
      //System.out.println("hahaa");
    }

  }


}

