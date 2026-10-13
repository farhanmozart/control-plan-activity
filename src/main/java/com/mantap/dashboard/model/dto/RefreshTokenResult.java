package com.mantap.dashboard.model.dto;

import com.mantap.dashboard.model.entity.tasks.RefreshTokenEntity;

public record RefreshTokenResult (String rawToken, RefreshTokenEntity entity){
}