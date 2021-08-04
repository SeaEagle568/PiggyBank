package services

import (
	"context"
)

type RPCListener struct {
	client HandlerClient
}

func (g *RPCListener) HandleEvent(event *Event) error {
	rpcEvent := &RPCEvent{
		EventType: event.eventType,
		Data:      event.data,
		Result:    event.result,
	}

	changedEvent, err := g.client.Handle(context.Background(), rpcEvent)

	event.eventType = changedEvent.EventType
	event.data = changedEvent.Data
	event.result = changedEvent.Result

	return err
}

func NewRPCListener(client HandlerClient) *RPCListener {
	return &RPCListener{client}
}
