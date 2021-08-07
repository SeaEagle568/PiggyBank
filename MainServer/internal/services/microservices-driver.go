package services

import (
	"encoding/json"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services/rpc"
	"github.com/sirupsen/logrus"
	"google.golang.org/grpc"
	"io/ioutil"
	"os"
)

type Microservice struct {
	Name      string `json:"name"`
	Address   string `json:"address"`
	RPCClient rpc.HandlerClient
}

type GoMicroservicesDriver struct {
	dict map[string]Microservice
}

func (driver *GoMicroservicesDriver) InitMicroservices() {
	jsonFile, err := os.Open("configs/microservices.json")
	if err != nil {
		logrus.Fatalf("Error while reading microservices data: %s", err.Error())
	}
	defer func(jsonFile *os.File) {
		err := jsonFile.Close()
		if err != nil {
			logrus.Errorf("Error closing JSON: %s", err.Error())
		}
	}(jsonFile)

	var microservices []Microservice
	byteValue, _ := ioutil.ReadAll(jsonFile)
	err = json.Unmarshal(byteValue, &microservices)
	if err != nil {
		panic("Wrong configuration of microservices.yml")
	}

	for _, value := range microservices {
		conn, err := grpc.Dial(value.Address, grpc.WithInsecure())
		if err != nil {
			logrus.Fatalf("Error while trying to dial with microservice %s on address %s : %s", value.Name, value.Address, err.Error())
		}
		value.RPCClient = rpc.NewHandlerClient(conn)
		driver.dict[value.Name] = value
	}
}

func (driver *GoMicroservicesDriver) GetMicroservice(name string) Microservice {
	return driver.dict[name]
}

func NewGoMicroservicesDriver() *GoMicroservicesDriver {
	res := &GoMicroservicesDriver{
		make(map[string]Microservice),
	}
	res.InitMicroservices()
	return res
}
