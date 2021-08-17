package event_controllers

import (
	"context"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services/rpc"
)

type EventHandler struct {
	driver services.EventDriver
}

func (e *EventHandler) Handle(_ context.Context, event *rpc.RPCEvent) (*rpc.RPCEventResult, error) {
	return e.driver.DirectCall(event), nil
}

func (e *EventHandler) MustEmbedUnimplementedHandlerServer() {
	panic("implement me")
}
