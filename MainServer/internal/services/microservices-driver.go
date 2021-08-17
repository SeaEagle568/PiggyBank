package services

import (
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services/rpc"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services/utils"
	"github.com/sirupsen/logrus"
	"google.golang.org/grpc"
)

type Microservice struct {
	Name      string `json:"name"`
	Address   string `json:"address"`
	RPCClient rpc.HandlerClient
}

type GoMicroservicesDriver struct {
	dict        map[string]Microservice
	connections []*grpc.ClientConn
}

func (driver *GoMicroservicesDriver) LoadMicroservices() {
	driver.close()
	microservices := utils.GetMicroservices()
	for _, value := range microservices {
		conn, err := grpc.Dial(value.Address, grpc.WithInsecure())
		driver.connections = append(driver.connections, conn)
		if err != nil {
			logrus.Fatalf("Error while trying to dial with microservice %s on address %s : %s", value.Name, value.Address, err.Error())
		}
		value.RPCClient = rpc.NewHandlerClient(conn)
		driver.dict[value.Name] = Microservice(value)
	}
}

func (driver *GoMicroservicesDriver) close() {
	for _, val := range driver.connections {
		err := val.Close()
		if err != nil {
			logrus.Errorf("Unable to close service connction: %s", err.Error())
		}
	}
	driver.connections = make([]*grpc.ClientConn, 0)
}

func (driver *GoMicroservicesDriver) GetMicroservice(name string) Microservice {
	return driver.dict[name]
}

func NewGoMicroservicesDriver() *GoMicroservicesDriver {
	res := &GoMicroservicesDriver{
		make(map[string]Microservice),
		make([]*grpc.ClientConn, 0),
	}
	res.LoadMicroservices()
	return res
}
