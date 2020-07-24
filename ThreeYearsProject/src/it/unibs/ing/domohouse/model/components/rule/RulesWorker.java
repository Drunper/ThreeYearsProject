package it.unibs.ing.domohouse.model.components.rule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.*;

import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.ModelStrings;

public class RulesWorker {

	private ScheduledExecutorService checkThread;
	private Map<Rule, Double> queuedRulesMap;
	private ClockStrategy clock;
	private DataFacade dataFacade;

	public RulesWorker(DataFacade dataFacade, ClockStrategy clock) {
		this.dataFacade = dataFacade;
		this.clock = clock;
		queuedRulesMap = new HashMap<>();
		checkThread = Executors.newSingleThreadScheduledExecutor();
	}

	public void startRulesWorker() {
		checkThread.scheduleAtFixedRate(new Runnable() {
			public void run() {
				try {
					checkRules();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 1, TimeUnit.SECONDS); // parte subito e ogni 1 secondi controlla le rules
	}

	public void checkRules() {
		for (Rule rule : dataFacade.getEnabledRulesList())
			checkRule(rule);

		for (Rule rule : queuedRulesMap.keySet())
			if (queuedRulesMap.get(rule) == Double.parseDouble(clock.getCurrentTime()))
				rule.doAction();
	}

	public void addQueuedRule(Rule rule, double time) {
		assert rule != null;

		queuedRulesMap.put(rule, time);
	}

	public void removeQueuedRule(Rule rule) {
		assert rule != null;

		queuedRulesMap.remove(rule);
	}

	public boolean containsQueuedRule(Rule rule) {
		assert rule != null;

		return queuedRulesMap.containsKey(rule);
	}

	public void checkRule(Rule rule) {
		assert ruleWorkerInvariant() : ModelStrings.WRONG_INVARIANT;
		if (rule.getState().isActive() && rule.getAntecedentValue(clock.getCurrentTime()))
			doConsequent(rule);
	}

	public void doConsequent(Rule rule) {
		assert ruleWorkerInvariant() : ModelStrings.WRONG_INVARIANT;
		if (rule.hasStartConsequent())
			queuedRulesMap.put(rule, rule.getTime());
		else
			rule.doAction();
	}

	public void stopCheckRules() {
		checkThread.shutdown();
	}

	private boolean ruleWorkerInvariant() {
		return dataFacade != null && queuedRulesMap != null;
	}
}
