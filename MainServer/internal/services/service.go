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
	LoadMicroservices()
	GetMicroservice(name string) Microservice
}
type EventDriver interface {
	LoadEventDriver()
	Call(event Event) *EventResult
}

type EventController interface {
	LoadEvents()
	NewEvent(eventType string, data *gin.Context, edriver EventDriver) (Event, *EventResult)
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
