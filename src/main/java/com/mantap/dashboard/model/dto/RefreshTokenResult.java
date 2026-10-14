package com.mantap.dashboard.model.dto;

import com.mantap.dashboard.model.entity.RefreshTokenEntity;

public record RefreshTokenResult (String rawToken, RefreshTokenEntity entity){
}