package main

import (
	pb "github.com/SeaEagle568/Piggy-Banks/MainServer"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/handlers"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services"
	"github.com/sirupsen/logrus"
	"github.com/spf13/viper"
	"github.com/subosito/gotenv"
)

func main() {

	if err := initConfig(); err != nil {
		logrus.Fatalf("error occured while initializing config: %s", err.Error())
	}
	if err := gotenv.Load(); err != nil {
		logrus.Fatalf("error occured while initializing enviromental variables: %s", err.Error())
	}
	service := services.NewService()
	handler := handlers.NewHandler(service)

	srv := new(pb.Server)
	if err := srv.Run(viper.GetString("port"), handler.InitRoutes()); err != nil {
		logrus.Fatalf("error occured while setting up server: %s", err.Error())
	}

}

func initConfig() error {
	viper.AddConfigPath("configs")
	viper.SetConfigName("config")
	return viper.ReadInConfig()
}
