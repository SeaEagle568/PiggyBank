package services

import (
	"encoding/json"
	"github.com/SeaEagle568/Piggy-Banks/MainServer/internal/services/events"
	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
	"net/http"
	"strings"
)

type Events struct {
}
type AuthorisationData struct {
	Username string `json:"username"`
	Name     string `json:"name"`
	Wallet   string `json:"wallet"`
	Client   string `json:"client"`
}

func getAuthInfo(ctx *gin.Context, driver EventDriver) (data *AuthorisationData, eventResult *EventResult) {
	event, result := Events{}.AuthEvent(ctx)
	if result != nil {
		return nil, result
	}
	answer := EventDriver.Call(driver, event)
	if answer.Code != 200 {
		return nil, answer
	}
	var temp AuthorisationData
	err := json.Unmarshal([]byte(answer.Message), &temp)
	data = &temp
	if err != nil {
		return nil, &EventResult{http.StatusInternalServerError, "Authentication server error: " + err.Error()}
	}
	return data, nil
}

func (e Events) AddPBEvent(context *gin.Context, driver EventDriver) (*events.AddPBEvent, *EventResult) {
	var eventData events.AddPBEventData
	err := context.BindJSON(&eventData)
	if err != nil {
		return nil, &EventResult{http.StatusBadRequest, "Error occurred while trying to parse request JSON"}
	}

	authdata, result := getAuthInfo(context, driver)
	if result != nil {
		return nil, result
	}
	eventData.Name = authdata.Username
	event := &events.AddPBEvent{EType: "AddPBEvent", Data: &eventData}
	logrus.Info(event)
	return event, nil
}

func (e Events) AuthEvent(context *gin.Context) (*events.AuthEvent, *EventResult) {
	bearToken := context.GetHeader("Authorization")
	strArr := strings.Split(bearToken, " ")
	if len(strArr) != 2 {
		return nil, &EventResult{http.StatusUnauthorized, "Invalid token format"}
	}
	token := strArr[1]

	clientName := context.GetHeader("Client")
	eventData := &events.AuthEventData{token, clientName}
	event := &events.AuthEvent{EType: "AuthEvent", Data: eventData}
	return event, nil
}
