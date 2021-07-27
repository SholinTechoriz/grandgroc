package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

public interface QRCodeFoundListener {
    void onQRCodeFound(String qrCode);
    void qrCodeNotFound();
}
