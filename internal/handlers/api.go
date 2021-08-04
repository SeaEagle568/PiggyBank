package handlers

import "github.com/gin-gonic/gin"

func (h *Handler) add(context *gin.Context) {
	event := h.services.NewEvent("AddPBEvent", context)
	h.services.EventDriver.Call(event)
}

func (h *Handler) getID(context *gin.Context) {
	event := h.services.NewEvent("GetPBEvent", context)
	h.services.EventDriver.Call(event)
}

func (h *Handler) deleteID(context *gin.Context) {
	event := h.services.NewEvent("DeletePBEvent", context)
	h.services.EventDriver.Call(event)
}

func (h *Handler) changeID(context *gin.Context) {
	event := h.services.NewEvent("AddVotingEvent", context)
	h.services.EventDriver.Call(event)
}

func (h *Handler) withdrawID(context *gin.Context) {
	event := h.services.NewEvent("WithdrawEvent", context)
	h.services.EventDriver.Call(event)
}

func (h *Handler) voteID(context *gin.Context) {
	event := h.services.NewEvent("VoteEvent", context)
	h.services.EventDriver.Call(event)
}

func (h *Handler) getTransactions(context *gin.Context) {
	event := h.services.NewEvent("GetTransactionsEvent", context)
	h.services.EventDriver.Call(event)
}
