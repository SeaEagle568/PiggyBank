package auth

import (
	"context"
	"crypto/aes"
	"crypto/cipher"
	"encoding/base64"
	"encoding/json"
	"fmt"
	"github.com/dgrijalva/jwt-go"
	"github.com/sirupsen/logrus"
	"net/http"
	"os"
)

type AuthHandler struct {
}

func (a *AuthHandler) mustEmbedUnimplementedHandlerServer() {
	return
}

type AuthEventData struct {
	JWTToken string `json:"jwt_token"`
	Client   string `json:"client"`
}
type AuthorisationData struct {
	Username string `json:"username"`
	Name     string `json:"name"`
	Wallet   string `json:"wallet"`
	Client   string `json:"client"`
}

func (a *AuthHandler) Handle(ctx context.Context, event *RPCEvent) (*RPCEventResult, error) {
	logrus.Infof("new cock arrived with type: %s, data: %s", event.GetEventType(), event.GetData())
	if event.GetEventType() != "AuthEvent" {
		return nil, fmt.Errorf("you tried to handle %s with AuthService, are you dumb? ", event.GetEventType())
	}
	var data AuthEventData
	err := json.Unmarshal([]byte(event.GetData()), &data)
	if err != nil {
		return nil, fmt.Errorf("error occured while trying to parse event JSON - sincerely yours AuthService")
	}
	resultData, err := extractTokenMetadata(data)
	if err != nil {
		return &RPCEventResult{Code: http.StatusUnauthorized, Message: err.Error()}, nil
	}
	jsonResultData, err := json.Marshal(resultData)
	if err != nil {
		return nil, fmt.Errorf("internal error in AuthService")
	}

	return &RPCEventResult{Code: http.StatusOK, Message: string(jsonResultData)}, nil
}

func extractTokenMetadata(data AuthEventData) (*AuthorisationData, error) {
	token, err := verifyToken(data.JWTToken)
	if err != nil {
		return nil, err
	}
	claims, ok := token.Claims.(jwt.MapClaims)
	if ok && token.Valid {
		username := decrypt([]byte(os.Getenv("AES_KEY")), claims["username"].(string))
		name := decrypt([]byte(os.Getenv("AES_KEY")), claims["name"].(string))
		wallet := decrypt([]byte(os.Getenv("AES_KEY")), claims["wallet"].(string))
		return &AuthorisationData{
			Username: username,
			Name:     name,
			Wallet:   wallet,
			Client:   data.Client,
		}, nil
	}
	return nil, fmt.Errorf("invalid JWT token")
}

func verifyToken(tokenString string) (*jwt.Token, error) {
	token, err := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("unexpected signing method: %v", token.Header["alg"])
		}
		return []byte(os.Getenv("ACCESS_SECRET")), nil
	})
	if err != nil {
		return nil, err
	}
	return token, nil
}

func decrypt(key []byte, input string) (output string) {
	cipherText, err := base64.URLEncoding.DecodeString(input)
	if err != nil {
		panic(fmt.Sprint("Error decoding base64: %s", err.Error()))
	}

	block, err := aes.NewCipher(key)
	if err != nil {
		panic(fmt.Sprint("Error generating block: %s", err.Error()))
	}

	if len(cipherText) < aes.BlockSize {
		panic("Ciphertext block size is too short!")
	}

	iv := cipherText[:aes.BlockSize]
	cipherText = cipherText[aes.BlockSize:]

	stream := cipher.NewCFBDecrypter(block, iv)
	stream.XORKeyStream(cipherText, cipherText)

	output = string(cipherText)
	return output
}
