package utils

import (
	"encoding/json"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services/rpc"
	"github.com/sirupsen/logrus"
	"io/ioutil"
	"os"
)

type JsonEvent struct {
	Name    string `json:"name"`
	Handler string `json:"handler"`
}
type Microservice struct {
	Name      string `json:"name"`
	Address   string `json:"address"`
	RPCClient rpc.HandlerClient
}

func GetMicroservices() []Microservice {
	var microservices []Microservice
	byteValue, err := readFile("configs/microservices.json")
	if err != nil {
		logrus.Fatalf("Error while reading config file: %s", err.Error())
	}
	err = json.Unmarshal(byteValue, &microservices)
	if err != nil {
		panic("Wrong configuration of microservices.yml")
	}
	return microservices
}

func GetEvents() []JsonEvent {
	var events []JsonEvent
	byteValue, err := readFile("configs/events.json")
	if err != nil {
		logrus.Fatalf("Error while reading config file: %s", err.Error())
	}

	err = json.Unmarshal(byteValue, &events)
	if err != nil {
		panic("Wrong configuration in events.yml")
	}
	return events
}

func readFile(filename string) ([]byte, error) {
	jsonFile, err := os.Open(filename)
	if err != nil {
		logrus.Fatalf("Error while reading events data: %s", err.Error())
	}
	defer func(jsonFile *os.File) {
		err := jsonFile.Close()
		if err != nil {
			logrus.Errorf("Error closing JSON: %s", err.Error())
		}
	}(jsonFile)
	byteValue, err := ioutil.ReadAll(jsonFile)
	return byteValue, err
}
