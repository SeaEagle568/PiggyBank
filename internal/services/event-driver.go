package services

type GoEventDriver struct {
	listeners     map[string]Listener
	microservices MicroserviceDriver
	events        Events
}

func (g *GoEventDriver) InitEventDriver() {
	for _, val := range g.events.GetEventTypes() {
		handlerName := g.events.GetHandlerByName(val)
		g.listeners[val] = NewRPCListener(g.microservices.GetMicroservice(handlerName).RPCClient)
	}
}

func (g *GoEventDriver) Call(event *Event) string {
	panic("implement me")
}

func (g *GoEventDriver) RegisterListener(event *Event, listener *Listener) {
	panic("implement me")
}

func (g *GoEventDriver) UnregisterListener(event *Event, listener *Listener) {
	panic("implement me")
}

func NewGoEventDriver(driver MicroserviceDriver, events Events) *GoEventDriver {
	ged := &GoEventDriver{
		listeners:     make(map[string]Listener),
		microservices: driver,
		events:        events,
	}
	ged.InitEventDriver()
	return ged
}
