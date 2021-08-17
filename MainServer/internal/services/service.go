package services

import (
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services/event_controllers"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services/rpc"
	"github.com/gin-gonic/gin"
)

type Event interface {
	GetType() string
	GetData() string
}

type EventResult struct {
	Code    int
	Message string
}

type Listener interface {
	HandleEvent(event Event) *EventResult
	GetRPCClient() rpc.HandlerClient
}

type MicroserviceDriver interface {
	LoadMicroservices()
	GetMicroservice(name string) Microservice
}
type EventDriver interface {
	LoadEventDriver()
	Call(event Event) *EventResult
	DirectCall(event *rpc.RPCEvent) *rpc.RPCEventResult
}

type EventManager interface {
	LoadEvents()
	NewEvent(eventType string, data *gin.Context, edriver EventDriver) (Event, *EventResult)
	GetHandlerByName(name string) string
	GetEventTypes() []string
}

type Service struct {
	EventManager
	MicroserviceDriver
	EventDriver
}

func NewService() *Service {
	events := event_controllers.NewGoEventsController()
	microservices := NewGoMicroservicesDriver()
	eventDriver := event_controllers.NewGoEventDriver(microservices, events)
	return &Service{
		EventManager:       events,
		MicroserviceDriver: microservices,
		EventDriver:        eventDriver,
	}

}
