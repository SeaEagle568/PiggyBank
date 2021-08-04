package services

import (
	"encoding/json"
	"google.golang.org/grpc"
	"io/ioutil"
	"log"
	"os"
)

type Microservice struct {
	Name      string `json:"name"`
	Address   string `json:"address"`
	RPCClient HandlerClient
}

type GoMicroservicesDriver struct {
	dict map[string]Microservice
}

func (driver *GoMicroservicesDriver) InitMicroservices() {
	jsonFile, err := os.Open("configs/microservices.json")
	if err != nil {
		log.Fatalf("Error while reading microservices data: %s", err.Error())
	}
	defer jsonFile.Close()
	var microservices []Microservice
	byteValue, _ := ioutil.ReadAll(jsonFile)
	json.Unmarshal(byteValue, &microservices)

	for _, value := range microservices {
		conn, err := grpc.Dial(value.Address, grpc.WithInsecure())
		if err != nil {
			log.Fatalf("Error while trying to dial with microservice %s on address %s : %s", value.Name, value.Address, err.Error())
		}
		value.RPCClient = NewHandlerClient(conn)
		driver.dict[value.Name] = value
		conn.Close()
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
