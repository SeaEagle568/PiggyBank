package event_controllers

import (
	"context"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services/rpc"
)

type GoEventDriver struct {
	listeners     map[string]services.Listener
	microservices services.MicroserviceDriver
	events        services.EventManager
}

func (g *GoEventDriver) LoadEventDriver() {
	for _, val := range g.events.GetEventTypes() {
		handlerName := g.events.GetHandlerByName(val)
		g.listeners[val] = services.NewRPCListener(g.microservices.GetMicroservice(handlerName).RPCClient)
	}
}

func (g *GoEventDriver) Call(event services.Event) *services.EventResult {
	result := g.listeners[event.GetType()].HandleEvent(event)
	return result
}

func (g *GoEventDriver) DirectCall(event *rpc.RPCEvent) *rpc.RPCEventResult {
	result, err := g.listeners[event.GetEventType()].GetRPCClient().Handle(context.Background(), event)
	if err != nil {
		return &rpc.RPCEventResult{Code: 500, Message: err.Error()}
	}
	return result
}

func NewGoEventDriver(driver services.MicroserviceDriver, events services.EventManager) *GoEventDriver {
	ged := &GoEventDriver{
		listeners:     make(map[string]services.Listener),
		microservices: driver,
		events:        events,
	}
	ged.LoadEventDriver()
	return ged
}
