package services

import (
	"encoding/json"
	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
	"io/ioutil"
	"os"
	"reflect"
)

type GoEventsController struct {
	nameToHandle map[string]string
	NamesList    []string
}

func (driver *GoEventsController) GetHandlerByName(name string) string {
	return driver.nameToHandle[name]
}

type JsonEvent struct {
	Name    string `json:"name"`
	Handler string `json:"handler"`
}

func (driver *GoEventsController) InitEvents() {

	jsonFile, err := os.Open("configs/events.json")
	if err != nil {
		logrus.Fatalf("Error while reading events data: %s", err.Error())
	}
	defer func(jsonFile *os.File) {
		err := jsonFile.Close()
		if err != nil {
			logrus.Errorf("Error closing JSON: %s", err.Error())
		}
	}(jsonFile)

	var events []JsonEvent
	byteValue, _ := ioutil.ReadAll(jsonFile)
	err = json.Unmarshal(byteValue, &events)
	if err != nil {
		panic("Wrong configuration in events.yml")
	}

	for _, value := range events {
		driver.NamesList = append(driver.NamesList, value.Name)
		driver.nameToHandle[value.Name] = value.Handler
	}
}

func (driver *GoEventsController) NewEvent(eventType string, data *gin.Context, edriver EventDriver) (Event, *EventResult) {
	var events Events
	inputs := make([]reflect.Value, 2)
	inputs[0] = reflect.ValueOf(data)
	inputs[1] = reflect.ValueOf(edriver)
	results := reflect.ValueOf(&events).MethodByName(eventType).Call(inputs)
	if !results[1].IsNil() {
		return nil, results[1].Interface().(*EventResult)
	}
	return results[0].Interface().(Event), nil
}

func (driver *GoEventsController) GetEventTypes() []string {
	return driver.NamesList
}

func NewGoEventsController() *GoEventsController {
	res := &GoEventsController{
		nameToHandle: make(map[string]string),
		NamesList:    make([]string, 0),
	}
	res.InitEvents()
	return res
}
