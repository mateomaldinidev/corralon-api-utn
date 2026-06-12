package com.utn.corralon.features.notifications.service;

import com.utn.corralon.features.offer.dto.OfferResponseDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;

public interface IEmailService {
    void sendWelcomeEmail(String to, String name);
    void sendPasswordChangedEmail(String to, String name);
    void sendOrderCreatedEmail(String to, String name, OrderResponseDTO order);
    void sendOrderStatusChangedEmail(String to, String name, OrderResponseDTO order, String newStatus);
    void sendOrderCancelledEmail(String to, String name, OrderResponseDTO order, String reason);
    void sendNewPromotionEmail(String to, String name, OfferResponseDTO offer);
}
