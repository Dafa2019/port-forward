package org.inurl.pf.support;

import org.inurl.pf.model.FlowAnalysis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlowAnalysisUtil {

    private static Map<String, FlowAnalysis> flowMap = new ConcurrentHashMap<>();

    public static FlowAnalysis get(String no) {
        return getOrNew(no);
    }

    public static void addReviveFlow(String no, long amount) {
        String name = "rf." + no;
        LockUtil.lock(name);
        FlowAnalysis flow = getOrNew(no);
        flow.addReceive(amount);
        flowMap.put(no, flow);
        LockUtil.release(name);
    }

    public static void addSendFlow(String no, long amount) {
        String name = "sf." + no;
        LockUtil.lock(name);
        FlowAnalysis flow = getOrNew(no);
        flow.addSend(amount);
        flowMap.put(no, flow);
        LockUtil.release(name);
    }

    public static void addConnectFlow(String no, long amount) {
        String name = "cf." + no;
        LockUtil.lock(name);
        FlowAnalysis flow = getOrNew(no);
        flow.addConnect(amount);
        flowMap.put(no, flow);
        LockUtil.release(name);
    }

    private static FlowAnalysis getOrNew(String no) {
        FlowAnalysis flow = flowMap.get(no);
        if (flow == null)
            flow = new FlowAnalysis();
        return flow;
    }

    public static FlowAnalysis getTotal() {
        FlowAnalysis flow = new FlowAnalysis();
        for (FlowAnalysis v : flowMap.values()) {
            flow.addConnect(v.getConnect());
            flow.addSend(v.getSend());
            flow.addReceive(v.getReceive());
        }
        return flow;
    }

}
