package org.inurl.pf.support;

import org.inurl.pf.model.FlowAnalysis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author raylax
 */
public class FlowAnalysisUtil {

    private static Map<String, FlowAnalysis> flowMap = new ConcurrentHashMap<>();

    public static FlowAnalysis get(String no) {
        return flowMap.get(no);
    }

    public static void clear(String no) {
        flowMap.remove(no);
    }

    public static void addReviveFlow(String no, long amount) {
        get(no).addReceive(amount);
    }

    public static void addSendFlow(String no, long amount) {
        get(no).addSend(amount);
    }

    public static void addConnectFlow(String no, long amount) {
        get(no).addConnect(amount);
    }

    public static void addTotalConnectFlow(String no, long amount) {
        get(no).addTotalConnect(amount);
    }

    public static void create(String no) {
        flowMap.put(no, new FlowAnalysis());
    }

    public static FlowAnalysis getTotal() {
        FlowAnalysis flow = new FlowAnalysis();
        for (FlowAnalysis v : flowMap.values()) {
            flow.addSend(v.getSend());
            flow.addReceive(v.getReceive());
            flow.addConnect(v.getConnect());
            flow.addTotalConnect(v.getTotalConnect());
        }
        return flow;
    }

}
