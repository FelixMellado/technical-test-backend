package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.model.dto.WalletDTO;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class WalletApplicationIT {

	public static final String NAME = "Jose";
	public static final String EMAIL = "jose@gmail.com";
	public static final String PATH = "/wallets/create";

	@Test
	public void emptyTest() {
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testCreateWallet() {
		WalletDTO walletDTO = new WalletDTO();
		walletDTO.setEmail(EMAIL);
		walletDTO.setName(NAME);
		walletDTO.setAmount(BigDecimal.ZERO);

		ResponseEntity<WalletDTO> response = restTemplate.postForEntity(
				PATH,
				walletDTO,
				WalletDTO.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		WalletDTO responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals(NAME, responseBody.getName());
		assertEquals(EMAIL, responseBody.getEmail());
		assertNotNull(responseBody.getId());
		assertNotNull(responseBody.getEmailId());
		assertEquals(BigDecimal.ZERO, responseBody.getAmount());
	}
}
