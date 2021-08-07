package events

import "encoding/json"

type AuthEvent struct {
	EType string
	Data  *AuthEventData
}

type AuthEventData struct {
	JWTToken string `json:"jwt_token"`
	Client   string `json:"client"`
}

func (a *AuthEvent) GetType() string {
	return a.EType
}

func (a *AuthEvent) GetData() string {
	data, _ := json.Marshal(a.Data)
	return string(data)
}
