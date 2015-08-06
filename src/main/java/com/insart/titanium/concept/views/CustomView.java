package com.insart.titanium.concept.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.context.util.AgentInstanceViewFactoryChainContext;
import com.espertech.esper.epl.expression.core.ExprNode;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.view.DataWindowView;
import com.espertech.esper.view.ViewDataVisitor;
import com.espertech.esper.view.ViewFactory;
import com.espertech.esper.view.ViewFactoryContext;
import com.espertech.esper.view.ViewSupport;
import com.insart.titanium.concept.esper.events.ATMTransactionEvent;
import com.insart.titanium.concept.persistance.repository.ATMTransactionRepository;

/**
 * @author Eugene Pehulja
 * @since Jul 31, 2015 4:26:55 PM
 */
@Component
@Scope("prototype")
public class CustomView extends ViewSupport implements DataWindowView {
	private AgentInstanceViewFactoryChainContext agentInstanceViewFactoryContext;
	private ExprNode account;
	private ViewFactoryContext viewFactoryContext;
	private ViewFactory viewFactory;

	@Autowired
	ATMTransactionRepository eventRepository;

	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
		if (newData != null) {
			Function<ATMTransactionEvent, EventBean> function = viewFactoryContext.getEventAdapterService()::adapterForBean;
			List<ATMTransactionEvent> persistedEvents = null;
			Iterable<ATMTransactionEvent> iterable = eventRepository.findAll();

			if (iterable != null && iterable.iterator().hasNext()) {
				persistedEvents = StreamSupport.stream(eventRepository.findAll().spliterator(), false).filter(a -> a != null).sorted((a, b) -> b.getDate().compareTo(a.getDate()))
						.collect(Collectors.toList());
			} else {
				persistedEvents = new ArrayList<ATMTransactionEvent>(1);
			}
			List<ATMTransactionEvent> outdateEvents = null;
			ATMTransactionEvent newEvent = (ATMTransactionEvent) newData[0].getUnderlying();

			if (persistedEvents.size() >= 5) {
				outdateEvents = persistedEvents.subList(4, persistedEvents.size());
				persistedEvents = persistedEvents.subList(0, 4);
			}

			if (outdateEvents != null) {
				eventRepository.delete(outdateEvents);
				oldData = outdateEvents.stream().map(function).toArray(size -> new EventBean[size]);
			}

			eventRepository.save(newEvent);
			persistedEvents.add(newEvent);

			this.updateChildren(persistedEvents.stream().map(function).toArray(size -> new EventBean[size]), oldData);
		}
	}

	@Override
	public EventType getEventType() {
		return getEventType(agentInstanceViewFactoryContext.getStatementContext().getEventAdapterService());
	}

	protected static EventType getEventType(EventAdapterService eventAdapterService) {
		return eventAdapterService.addBeanType(ATMTransactionEvent.class.getName(), ATMTransactionEvent.class, false, false, false);
	}

	@Override
	public Iterator<EventBean> iterator() {
		return Collections.<EventBean> emptyList().iterator();
	}

	public void setAgentInstanceViewFactoryContext(AgentInstanceViewFactoryChainContext agentInstanceViewFactoryContext) {
		this.agentInstanceViewFactoryContext = agentInstanceViewFactoryContext;
	}

	public void setAccount(ExprNode account) {
		this.account = account;
	}

	public void setViewFactoryContext(ViewFactoryContext viewFactoryContext) {
		this.viewFactoryContext = viewFactoryContext;
	}

	public void setViewFactory(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.espertech.esper.view.ViewDataVisitable#visitView(com.espertech.esper.
	 * view.ViewDataVisitor)
	 */
	@Override
	public void visitView(ViewDataVisitor viewDataVisitor) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.espertech.esper.view.GroupableView#getViewFactory()
	 */
	@Override
	public ViewFactory getViewFactory() {
		return viewFactory;
	}
}
