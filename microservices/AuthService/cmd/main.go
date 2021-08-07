package main

import (
	"github.com/SeaEagle568/Piggy-Banks/microservices/AuthService/internal/services"
	"github.com/sirupsen/logrus"
	"github.com/spf13/viper"
	"github.com/subosito/gotenv"
	"google.golang.org/grpc"
	"net"
)

func main() {
	if err := gotenv.Load(); err != nil {
		logrus.Fatalf("error occured while initializing enviromental variables: %s", err.Error())
	}
	if err := initConfig(); err != nil {
		logrus.Fatalf("error occured while initializing config: %s", err.Error())
	}
	server := grpc.NewServer()
	service := &services.AuthHandler{}
	services.RegisterHandlerServer(server, service)

	listener, err := net.Listen("tcp", viper.GetString("port"))
	if err != nil {
		logrus.Fatalf(err.Error())
	}

	if err := server.Serve(listener); err != nil {
		logrus.Fatalf(err.Error())
	}

}

func initConfig() error {
	viper.AddConfigPath("configs")
	viper.SetConfigName("config")
	return viper.ReadInConfig()
}
