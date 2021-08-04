package services

import "github.com/gin-gonic/gin"

type Event struct {
	eventType string
	data      string
	result    string
}

type Listener interface {
	HandleEvent(event *Event) error
}

type MicroserviceDriver interface {
	InitMicroservices()
	GetMicroservice(name string) Microservice
}
type EventDriver interface {
	InitEventDriver()
	Call(event *Event) string
	RegisterListener(event *Event, listener *Listener)
	UnregisterListener(event *Event, listener *Listener)
}

type Events interface {
	InitEvents()
	NewEvent(eventType string, data *gin.Context) *Event
	NewEmptyEvent(eventType string) *Event
	GetHandlerByName(name string) string
	GetEventTypes() []string
}

type Service struct {
	Events
	MicroserviceDriver
	EventDriver
}

func NewService() *Service {
	events := NewGoEvents()
	microservices := NewGoMicroservicesDriver()
	eventDriver := NewGoEventDriver(microservices, events)
	return &Service{
		Events:             events,
		MicroserviceDriver: microservices,
		EventDriver:        eventDriver,
	}

}
