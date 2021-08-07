package main

import (
	"bufio"
	"context"
	"fmt"
	pb "github.com/SeaEagle568/Piggy-Banks/MainServer"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/handlers"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services"
	"github.com/sirupsen/logrus"
	"github.com/spf13/viper"
	"github.com/subosito/gotenv"
	"os"
	"strings"
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
	consoleReady := make(chan bool)
	go consoleRead(srv, service, consoleReady)
	if err := srv.Run(viper.GetString("port"), handler.InitRoutes()); err != nil {
		logrus.Fatalf("error occured while setting up server: %s", err.Error())
	}
	<-consoleReady

}

func initConfig() error {
	viper.AddConfigPath("configs")
	viper.SetConfigName("config")
	return viper.ReadInConfig()
}

func consoleRead(srv *pb.Server, service *services.Service, ready chan bool) {
	reader := bufio.NewReader(os.Stdin)
	fmt.Println("Shell initialized")
	fmt.Println("---------------------")

	for {
		text, _ := reader.ReadString('\n')
		// convert CRLF to LF
		text = strings.Replace(text, "\n", "", -1)

		if strings.Compare("reload", text) == 0 {
			fmt.Print("reloading configs")
			service.EventController.LoadEvents()
			fmt.Print("...")
			service.MicroserviceDriver.LoadMicroservices()
			fmt.Print("...")
			service.EventDriver.LoadEventDriver()
			fmt.Println(" Done")
		} else if strings.Compare("stop", text) == 0 {
			fmt.Print("stopping server...")
			err := srv.Shutdown(context.Background())
			if err != nil {
				logrus.Fatalf("Error occurred while stopping server: %s", err.Error())
			}
			fmt.Println(" Done")
			ready <- true
		} else {
			fmt.Println("reload - reloads configs")
			fmt.Println("stop - shutdowns server properly")
		}

	}
}
