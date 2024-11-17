package br.gov.serpro.sedat.seat3.pocs.springbootoauth2authorizationserverdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class SpringbootOauth2AuthorizationServerDemoApplicationTests {

	@Test
	@WithAnonymousUser
	void anonymoudUserTest() {
		//TODO
	}

	@Test
	@WithMockUser(username="customUsername@example.io", roles={"USER_ADMIN"})
	public void test() {
		//TODO
	}

}
