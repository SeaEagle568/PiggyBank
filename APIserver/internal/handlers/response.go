package handlers

import (
	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
)

type jsonError struct {
	Message string `json:"message"`
}

func NewErrorResponce(context *gin.Context, status int, message string) {
	logrus.Error(message)
	context.AbortWithStatusJSON(status, jsonError{message})
}
