package services

import (
	"encoding/json"
	"github.com/gin-gonic/gin"
	"io/ioutil"
	"log"
	"os"
)

type GoEvents struct {
	dict       map[string]string
	EventsList []string
}

func (driver *GoEvents) GetHandlerByName(name string) string {
	return driver.dict[name]
}

type JsonEvent struct {
	Name    string `json:"name"`
	Handler string `json:"handler"`
}

func (driver *GoEvents) InitEvents() {

	jsonFile, err := os.Open("configs/events.json")
	if err != nil {
		log.Fatalf("Error while reading events data: %s", err.Error())
	}
	defer jsonFile.Close()
	var events []JsonEvent
	byteValue, _ := ioutil.ReadAll(jsonFile)
	json.Unmarshal(byteValue, &events)

	for _, value := range events {
		driver.EventsList = append(driver.EventsList, value.Name)
		driver.dict[value.Name] = value.Handler
	}
}

func (driver *GoEvents) NewEvent(eventType string, data *gin.Context) *Event {
	panic("implement me")
}

func (driver *GoEvents) NewEmptyEvent(eventType string) *Event {
	panic("implement me")
}

func (driver *GoEvents) GetEventTypes() []string {
	return driver.EventsList
}

func NewGoEvents() *GoEvents {
	res := &GoEvents{
		dict:       make(map[string]string),
		EventsList: make([]string, 0),
	}
	res.InitEvents()
	return res
}
