package handlers

import "github.com/gin-gonic/gin"

func (h *Handler) add(context *gin.Context) {
	h.callEvent("AddPBEvent", context)
}

func (h *Handler) getID(context *gin.Context) {
	h.callEvent("GetPBEvent", context)
}

func (h *Handler) deleteID(context *gin.Context) {
	h.callEvent("DeletePBEvent", context)
}

func (h *Handler) changeID(context *gin.Context) {
	h.callEvent("AddVotingEvent", context)
}

func (h *Handler) withdrawID(context *gin.Context) {
	h.callEvent("WithdrawEvent", context)
}

func (h *Handler) voteID(context *gin.Context) {
	h.callEvent("VoteEvent", context)
}

func (h *Handler) getTransactions(context *gin.Context) {
	h.callEvent("GetTransactionsEvent", context)
}

func (h *Handler) callEvent(name string, context *gin.Context) {
	event, result := h.Services.NewEvent(name, context, h.Services.EventDriver)
	if result != nil {
		NewErrorResponce(context, result.Code, result.Message)
		return
	}
	answer := h.Services.EventDriver.Call(event)
	context.JSON(answer.Code, answer.Message)
}
