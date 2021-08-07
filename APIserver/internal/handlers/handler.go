package handlers

import (
	"github.com/SeaEagle568/Piggy-Banks/APIserver/internal/services"
	"github.com/gin-gonic/gin"
)

type Handler struct {
	Services *services.Service
}

func NewHandler(services *services.Service) *Handler {
	return &Handler{Services: services}
}

func (h Handler) InitRoutes() *gin.Engine {
	router := gin.Default()

	api := router.Group("/api/pb")
	{
		api.POST("/add", h.add)
		api.GET("/get/:id", h.getID)
		api.DELETE("/delete/:id", h.deleteID)
		api.PUT("/change/:id", h.changeID)
		api.GET("/withdraw/:id", h.withdrawID)
		api.POST("/vote/:id", h.voteID)
		api.GET("/transactions", h.getTransactions)
	}

	return router
}
