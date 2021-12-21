package com.anker.expressfeeprarate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleTest {

    static final Logger log = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void test() throws InterruptedException, ExecutionException {
        List<FutureTask<Long>> tasks = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            tasks.add(testRules(10000));
        }
        List<Long> durations = tasks.stream().map(t -> {
            try {
                return t.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        Long duration = durations.stream().mapToLong(Long::longValue).sum();
        System.out.println("Total Calculate Spent time is: " + duration);
    }

    private FutureTask<Long> testRules(long count) throws InterruptedException, ExecutionException {
        FutureTask<Long> futureTask = new FutureTask<Long>(() -> {
            long startTime = System.currentTimeMillis();

            KieServices kieServices = KieServices.Factory.get();
            KieContainer kContainer = kieServices.getKieClasspathContainer();
            KieBase kieBase = kContainer.getKieBase();
            KieSession session = kieBase.newKieSession();

            insertHcInfo(session);
            insertOrderRevenue(session, count);
            insertBasicData(session);

            session.fireAllRules();
            Long spentTime = (Long) session.getGlobal("spentTime");
            session.dispose();
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            System.out.println(session.getObjects().size() + " spent time: " + duration);
            System.out.println("calculation spent time: " + spentTime);
            return spentTime;
        });
        new Thread(futureTask).run();
        return futureTask;
    }

    private void insertHcInfo(KieSession session) {
        HcInfo noneBGHcInfo = new HcInfo("202107", 10000, 0, "None BG", 0, 0);
        session.insert(noneBGHcInfo);
        String[] bgPdt = { "Battery", "Cable", "Headphone", "xxx", "yyy" };
        for (int i = 0; i < 5; i++) {
            HcInfo hcInfo = new HcInfo("202107", i + 1, 0, bgPdt[i % 5], 0, 0);
            session.insert(hcInfo);
        }
    }

    private void insertOrderRevenue(KieSession session, long count) {
        String[] bgPdt = { "Battery", "Cable", "Headphone", "xxx", "yyy" };
        String[] marketplace = { "aliexpress", "amazon" };
        for (int i = 0; i < count; i++) {
            OrderRevenue orderRevenue = new OrderRevenue("202107", marketplace[i % 2] + "" + i % 100,
                    bgPdt[i % 5], i + 1, 0, 0);
            session.insert(orderRevenue);
        }
    }

    private void insertBasicData(KieSession session) {
        for (int i = 0; i < 1; i++) {
            BasicData basicData = new BasicData("202107", "", 10000);
            session.insert(basicData);
        }
    }
}