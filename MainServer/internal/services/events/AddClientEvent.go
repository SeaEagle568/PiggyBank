package events

type AddClientEvent struct {
	eventType string
}

func (a *AddClientEvent) GetType() string {
	return a.eventType
}

func (a *AddClientEvent) GetData() string {
	panic("implement me")
}

func (a *AddClientEvent) GetResult() string {
	panic("implement me")
}

func (a *AddClientEvent) SetResult(result string) {
	panic("implement me")
}
