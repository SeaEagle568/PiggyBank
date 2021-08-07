package services

import "github.com/gin-gonic/gin"

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
}

type MicroserviceDriver interface {
	InitMicroservices()
	GetMicroservice(name string) Microservice
}
type EventDriver interface {
	InitEventDriver()
	Call(event Event) *EventResult
	RegisterListener(event Event, listener *Listener)
	UnregisterListener(event Event, listener *Listener)
}

type EventController interface {
	InitEvents()
	NewEvent(eventType string, data *gin.Context, edriver EventDriver) (Event, *EventResult)
	NewEmptyEvent(eventType string) Event
	GetHandlerByName(name string) string
	GetEventTypes() []string
}

type Service struct {
	EventController
	MicroserviceDriver
	EventDriver
}

func NewService() *Service {
	events := NewGoEventsController()
	microservices := NewGoMicroservicesDriver()
	eventDriver := NewGoEventDriver(microservices, events)
	return &Service{
		EventController:    events,
		MicroserviceDriver: microservices,
		EventDriver:        eventDriver,
	}

}
