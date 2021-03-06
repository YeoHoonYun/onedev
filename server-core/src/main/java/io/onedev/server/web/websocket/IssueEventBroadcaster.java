package io.onedev.server.web.websocket;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.onedev.commons.launcher.loader.Listen;
import io.onedev.server.event.issue.IssueEvent;
import io.onedev.server.issue.BoardSpec;
import io.onedev.server.model.Issue;

@Singleton
public class IssueEventBroadcaster {
	
	private final WebSocketManager webSocketManager;
	
	@Inject
	public IssueEventBroadcaster(WebSocketManager webSocketManager) {
		this.webSocketManager = webSocketManager;
	}

	@Listen
	public void on(IssueEvent event) {
		webSocketManager.notifyObservableChange(Issue.getWebSocketObservable(event.getIssue().getId()), null);
		if (event.affectsBoards())
			webSocketManager.notifyObservableChange(BoardSpec.getWebSocketObservable(event.getIssue().getProject().getId()), null);
	}

}