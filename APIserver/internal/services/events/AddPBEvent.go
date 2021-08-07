package events

import (
	"encoding/json"
	"github.com/sirupsen/logrus"
)

type AddPBEvent struct {
	EType string
	Data  *AddPBEventData
}

type AddPBEventData struct {
	Name        string `json:"name"`
	Description string `json:"description" binding:"required"`
	Author      string `json:"author" binding:"required"`
	Destination string `json:"destination" binding:"required"`
}

func (a *AddPBEvent) GetType() string {
	return a.EType
}

func (a *AddPBEvent) GetData() string {
	data, _ := json.Marshal(a.Data)
	logrus.Infof("Data from AddPBEvent:\n%s", data)
	return string(data)
}
