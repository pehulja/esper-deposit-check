package com.insart.titanium.concept.views;

import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;

import com.espertech.esper.client.EventType;
import com.espertech.esper.core.context.util.AgentInstanceViewFactoryChainContext;
import com.espertech.esper.core.service.StatementContext;
import com.espertech.esper.epl.expression.core.ExprNode;
import com.espertech.esper.view.DataWindowViewFactory;
import com.espertech.esper.view.View;
import com.espertech.esper.view.ViewFactory;
import com.espertech.esper.view.ViewFactoryContext;
import com.espertech.esper.view.ViewFactorySupport;
import com.espertech.esper.view.ViewParameterException;
import com.insart.titanium.concept.configuration.ApplicationContextProvider;

/**
 * @author Eugene Pehulja
 * @since Jul 31, 2015 4:15:25 PM
 */
public class CustomViewFactory extends ViewFactorySupport implements DataWindowViewFactory {

	private ViewFactoryContext viewFactoryContext;
	private List<ExprNode> viewParameters;
	private ExprNode accountNameExpression;
	private ConfigurableApplicationContext applicationContext;

	public CustomViewFactory() {
		super();
	}

	@Override
	public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters) throws ViewParameterException {
		this.viewFactoryContext = viewFactoryContext;
		if (viewParameters.size() != 1) {
			throw new ViewParameterException("View require 1 parameter -> length of the distributed view");
		}
		this.viewParameters = viewParameters;
		applicationContext = (ConfigurableApplicationContext) ApplicationContextProvider.getApplicationContext();
	}

	@Override
	public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories)
			throws ViewParameterException {
		ExprNode[] validatedNodes = ViewFactorySupport.validate("Distributed Length view", parentEventType, statementContext, viewParameters, true);
		accountNameExpression = validatedNodes[0];

		if ((accountNameExpression.getExprEvaluator().getType() != Integer.class)) {
			throw new ViewParameterException("View requires long-typed timestamp values in parameter 1");
		}
	}

	@Override
	public View makeView(AgentInstanceViewFactoryChainContext agentInstanceViewFactoryContext) {
		CustomView customView = applicationContext.getBean(CustomView.class);
		customView.setAgentInstanceViewFactoryContext(agentInstanceViewFactoryContext);
		customView.setAccount(accountNameExpression);
		customView.setViewFactoryContext(viewFactoryContext);
		customView.setViewFactory(this);
		return customView;
	}

	@Override
	public EventType getEventType() {
		return CustomView.getEventType(viewFactoryContext.getEventAdapterService());
	}

	@Override
	public String getViewName() {
		return CustomView.class.getSimpleName();
	}

}
