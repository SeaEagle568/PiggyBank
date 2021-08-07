package main

import (
	"github.com/SeaEagle568/Piggy-Banks/Microservices/AuthService/internal/auth"
	"github.com/sirupsen/logrus"
	"github.com/subosito/gotenv"
	"google.golang.org/grpc"
	"net"
)

func main() {
	if err := gotenv.Load(); err != nil {
		logrus.Fatalf("error occured while initializing enviromental variables: %s", err.Error())
	}
	server := grpc.NewServer()
	service := &auth.AuthHandler{}
	auth.RegisterHandlerServer(server, service)

	listener, err := net.Listen("tcp", ":8004")
	if err != nil {
		logrus.Fatalf(err.Error())
	}

	if err := server.Serve(listener); err != nil {
		logrus.Fatalf(err.Error())
	}

}
