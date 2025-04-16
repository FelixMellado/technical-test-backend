package com.playtomic.tests.wallet.model.dto;

import com.playtomic.tests.wallet.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WalletMapper {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Mapping(source = "id", target = "id")
    WalletDTO walletToWalletDTO(Wallet wallet);
    Wallet walletDtoToWallet(WalletDTO walletDTO);
}

