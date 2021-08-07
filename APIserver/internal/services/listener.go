package services

import (
	"context"
	"github.com/SeaEagle568/Piggy-Banks/APIserver/internal/services/rpc"
)

type RPCListener struct {
	client rpc.HandlerClient
}

func (g *RPCListener) HandleEvent(event Event) *EventResult {
	rpcEvent := &rpc.RPCEvent{
		EventType: event.GetType(),
		Data:      event.GetData(),
	}

	result, err := g.client.Handle(context.Background(), rpcEvent)
	if err != nil {
		return &EventResult{
			Code:    500,
			Message: "Error making RPC call: " + err.Error(),
		}
	}
	return &EventResult{
		Code:    int(result.Code),
		Message: result.Message,
	}
}

func NewRPCListener(client rpc.HandlerClient) *RPCListener {
	return &RPCListener{client}
}
